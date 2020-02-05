package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;

import java.util.List;

public interface CatalogDao {
    //查找所有分类
    List<Category> findAll();
}
