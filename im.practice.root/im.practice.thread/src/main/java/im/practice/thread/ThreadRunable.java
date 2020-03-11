package im.practice.thread;

/**
 * class_name: $CLASS_NAME$
 * package: im.practice.thread$
 * describe: TODO
 * creat_user: yangyang
 * creat_date: $date$ $time$
 **/
public class ThreadRunable {
    public static void main(String[] args){
        MyThreadRunable a=new MyThreadRunable("A");
        MyThreadRunable b=new MyThreadRunable("B");
        MyThreadRunable c=new MyThreadRunable("C");
        new Thread(a,"A").start();
        new Thread(a,"B").start();
        new Thread(a,"C").start();

    }
}
class MyThreadRunable implements Runnable{
    private Integer count=5;
    private String name;
    public MyThreadRunable(){

    }

    public MyThreadRunable(String name){
        this.name=name;
    }
    @Override
    public void run() {
        System.out.println("子线程开始运行-------------");
        while(count>0){
            count--;
            System.out.println("线程名称："+this.name+"  count="+count);
        }
    }
}
