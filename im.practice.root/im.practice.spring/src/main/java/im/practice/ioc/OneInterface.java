package im.practice.ioc;

/**
 * class_name: $CLASS_NAME$
 * package: im.practice.spring.interfaces$
 * describe: TODO
 * creat_user: yangyang
 * creat_date: $date$ $time$
 **/
public abstract class OneInterface {
    protected ContentThreadPool contentThreadPool;
    public abstract void say(String word);

    public ContentThreadPool getContentThreadPool() {
        return contentThreadPool;
    }

    public void setContentThreadPool(ContentThreadPool contentThreadPool) {
        this.contentThreadPool = contentThreadPool;
    }
}
