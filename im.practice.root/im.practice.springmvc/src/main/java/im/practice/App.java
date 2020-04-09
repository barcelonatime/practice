package im.practice;

import im.practice.annotation.MyController;
import im.practice.annotation.MyMapping;
import im.practice.annotation.ScanBean;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
@ScanBean(sacnPackage="im.practice")
public class App 
{
    static Map<String,Class<?>> classMap=new HashMap<String,Class<?>>(16);
    static Map<String,Method> methodMap=new HashMap<String,Method>(16);
    public static void main( String[] args )
    {
        try {
            Class appClazz=App.class;
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

    public static void scanFile(String rootPath,String rootPackage) throws  Exception{
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
