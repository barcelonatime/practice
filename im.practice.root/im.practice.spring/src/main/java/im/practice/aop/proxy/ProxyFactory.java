package im.practice.aop.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * class_name: $CLASS_NAME$
 * package: im.practice.aop.proxy$
 * describe: TODO
 * creat_user: yangyang
 * creat_date: $date$ $time$
 **/
public class ProxyFactory {
    private Object targetObject;
    private BeforeAdvice beforeAdvice;
    private AfterAdvice afterAdvice;

    public Object createProxy(){
        ClassLoader classLoader=this.getClass().getClassLoader();
        Class[] interfaces=targetObject.getClass().getInterfaces();
        InvocationHandler invocationHandler=new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if(beforeAdvice!=null){
                    beforeAdvice.befor();
                }

                Object result=method.invoke(targetObject,args);
                if(afterAdvice!=null){
                    afterAdvice.after();
                }

                return result;
            }
        };

        Object obj=Proxy.newProxyInstance(classLoader,interfaces,invocationHandler);
        return obj;
    }




    public Object getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(Object targetObject) {
        this.targetObject = targetObject;
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
