package im.practice.thread;

/**
 * class_name: $CLASS_NAME$
 * package: im.practice.thread$
 * describe: TODO
 * creat_user: yangyang
 * creat_date: $date$ $time$
 **/
public class ThreadNotify {
    public static void main(String[] args){
        MyService ms=new MyService();
        MyService ms2=new MyService();
        MyThreadNotify1 m1=new MyThreadNotify1(ms);
        MyThreadNotify2 m2=new MyThreadNotify2(ms2);
        MyThreadNotify3 m3=new MyThreadNotify3(ms2);
        new Thread(m1).start();
        new Thread(m2).start();
        new Thread(m3).start();

    }
}
class MyThreadNotify1 implements Runnable{
    public MyService ms;
    public MyThreadNotify1(MyService ms){
        this.ms=ms;
    }
    
    @Override
    public void run() {
        ms.service1();
    }
}
class MyThreadNotify2 implements Runnable{
    public MyService ms;
    public MyThreadNotify2(MyService ms){
        this.ms=ms;
    }

    @Override
    public void run() {
        ms.service2();
    }
}
class MyThreadNotify3 implements Runnable{
    public MyService ms;
    public MyThreadNotify3(MyService ms){
        this.ms=ms;
    }

    @Override
    public void run() {
        ms.service3();
    }
}
class  MyService {
    public synchronized void service1(){
        try {
            System.out.println("service 1 wait--------");
            wait();
            System.out.println("service 1 end-----------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public synchronized void service2(){
        try {
            System.out.println("service 2 wait---------");
            wait();
            System.out.println("service 2 end------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public synchronized void service3(){
        try {
            System.out.println("service 3 notify---------");
            notifyAll();
            System.out.println("service 3 notify end------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}