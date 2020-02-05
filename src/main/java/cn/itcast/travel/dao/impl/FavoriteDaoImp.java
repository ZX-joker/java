package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class FavoriteDaoImp implements FavoriteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 查询路线的收藏次数
     * @param rid
     * @return
     */
    @Override
    public int countByRid(int rid) {
        String sql = "select count(*) from tab_favorite where rid = ?";
        int count = 0;
        try {
            count = template.queryForObject(sql,Integer.class,rid);
        } catch (DataAccessException e) {

        }
        return count;
    }

    /**
     * 判断线路是否被用户收藏过
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public Favorite findFavoriteByRidAnduid(int rid, int uid) {
        String sql = "select * from tab_favorite where rid = ? and uid = ?";
        Favorite favorite = null;
        try {
            favorite = template.queryForObject(sql,new BeanPropertyRowMapper<Favorite>(Favorite.class),rid,uid);
        } catch (DataAccessException e) {

        }
        return favorite;
    }

    /**
     * 收藏路线
     * @param rid
     * @param uid
     */
    @Override
    public void addFavorite(int rid, int uid) {
        String sql = "insert into tab_favorite values(?,?,?)";
        template.update(sql,rid,new Date(),uid);
    }
}
