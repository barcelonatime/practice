package im.practice.servlet;

import im.practice.App;
import im.practice.annotation.MyController;
import im.practice.annotation.MyMapping;
import im.practice.annotation.ScanBean;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: im.practice.root
 * @description:
 * @author: yangyang
 * @date: 2020-04-09 17:50
 **/
@WebServlet(urlPatterns={"*.do"})
public class MyServlet extends HttpServlet {
    Map<String,Class<?>> classMap=new HashMap<String,Class<?>>(16);
    Map<String, Method> methodMap=new HashMap<String,Method>(16);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String url=req.getServletPath();
            Method method=methodMap.get(url.substring(0,url.indexOf(".")));
            if (method!=null){
                String objpk=method.getDeclaringClass().getName();
                System.out.println(objpk);
                Class clazz=classMap.get(objpk);
                if (clazz!=null){
                    method.invoke(clazz.newInstance());
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            Class appClazz= App.class;
            ScanBean scanBean=App.class.getAnnotation(ScanBean.class);
            String pk=scanBean == null?appClazz.getPackage().getName():scanBean.sacnPackage();
            System.out.println("根路径=="+pk);
            ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
            URL url= classLoader.getResource(pk.replace(".","/"));
            //System.out.println(url.getPath());
            scanFile(url.getPath(),pk);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  void scanFile(String rootPath,String rootPackage) throws  Exception{
        ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
        File file=new File(rootPath);
        File[] subFile=file.listFiles();
        for (File f:subFile){
            if (f.isDirectory()){
                scanFile(f.getAbsolutePath(),rootPackage+"."+f.getName());
            }else {
                if (f.getName().endsWith(".class")){
                    String className=rootPackage+"."+(f.getName().substring(0,f.getName().indexOf(".")));
                    System.out.println(className);
                    Class clazz=classLoader.loadClass(className);
                    if (clazz.isAnnotationPresent(MyController.class)){
                        classMap.put(className,clazz);
                        String path="";
                        if (clazz.isAnnotationPresent(MyMapping.class)){
                            MyMapping myMapping= (MyMapping) clazz.getAnnotation(MyMapping.class);
                            path=myMapping.value();
                        }

                        Method[] methods=clazz.getDeclaredMethods();
                        for (Method method:methods){
                            if (method.isAnnotationPresent(MyMapping.class)){
                                String realPath=path+method.getAnnotation(MyMapping.class).value();
                                System.out.println(realPath);
                                methodMap.put(realPath,method);
                            }
                        }
                    }
                }
            }
        }
    }
}
