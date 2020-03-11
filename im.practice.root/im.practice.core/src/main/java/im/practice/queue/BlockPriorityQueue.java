package im.practice.queue;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 说明：自定义队列,支持元素优先级调整(只能调整到最高优先级)
 * 总体设计思路：基于LinkedList双向链表,易插入和删除,并且LinkedList本身可以作为队列使用，
 *             提供了很多队列的方法，比如先进先出,后进先出等，本队列采用先进先出策略。
 *             且基于双向链表的LinkedList无需实现自动扩容机制
 * 实现的方法：入队，出队，调整优先级，获取整个队列
 * 注意：队列中的元素对象必须重新equals和hashCode方法
 * @author yangyang
 * @date 2019-06-11 15:41:16
 */
public class BlockPriorityQueue<E> implements Serializable {
    private static final long serialVersionUID = -5769413626626097540L;
    /**
     * 队列双向链表
     */
    private transient LinkedList<Object> queue;
    /**
     * 队列中元素个数
     */
    private transient int size;
    /**
     * 可重入锁，用来实现队列同步机制，相比synchronized更灵活
     */
    private final ReentrantLock lock;
    private final Condition notEmpty;

    public BlockPriorityQueue(){
        this.lock = new ReentrantLock();
        this.notEmpty = lock.newCondition();
        this.queue = new LinkedList<Object>();
    }
    /**
     * 函数说明：添加一个元素到队列尾部
     * 使用方法：
     *
     * @param e
     * @return 成功返回true,是否false
     * @author yangyang
     * @date 2019-06-11 16:25:07
     */
    public boolean offer(E e) {
        if (e == null)
            throw new NullPointerException();
        final ReentrantLock lock = this.lock;
        lock.lock();
        int n=size;
        boolean flag=true;
        try {
            flag=queue.offerLast(e);
            size = n + 1;
        } finally {
            lock.unlock();
        }
        return flag;
    }
    /**
     * 函数说明：从队列头出队
     * 使用方法：
     *
     * @param
     * @return 队列为空返回null，否则返回队列第一个元素
     * @author yangyang
     * @date 2019-06-11 20:15:53
     */
    public E poll(){
        final ReentrantLock lock=this.lock;
        lock.lock();
        try {
            int n = size - 1;
            if (n < 0)
                return null;
            else {
                E x=(E)queue.pollFirst();
                size=n;
                return x;
            }
        } finally {
            lock.unlock();
        }
    }
    /**
     * 函数说明：设置队列中某个元素优先级为最高
     * 使用方法：
     *
     * @param e
     * @return
     * @author yangyang
     * @date 2019-06-11 20:27:57
     */
    public boolean setPriority(E e){
        final ReentrantLock lock=this.lock;
        lock.lock();
        try {
            int n = size - 1;
            if (n < 0){
                return false;
            }else {
                boolean isHave=false;
                Iterator iterator =queue.iterator();
                Object obj,temp=null;
                while (iterator.hasNext()){
                    obj=iterator.next();
                    if(e.equals(obj)){
                        temp=obj;
                        iterator.remove();
                        isHave=true;
                        break;
                    }
                }
                if(isHave){
                    queue.offerFirst(temp);
                    return true;
                }
            }
        } finally {
            lock.unlock();
        }
        return false;
    }
    /**
     * 函数说明：设置队列中某个元素优先级,可设置级别
     * 使用方法：
     *
     * @param e
     * @param index 把元素迁移到那个位置
     * @return
     * @author yangyang
     * @date 2019-06-11 20:30:27
     */
    public boolean setPriority(E e,int index){
        final ReentrantLock lock=this.lock;
        lock.lock();
        try {
            int n = size - 1;
            if (n < 0){
                return false;
            }else {
                boolean isHave=false;
                Iterator iterator =queue.iterator();
                Object obj,temp=null;
                while (iterator.hasNext()){
                    obj=iterator.next();
                    if(e.equals(obj)){
                        temp=obj;
                        iterator.remove();
                        isHave=true;
                        break;
                    }
                }
                if(isHave){
                    queue.add(index,temp);
                }
            }
        } finally {
            lock.unlock();
        }
        return true;
    }
    /**
     * 函数说明：获得整个队列
     * 使用方法：
     *
     * @param
     * @return
     * @author yangyang
     * @date 2019-06-11 20:32:29
     */
    public LinkedList getQueue(){
        return queue;
    }

}
