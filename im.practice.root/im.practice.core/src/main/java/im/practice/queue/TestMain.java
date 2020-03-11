package im.practice.queue;

import java.util.Date;
import java.util.List;

/**
 * class_name: $
 * package: im.practice.queue$
 * describe: TODO
 * creat_user: yangyang
 * creat_date: $ $
 **/
public class TestMain {
    private final static BlockPriorityQueue<Content> queue=new BlockPriorityQueue<>();
    public static void main(String[] args) throws Exception{
        //初始化队列
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<1000000;i++){
                    Content content=new Content();
                    content.setId(i+"");
                    content.setName("yytest-"+i);
                    queue.offer(content);
                }

            }
        }).start();
        Thread.sleep(2000);
        List<Content> list=queue.getQueue();
        for(int i=0;i<10;i++){
            System.out.println(list.get(i).toString());
        }
        System.out.println("#############################################");
        //设置8号元素为最高优先级
        new Thread(new Runnable() {
            @Override
            public void run() {
                Content content=new Content();
                content.setId("999999");
                content.setName("xxxx");
                Long start=new Date().getTime();
                boolean t=queue.setPriority(content);
                System.out.println("总耗时："+(new Date().getTime()-start)+","+t);
            }
        }).start();
        //出队最高优先级元素
        new Thread(new Runnable() {
            @Override
            public void run() {
                Content c=queue.poll();
                System.out.println("poll==="+c.toString());
            }
        }).start();
        Thread.sleep(2000);
        list=queue.getQueue();
        for(int i=0;i<10;i++){
            System.out.println(list.get(i).toString());
        }
        System.out.println("#############################################");
        //把5号元素设置为第3优先级
        new Thread(new Runnable() {
            @Override
            public void run() {
                Content content=new Content();
                content.setId("5");
                content.setName("xxxx");
                queue.setPriority(content,2);
            }
        }).start();
        Thread.sleep(2000);
        list=queue.getQueue();
        for(int i=0;i<10;i++){
            System.out.println(list.get(i).toString());
        }
    }
}
