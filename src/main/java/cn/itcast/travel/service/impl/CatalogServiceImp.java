package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CatalogDao;
import cn.itcast.travel.dao.impl.CatalogDaoImp;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CatalogService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CatalogServiceImp implements CatalogService{
    private CatalogDao catalogDao = new CatalogDaoImp();
    /**
     * 查找所有分类
     * @return
     */
    @Override
    public List<Category> findAll() {
        Jedis jedis = JedisUtil.getJedis();

        Set<Tuple> catagories = jedis.zrangeWithScores("catagory", 0, -1);
        List<Category> list = null;
        if (catagories == null || catagories.size() == 0){
            list = catalogDao.findAll();
            for (Category category : list){
                jedis.zadd("catagory",category.getCid(),category.getCname());
            }
        }else {
            list = new ArrayList<Category>();
            for (Tuple tuple : catagories){
                Category category = new Category();
                category.setCid((int) tuple.getScore());
                category.setCname(tuple.getElement());
                list.add(category);
            }
        }

        return list;
    }
}
