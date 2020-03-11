package im.practice.aop.springaop.service;

import im.practice.aop.springaop.dao.DbDaoImpl;

/**
 * class_name: $CLASS_NAME$
 * package: im.practice.aop.springaop.service$
 * describe: TODO
 * creat_user: yangyang
 * creat_date: $date$ $time$
 **/
public class UserService {
    private DbDaoImpl dbDao;
    public void addUser(){
        System.out.println("service 中保存用户........");
        try {
            dbDao.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DbDaoImpl getDbDao() {
        return dbDao;
    }

    public void setDbDao(DbDaoImpl dbDao) {
        this.dbDao = dbDao;
    }
}
