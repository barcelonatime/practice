package im.practice.io.nio.reactor;

/**
 * class_name: $
 * package: im.practice.io.nio.reactor$
 * describe: TODO
 * creat_user: yangyang
 * creat_date: $ $
 **/
public class Main {
    public static void main(String[] args) {
        try {
            NioReServer nioReServer=new NioReServer();
            //new Thread(nioReServer).start();
            nioReServer.listener();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
