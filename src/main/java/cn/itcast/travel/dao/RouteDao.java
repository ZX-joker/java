package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    //根据cid查询总路线数
    int findTotalCount(int cid, String rname);
    //根据开始页码和每页显示数据数查询路线
    List<Route> findAllByPage(int cid, int start, int pageSize, String rname);
    //根据rid查找路线具体信息
    Route findOneByRid(int rid);
}
