package im.practice.thread;

/**
 * class_name: $CLASS_NAME$
 * package: im.practice.thread$
 * describe: TODO
 * creat_user: yangyang
 * creat_date: $date$ $time$
 **/
public class ThreadWait {
    public static void main(String[] args){
        MyThreadWait mw=new MyThreadWait();
        new Thread(mw).start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (mw){
            System.out.println("线程notify-------");
            mw.notify();
        }

    }
}

class MyThreadWait implements Runnable{
    @Override
    public void run() {
        System.out.println("线程开始运行---------");
        synchronized (this){
            try {
                System.out.println("线程wait-------------");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("线程运行结束------------");
    }
}
