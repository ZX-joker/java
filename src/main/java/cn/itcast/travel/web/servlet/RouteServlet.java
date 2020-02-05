package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImp;
import cn.itcast.travel.service.impl.RouteServiceImp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImp();
    private FavoriteService favoriteService = new FavoriteServiceImp();
    /**
     * 分页查询所有路线
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findAllByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取分页路径
        String cidstr = request.getParameter("cid");
        //获取当前页
        String currentPageStr = request.getParameter("currentPage");
        int cid = 0;
        if (cidstr != null && cidstr.length() != 0 && !"null".equals(cidstr)){
            cid = Integer.parseInt(cidstr);
        }
        int currentPage = 0;
        if (currentPageStr != null && currentPageStr.length() != 0 && !"null".equals(currentPageStr)){
            currentPage = Integer.parseInt(currentPageStr);
        }else {
            //初始化当前页
            currentPage = 1;
        }
        //获取每页显示的数据数
        String pageSizeStr = request.getParameter("pageSize");
        int pageSize = 0;
        if (pageSizeStr != null && pageSizeStr.length() != 0 && !"null".equals(pageSizeStr)){
            pageSize = Integer.parseInt(pageSizeStr);
        }else {
            pageSize = 8;
        }
        String rname = request.getParameter("rname");
        rname = new String(rname.getBytes("iso-8859-1"),"utf-8");
        PageBean<Route> pb = routeService.findAllByPage(cid,currentPage,pageSize,rname);
        writeValue(pb,response);
    }

    /**
     * 根据rid查找路线具体信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOneByRid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        Route route = routeService.findOneByrid(Integer.parseInt(rid));
        writeValue(route,response);
    }

    /**
     * 判断线路是否被用户收藏过
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user == null){
            uid = 0;
        }else {
            uid = user.getUid();
        }
        boolean flag = favoriteService.isFavorite(Integer.parseInt(rid),uid);
        System.out.println(flag);
        writeValue(flag,response);
    }

    /**
     * 添加收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid = 0;
        if (user == null){
            return;
        }else {
            uid = user.getUid();
        }
        //收藏路线
        favoriteService.addFavorite(rid,uid);
    }
}
