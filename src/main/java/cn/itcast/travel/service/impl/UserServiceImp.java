package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImp;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImp implements UserService {
    private UserDao userDao = new UserDaoImp();
    /**
     * 注册用户
     * @param user
     * @return
     */
    @Override
    public boolean regist(User user) {
        //先查找注册用户是否已存在
        User u = userDao.findByUsername(user.getUsername());
        if (u != null){
            return false;
        }else {
            user.setStatus("N");
            user.setCode(UuidUtil.getUuid());
            //把用户存入数据库
            userDao.save(user);
            String content = "<a href='http://localhost:8080/travel03/user/active?code="+user.getCode()+"'>点击激活【黑马旅游网】</a>";
            MailUtils.sendMail(user.getEmail(),content,"激活邮件");
            return true;
        }

    }

    /**
     * 激活用户
     * @param code
     * @return
     */
    @Override
    public boolean activeUser(String code) {
        //根据激活码查找用户
        User user = userDao.findUserByCode(code);
        boolean flag = false;
        if (user != null){
            flag = true;
            userDao.activeUser(code);
        }
        return flag;
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public User login(String username, String password) {
        User user = userDao.findUserByUserNameAndPassWord(username,password);
        return user;
    }
}
