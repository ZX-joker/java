package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    //按用户名查找用户
    User findByUsername(String username);
    //把用户存入数据库
    void save(User user);
    //激活用户
    void activeUser(String code);
    //根据激活码查找用户
    User findUserByCode(String code);
    //根据用户名和密码查找用户
    User findUserByUserNameAndPassWord(String username, String password);
}
