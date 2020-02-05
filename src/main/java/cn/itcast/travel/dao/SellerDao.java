package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

public interface SellerDao {
    //查找商家的信息
    Seller findBySid(int sid);
}
