package im.practice.controller;

import im.practice.annotation.MyController;
import im.practice.annotation.MyMapping;

/**
 * @program: im.practice.root
 * @description:
 * @author: yangyang
 * @date: 2020-04-09 10:21
 **/
@MyController
@MyMapping("/76")
public class MvcController {
    @MyMapping("/search")
    public void search(){
        System.out.println("search方法被执行---------");
    }
    @MyMapping("/add")
    public void add(){
        System.out.println("add 方法被执行------------");
    }
    @MyMapping("/delete")
    public void delete(){
        System.out.println("delete 方法被执行------------");
    }
}
