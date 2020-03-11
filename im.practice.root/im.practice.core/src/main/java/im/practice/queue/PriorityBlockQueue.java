package im.practice.queue;

import java.io.Serializable;
import java.util.Comparator;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 说明：自定义优先级队列,队列里的元素在加入队列时需要制定优先级，并且支持元素优先级调整(只能调整到最高优先级)
 * 实现的方法：自动扩容，入队，出队，调整优先级
 * @author yangyang
 * @date 2019-06-11 15:41:16
 */

public class PriorityBlockQueue<E> implements Serializable {
    private static final long serialVersionUID = 5169357435834504495L;
    /**
     * 默认队列容量
     */
    private static final int DEFAULT_INITIAL_CAPACITY = 11;
    /**
     * 队列最大容量
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    /**
     * 队列数组
     */
    private transient Object[] queue;
    /**
     * 队列中元素个数
     */
    private transient int size;
    /**
     * 比较器，优先级队列时根据此比较器确认元素的优先级，
     * 所以队列中的元素必须实现Comparable接口
     */
    private transient Comparator<? super E> comparator;
    /**
     * 可重入锁，用来实现队列同步机制，相比synchronized更灵活
     */
    private final ReentrantLock lock;
    private final Condition notEmpty;
    /**
     * 自旋变量,队列自动扩容判断标志
     */
    private transient volatile int allocationSpinLock;
    /**
     *  Unsafe类提供了一些比较底层的不安全的操作，
     *  但是这些操作都非常有用，很多类库都应用了Unsafe的功能
     */
    private static final sun.misc.Unsafe UNSAFE;
    /**
     * 读取内容中自旋变量的偏移量
     */
    private static final long allocationSpinLockOffset;
    static {
        try {
            UNSAFE = sun.misc.Unsafe.getUnsafe();
            Class<?> k = PriorityBlockQueue.class;
            allocationSpinLockOffset = UNSAFE.objectFieldOffset
                    (k.getDeclaredField("allocationSpinLock"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    /**
     * 各种构造器
     */
    public PriorityBlockQueue() {
        this(DEFAULT_INITIAL_CAPACITY, null);
    }

    public PriorityBlockQueue(int initialCapacity) {
        this(initialCapacity, null);
    }

    public PriorityBlockQueue(int initialCapacity,
                                 Comparator<? super E> comparator) {
        if (initialCapacity < 1)
            throw new IllegalArgumentException();
        this.lock = new ReentrantLock();
        this.notEmpty = lock.newCondition();
        this.comparator = comparator;
        this.queue = new Object[initialCapacity];
    }
    /**
     * 函数说明：添加一个元素到队列中
     * 使用方法：
     *
     * @param e
     * @return
     * @author yangyang
     * @date 2019-06-11 16:25:07
     */
    public boolean offer(E e) {
        if (e == null)
            throw new NullPointerException();
        final ReentrantLock lock = this.lock;
        lock.lock();
        int n, cap;
        Object[] array;
        //当前元素个数大于队列容量时，开始进行扩容
        while ((n = size) >= (cap = (array = queue).length))
            tryGrow(array, cap);
        try {
            Comparator<? super E> cmp = comparator;
            if (cmp == null)
                siftUpComparable(n, e, array);
            else
                siftUpUsingComparator(n, e, array, cmp);
            size = n + 1;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
        return true;
    }


    /**
     * 函数说明：自动扩容方法
     * 使用方法：
     *
     * @param array 队列数组
     * @param oldCap 旧队列容量
     * @return
     * @author yangyang
     * @date 2019-06-11 15:57:02
     */
    private void tryGrow(Object[] array, int oldCap) {
        //先释放锁,这样当队列在自动扩容的同时，其他线程可以操作入队或者出队操作，提高并发效率
        // 而自动扩容使用CAS操作保证线程的安全性
        lock.unlock();
        Object[] newArray = null;
        //compareAndSwapInt方法利用Unsafe提高的原子CAS功能，保证只有一个线程能执行自动扩容
        //当自旋变量allocationSpinLock=0时，说明没有其他线程在进行扩容
        if (allocationSpinLock == 0 &&
                //CAS操作，此方法类似乐观锁，当且仅当allocationSpinLockOffset值=0时，返回true，
                // 并将allocationSpinLockOffset值设置成1
                UNSAFE.compareAndSwapInt(this, allocationSpinLockOffset,
                        0, 1)) {
            try {
                //扩容算法参照Java优先级队列PriorityBlockingQueue的算法
                //oldGap<64则扩容新增oldcap+2,否者扩容50%，并且最大为MAX_ARRAY_SIZE
                int newCap = oldCap + ((oldCap < 64) ?
                                        (oldCap + 2) :
                                        (oldCap >> 1));
                if (newCap - MAX_ARRAY_SIZE > 0) {
                    int minCap = oldCap + 1;
                    if (minCap < 0 || minCap > MAX_ARRAY_SIZE)
                        throw new OutOfMemoryError();
                    newCap = MAX_ARRAY_SIZE;
                }
                if (newCap > oldCap && queue == array)
                    newArray = new Object[newCap];
            } finally {
                //最后一定设置自旋变量重置为0
                allocationSpinLock = 0;
            }
        }
        //当newArray为空时，说明当前线程没有执行自动扩容的代码，让出cpu给扩容线程
        if (newArray == null)
            Thread.yield();
        lock.lock();//获得锁，进行数据复制
        if (newArray != null && queue == array) {
            queue = newArray;
            System.arraycopy(array, 0, newArray, 0, oldCap);
        }
    }


    private static <T> void siftUpComparable(int k, T x, Object[] array) {
        Comparable<? super T> key = (Comparable<? super T>) x;
        while (k > 0) {
            int parent = (k - 1) >>> 1;
            Object e = array[parent];
            if (key.compareTo((T) e) >= 0)
                break;
            array[k] = e;
            k = parent;
        }
        array[k] = key;
    }

    private static <T> void siftUpUsingComparator(int k, T x, Object[] array,
                                                  Comparator<? super T> cmp) {
        while (k > 0) {
            int parent = (k - 1) >>> 1;
            Object e = array[parent];
            if (cmp.compare(x, (T) e) >= 0)
                break;
            array[k] = e;
            k = parent;
        }
        array[k] = x;
    }

}