package im.practice.thread;

/**
 * class_name: $CLASS_NAME$
 * package: im.practice.thread$
 * describe: TODO
 * creat_user: yangyang
 * creat_date: $date$ $time$
 **/
public class ThreadThread {
    public static void main(String[] args){
       /* MyThread a=new MyThread("A");
        MyThread b=new MyThread("B");
        MyThread c=new MyThread("C");
        a.start();
        b.start();
        c.start();*/
        MyThread mythread=new MyThread();
        //线程a b c启动的时候，执行的是myThread的方法，此时数据共享
        Thread a=new Thread(mythread,"A");
        Thread b=new Thread(mythread,"B");
        Thread c=new Thread(mythread,"C");
        b.start();
        a.start();
        c.start();



        //System.out.println("主线程结束---------------");
        //System.exit(-1);

    }
}
class MyThread extends Thread{
    private Integer count=5;

    public MyThread(){

    }
    public MyThread(String name){
        this.setName(name);
    }
    @Override
    public void run() {
        System.out.println("子线程开始运行-------------");
        while(count>0){
            count--;
            System.out.println("线程名称："+this.currentThread().getName()+"  count="+count);
        }
        System.out.println("子线程结束--------------------");
    }
}