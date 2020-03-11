package im.practice.aop.proxy;

/**
 * class_name: $CLASS_NAME$
 * package: im.practice.aop.proxy$
 * describe: TODO
 * creat_user: yangyang
 * creat_date: $date$ $time$
 **/
public class ManWaiter implements Waiter {
    @Override
    public String service() {
        System.out.println("我是男服务员，可以给你服务...................");
        return "男服务员服务就是爽";
    }
}
