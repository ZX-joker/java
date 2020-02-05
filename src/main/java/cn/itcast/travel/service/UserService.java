package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    //注册用户
    boolean regist(User user);
    //激活用户
    boolean activeUser(String code);

    User login(String username, String password);
}
