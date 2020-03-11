package im.practice;

import im.practice.aop.cglib.CglibFactory;
import im.practice.aop.cglib.SomeService;
import im.practice.aop.proxy.*;
import im.practice.aop.springaop.dao.DbDaoImpl;
import im.practice.aop.springaop.introduction.Einstein;
import im.practice.aop.springaop.introduction.Mathematician;
import im.practice.aop.springaop.service.UserService;
import im.practice.ioc.OneInterface;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Unit test for simple App.
 */
public class AppTest {
    private ClassPathXmlApplicationContext context;

    @Before
    public void before(){
        context=new ClassPathXmlApplicationContext("classpath*:spring-*.xml");
        context.start();
    }
    @Test
    public void testIntroduction(){
        Einstein einstein=(Einstein)context.getBean("einstein");
        einstein.invent();
        Mathematician mathematician=(Mathematician)context.getBean("einstein");
        mathematician.calculate();
    }


    @Test
    public void testAdvice2(){
        UserService userService=(UserService) context.getBean("userService");
        userService.addUser();
    }


    @Test
    public void testAdvice(){
        DbDaoImpl dbDao=(DbDaoImpl)context.getBean("dbDaoImpl");
        try {
            dbDao.save();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Test
    public void testSay(){
        OneInterface in=(OneInterface)context.getBean("oneInterface");
        in.say("wo ca");
        OneInterface in2=(OneInterface)context.getBean("oneInterface2");
        
        System.out.println(in);
        System.out.println(in.getContentThreadPool());
        System.out.println(in2);
        System.out.println(in2.getContentThreadPool());

    }

    @Test
    public void testProxy(){
        ClassLoader classLoader=this.getClass().getClassLoader();
        InvocationHandler invocationHandler=new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("你好--------");
                return args[0];
            }
        };
        Object obj=Proxy.newProxyInstance(classLoader,new Class[]{InterfaceOne.class,InterfaceTwo.class},invocationHandler);
        InterfaceOne one=(InterfaceOne)obj;
        InterfaceTwo two=(InterfaceTwo)obj;
        //one.one();
        //two.two();
        //System.out.println(one.toString());
        //System.out.println(one.getClass());
        String name=one.aaa("yy",31);
        System.out.println(name);

    }
    @Test
    public void testAopProxy(){
        ProxyFactory proxyFactory=new ProxyFactory();
        ManWaiter manWaiter=new ManWaiter();
        proxyFactory.setTargetObject(manWaiter);
        proxyFactory.setBeforeAdvice(new BeforeAdvice() {
            @Override
            public void befor() {
                System.out.println("请求开始服务................");
            }
        });

        proxyFactory.setAfterAdvice(new AfterAdvice() {
            @Override
            public void after() {
                System.out.println("您的服务已经结束..............");
            }
        });

        Waiter object=(Waiter) proxyFactory.createProxy();
        String desc=object.service();
        System.out.println(desc);

    }
    @Test
    public void testCglibProxy(){
        CglibFactory proxyFactory=new CglibFactory();
        proxyFactory.setBeforeAdvice(new BeforeAdvice() {
            @Override
            public void befor() {
                System.out.println("befor is exceue............");
            }
        });
        proxyFactory.setAfterAdvice(new AfterAdvice() {
            @Override
            public void after() {
                System.out.println("after is exceus...............");
            }
        });
        SomeService proxy=proxyFactory.createCglib();
        String result=proxy.doFisrt();
        System.out.println(result);
        proxy.doSeconed();
    }


    @After
    public void after(){
        context.destroy();
    }
}
