package im.practice.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

public class zkClient {

    public static void main(String[] args) {
        try {
            CuratorFramework client = CuratorFrameworkFactory.newClient("10.221.122.81:2181,10.221.122.81:2182,10.221.122.81:2183", new RetryNTimes(10, 5000));
            client.start();
            byte[] bytes=client.getData().forPath("/yytest");

            System.out.println(new String(bytes));


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
