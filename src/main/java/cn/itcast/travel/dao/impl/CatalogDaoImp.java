package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.CatalogDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CatalogDaoImp implements CatalogDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    //查找所有分类
    @Override
    public List<Category> findAll() {
        List<Category> list = null;
        String sql = "select * from tab_category";
        try {
            list = template.query(sql,new BeanPropertyRowMapper<Category>(Category.class));
        } catch (DataAccessException e) {
        }
        return list;
    }
}
