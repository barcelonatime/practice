package im.practice.aop.cglib;

import im.practice.aop.proxy.AfterAdvice;
import im.practice.aop.proxy.BeforeAdvice;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * class_name: $CLASS_NAME$
 * package: im.practice.aop.cglib$
 * describe: TODO
 * creat_user: yangyang
 * creat_date: $date$ $time$
 **/
public class CglibFactory implements MethodInterceptor {
    private BeforeAdvice beforeAdvice;
    private AfterAdvice afterAdvice;

    public SomeService createCglib(){
        Enhancer enhancer = new Enhancer();
        //将目标类设置为父类，cglib动态代理增强的原理就是子类增强父类,cglib不能增强目标类为final的类
        //因为final类不能有子类
        enhancer.setSuperclass(SomeService.class);
        //设置回调接口,这里的MethodInterceptor实现类回调接口，而我们又实现了MethodInterceptor,其实
        //这里的回调接口就是本类对象,调用的方法其实就是intercept()方法
        enhancer.setCallback(this);
        //create()方法用于创建cglib动态代理对象
        return (SomeService) enhancer.create();

    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if(beforeAdvice!=null){
            beforeAdvice.befor();
        }
        Object result=methodProxy.invokeSuper(o,objects);
        if(result!=null){
            result=((String)result).toUpperCase();
        }
        if(afterAdvice!=null){
            afterAdvice.after();
        }
        return result;
    }

    public BeforeAdvice getBeforeAdvice() {
        return beforeAdvice;
    }

    public void setBeforeAdvice(BeforeAdvice beforeAdvice) {
        this.beforeAdvice = beforeAdvice;
    }

    public AfterAdvice getAfterAdvice() {
        return afterAdvice;
    }

    public void setAfterAdvice(AfterAdvice afterAdvice) {
        this.afterAdvice = afterAdvice;
    }
}
