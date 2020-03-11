package im.practice.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * class_name: $CLASS_NAME$
 * package: im.practice.thread$
 * describe: TODO
 * creat_user: yangyang
 * creat_date: $date$ $time$
 **/
public class ThreadPool {
    public static void main(String[] args){
        ExecutorService workers = Executors.newCachedThreadPool();
        workers.execute(new MyThreadPool(new Runnable(){
            @Override
            public void run() {
                System.out.println("poll执行--------------");
            }
        }));

    }
}
class MyThreadPool implements Runnable{
    private Runnable run;
    public MyThreadPool(Runnable run){
        this.run=run;
    }
    @Override
    public void run() {
        System.out.println("子线程执行--------------");
        run.run();
    }
}
