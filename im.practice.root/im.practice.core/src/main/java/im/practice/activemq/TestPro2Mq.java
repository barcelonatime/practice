package im.practice.activemq;

/**
 * class_name: $CLASS_NAME$
 * package: im.practice.activemq$
 * describe: TODO
 * creat_user: yangyang
 * creat_date: $date$ $time$
 **/
public class TestPro2Mq {
    public static void main(String[] args){
        Producter producter = new Producter("tcp://10.221.122.91:61617","P-2");
        producter.init();
        TestPro2Mq testMq = new TestPro2Mq();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Thread 1
        new Thread(testMq.new Productor2Mq(producter)).start();
        //Thread 2
        //new Thread(testMq.new ProductorMq(producter)).start();
        //Thread 3
        //new Thread(testMq.new ProductorMq(producter)).start();
        //Thread 4
        //new Thread(testMq.new ProductorMq(producter)).start();
        //Thread 5
        //new Thread(testMq.new ProductorMq(producter)).start();
    }

    private class Productor2Mq implements Runnable{
        Producter producter;
        public Productor2Mq(Producter producter){
            this.producter = producter;
        }

        @Override
        public void run() {
            while(true){
                try {
                    producter.sendMessage("Jaycekon-MQ");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
