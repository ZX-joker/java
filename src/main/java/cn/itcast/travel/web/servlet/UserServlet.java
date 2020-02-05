package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImp;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService userService = new UserServiceImp();

    //注册用户
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //首先校验验证码
        HttpSession session = request.getSession();
        String CHECKCODE_SERVER = (String) session.getAttribute("CHECKCODE_SERVER");
        System.out.println(CHECKCODE_SERVER);
        //及时销毁验证码，防止复用
        session.removeAttribute("CHECKCODE_SERVER");
        String checkCode = request.getParameter("check");
        System.out.println(checkCode);

        if (CHECKCODE_SERVER == null || !CHECKCODE_SERVER.equalsIgnoreCase(checkCode)) {
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            writeValue(info, response);
            return;
        }
        //将注册表单封装为一个用户
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        ResultInfo info = new ResultInfo();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        boolean flag = userService.regist(user);
        if (flag) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
            info.setErrorMsg("用户已存在!");
        }
        writeValue(info, response);
    }

    /**
     * 激活用户
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        System.out.println(code);
        String msg = null;
        if (code != null) {
            boolean flag = userService.activeUser(code);
            if (flag) {
                msg = "已激活成功，快去登录把！！！";
            } else {
                msg = "激活失败";
            }
            response.getWriter().write(msg);
        }
    }

    /**
     * 用户登录
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String CHECKCODE_SERVER = (String) session.getAttribute("CHECKCODE_SERVER");
        //防止验证码复用
        session.removeAttribute("CHECKCODE_SERVER");
        String check = request.getParameter("check");
        ResultInfo info = new ResultInfo();
        if (CHECKCODE_SERVER == null || !CHECKCODE_SERVER.equalsIgnoreCase(check)) {
            info.setFlag(false);
            info.setErrorMsg("验证码错误！！");
            writeValue(info, response);
            return;
        } else {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            User user = userService.login(username, password);
            if (user == null) {
                info.setFlag(false);
                info.setErrorMsg("用户名或密码错误！！");
            }
            if (user != null && !"Y".equals(user.getStatus())) {
                info.setFlag(false);
                info.setErrorMsg("用户未激活，无法登录！");
            }
            if (user != null && "Y".equals(user.getStatus())) {
                info.setFlag(true);
                session.setAttribute("user", user);
            }
            writeValue(info, response);
        }
    }

    /**
     * 查找一个用户
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        writeValue(user, response);
    }

    /**
     * 退出功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //销毁session
        request.getSession().invalidate();
        //重定向到首页
        response.sendRedirect(request.getContextPath() + "/index.html");
    }

    /**
     * 根据UID查找用户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOneByUid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

