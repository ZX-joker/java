package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface Route_img_Dao {
    //查询路径的图片信息
    List<RouteImg> findByRid(int rid);
}
