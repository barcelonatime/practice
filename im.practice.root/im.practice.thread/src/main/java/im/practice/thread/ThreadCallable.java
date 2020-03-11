package im.practice.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * class_name: $CLASS_NAME$
 * package: im.practice.thread$
 * describe: TODO
 * creat_user: yangyang
 * creat_date: $date$ $time$
 **/
public class ThreadCallable {
    public static void main(String[] args){
        MyThreadCallable t1=new MyThreadCallable();
        FutureTask<Integer> future=new FutureTask<Integer>(t1);
        new Thread(future).start();
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("callable 主线程结束-------------");
    }
}
class MyThreadCallable implements Callable<Integer>{
    @Override
    public Integer call() throws Exception {
        System.out.println("callable 子线程开始执行----------------");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("callable 子线程执行结束-----------------");
        return 5;
    }
}
