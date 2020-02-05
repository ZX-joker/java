package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;

import java.util.List;

public interface CatalogService {
    //查找所有分类
    List<Category> findAll();
}
