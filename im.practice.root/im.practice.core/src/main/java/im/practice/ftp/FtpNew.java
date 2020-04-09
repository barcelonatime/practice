package im.practice.ftp;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * class_name: $
 * package: im.practice.ftp$
 * describe: TODO
 * creat_user: yangyang
 * creat_date: $ $
 **/
public class FtpNew {
    public static void main(String[] args) {
        FTPClient ftpClient = new FTPClient();

        String filename ="123.xml";
        String resultFileURL = "ftp://ftptest:ftptest@192.168.12.44:21//home/ftptest/upload";


        String requestXml="<ADI xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "  <Objects></Objects>\n" +
                "</ADI>";
        String[] arr = ftpUrl(resultFileURL);

        try {
            ftpClient.connect(arr[2], Integer.parseInt(arr[3]));
            ftpClient.login(arr[0], arr[1]);
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {

                ftpClient.disconnect();
            } else {
                // 设置PassiveMode传输
                ftpClient.enterLocalPassiveMode();
                // 设置以二进制流的方式传输
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

                if (ftpClient.changeWorkingDirectory(arr[4])) {
                    //Boolean f=ftpClient.storeFile(new String(filename.getBytes(), "iso8859-1"), IOUtils.toInputStream(requestXml, "utf-8"));
                    //System.out.println(f);
                } else {

                    boolean isSucc = ftpClient.makeDirectory(arr[4]);
                    if (ftpClient.changeWorkingDirectory(arr[4])){
                        //ftpClient.storeFile(new String(filename.getBytes(), "iso8859-1"), IOUtils.toInputStream(requestXml, "utf-8"));
                    }else{

                    }

                }
            }
        } catch (Exception e) {

        } finally {
            //logger.info("进入finally");
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                //logger.error("关闭连接异常", e);
            }
        }
    }

    public static String[] ftpUrl(String url) {
        //logger.info("ftpurl:"+url);
        if (url.endsWith("/"))
            url = StringUtils.removeEnd(url, "/");
        int uStart = "ftp://".length();
        int uEnd = StringUtils.indexOf(url, ":", uStart);
        String loginName = StringUtils.substring(url, uStart, uEnd);
        int atIndex = StringUtils.indexOf(url, "@", uStart);
        int pStart = uEnd + 1;
        int pEnd = atIndex;
        String passWord = StringUtils.substring(url, pStart, pEnd);
        int ipStart = atIndex + 1;
        int ipEnd = StringUtils.indexOf(url, ":", atIndex);
        String host = StringUtils.substring(url, ipStart, ipEnd);
        int portStart = ipEnd + 1;
        int portEnd = StringUtils.indexOf(url, "/", atIndex);
        if (portEnd == StringUtils.INDEX_NOT_FOUND)
            portEnd = url.length();
        String port = StringUtils.substring(url, portStart, portEnd);
        String rPath = StringUtils.substring(url, portEnd + 1);
        String[] paths = StringUtils.split(rPath, "/");

        String[] arr = new String[]{loginName,passWord,host,port,rPath};

        return arr;
    }
}
