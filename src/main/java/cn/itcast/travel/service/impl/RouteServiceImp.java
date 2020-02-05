package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.Route_img_Dao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImp;
import cn.itcast.travel.dao.impl.RouteDaoImp;
import cn.itcast.travel.dao.impl.Route_img_DaoImp;
import cn.itcast.travel.dao.impl.SellserDaoImp;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImp implements RouteService {
    private RouteDao routeDao = new RouteDaoImp();
    private Route_img_Dao imgDao = new Route_img_DaoImp();
    private SellerDao sellerDao = new SellserDaoImp();
    private FavoriteDao favoriteDao = new FavoriteDaoImp();
    /**
     * 分页查询所有路线
     * @param cid
     * @param currentPage
     * @param pageSize
     * @param rname
     * @return
     */
    @Override
    public PageBean<Route> findAllByPage(int cid, int currentPage, int pageSize, String rname) {
        PageBean<Route> pb = new PageBean<Route>();
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);
        //查找总记录数
        int totalCount = routeDao.findTotalCount(cid,rname);
        pb.setTotalCount(totalCount);
        //求出总页数
        int totalPages = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize + 1);
        pb.setTotalPages(totalPages);
        //根据开始页码和每页显示数据数查询路线
        int start = (currentPage - 1) * pageSize;
        List<Route> routes = routeDao.findAllByPage(cid,start,pageSize,rname);
        pb.setList(routes);

        return pb;
    }

    /**
     * 根据rid查找路线具体信息
     * @param rid
     * @return
     */
    @Override
    public Route findOneByrid(int rid) {
        Route route = routeDao.findOneByRid(rid);
        Seller seller = sellerDao.findBySid(route.getSid());
        List<RouteImg> imgs = imgDao.findByRid(rid);
        //查询路线的收藏次数
        int count = favoriteDao.countByRid(rid);
        route.setSeller(seller);
        route.setRouteImgList(imgs);
        route.setCount(count);
        return route;
    }


}
