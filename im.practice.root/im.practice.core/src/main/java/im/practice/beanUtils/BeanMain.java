package im.practice.beanUtils;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * class_name: $CLASS_NAME$
 * package: im.practice.beanUtils$
 * describe: TODO
 * creat_user: yangyang
 * creat_date: $date$ $time$
 **/
public class BeanMain {
    public static void main(String[] args){
        BeanTest b1=new BeanTest();
        b1.setName("yy1");
        List<String> list=new ArrayList<>();
        list.add("1");list.add("2");
        Map<String,List<String>> map=new HashMap<String,List<String>>();
        map.put("test",list);
        b1.setMap(map);
        BeanTest b2=new BeanTest();
        try {
            BeanUtils.copyProperties(b2,b1);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        b1.setMap(new HashMap<String, List<String>>());
        System.out.println(b2.getName());


    }
}
