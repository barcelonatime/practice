package im.practice.ioc;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ContentThreadPool {
	private int threadMinCount=5;
    private int threadMaxCount=10;
    private int checkPeriod=5;


    public void setThreadMinCount(int threadMinCount) {
        this.threadMinCount = threadMinCount;
    }

    public void setThreadMaxCount(int threadMaxCount) {
        this.threadMaxCount = threadMaxCount;
    }

    public void setCheckPeriod(int checkPeriod) {
        this.checkPeriod = checkPeriod;
    }

    private ThreadPoolExecutor threadPool = null;

    private BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
    public void init() {
        threadPool = new ThreadPoolExecutor(threadMinCount, threadMaxCount, checkPeriod,
                TimeUnit.SECONDS, workQueue);
    }

    public void execute(Runnable command) {
        if (threadPool != null) {
            threadPool.execute(command);
            if(workQueue.size() > 1000){
            	//logger.info(" ContentThreadPool队列长度: " + workQueue.size());
            }
            
        } else {
            this.init();
            threadPool.execute(command);
        }
    }
}
