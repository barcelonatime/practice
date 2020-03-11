package im.practice.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;

/**
 * class_name: $
 * package: im.practice.ftp$
 * describe: TODO
 * creat_user: yangyang
 * creat_date: $ $
 **/
public class FtpTest {
    public static void main(String[] args) {
        try {
            FTPClient ftpClient = new FTPClient();
            ftpClient.connect("10.221.122.93", 21);
            ftpClient.login("ftpupload", "201707");
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.changeWorkingDirectory("/business/Video/20190311/");
            //String[] result=ftpClient.doCommandAsStrings("dir","111.ts");
            //System.out.println(result);
            FTPFile[] files=ftpClient.listFiles();
            System.out.println(files[0].getSize());
            System.out.println(files.length);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
