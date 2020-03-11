package im.practice.aop.springaop.aspect;

/**
 * class_name: $CLASS_NAME$
 * package: im.practice.aop.springaop.aspect$
 * describe: TODO
 * creat_user: yangyang
 * creat_date: $date$ $time$
 **/
public class Transactions {
    public void startTransactions(){
        System.out.println("开启事务...............");
    }
    
    public void commit(){
        System.out.println("事务已经提交............");
    }
    
    public void rollBack(){
        System.out.println("事务已经回滚.............");
    }
}
