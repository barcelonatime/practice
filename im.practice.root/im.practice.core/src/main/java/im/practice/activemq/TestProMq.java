package im.practice.activemq;

/**
 * class_name: $CLASS_NAME$
 * package: im.practice.activemq$
 * describe: TODO
 * creat_user: yangyang
 * creat_date: $date$ $time$
 **/
public class TestProMq {
    public static void main(String[] args){
        Producter producter = new Producter("tcp://10.221.122.91:61616","P-1");
        producter.init();
        TestProMq testMq = new TestProMq();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Thread 1
        new Thread(testMq.new ProductorMq(producter)).start();
        //Thread 2
        //new Thread(testMq.new ProductorMq(producter)).start();
        //Thread 3
        //new Thread(testMq.new ProductorMq(producter)).start();
        //Thread 4
        //new Thread(testMq.new ProductorMq(producter)).start();
        //Thread 5
        //new Thread(testMq.new ProductorMq(producter)).start();
    }

    private class ProductorMq implements Runnable{
        Producter producter;
        public ProductorMq(Producter producter){
            this.producter = producter;
        }

        @Override
        public void run() {
            while(true){
                try {
                    producter.sendMessage("Collect");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
