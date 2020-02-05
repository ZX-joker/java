package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface RouteService {
    //分页查询所有路线
    PageBean<Route> findAllByPage(int cid, int currentPage, int pageSize, String rname);
    //根据rid查找路线具体信息
    Route findOneByrid(int parseInt);

}
