package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImp implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     * 根据cid查询总路线数
     * @param cid
     * @param rname
     * @return
     */
    @Override
    public int findTotalCount(int cid, String rname) {
        String sql = "select count(*) from tab_route where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if ((cid != 0 && rname == null) || (cid != 0 && rname.length() == 0) || (cid != 0 && "null".equals(rname))){
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if (rname != null && rname.length() != 0 && !"null".equals(rname)){
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }

        sql = sb.toString();

        int n = template.queryForObject(sql,Integer.class,params.toArray());

        return n;
    }

    /**
     * 根据开始页码和每页显示数据数查询路线
     * @param cid
     * @param start
     * @param pageSize
     * @param rname
     * @return
     */
    @Override
    public List<Route> findAllByPage(int cid, int start, int pageSize, String rname) {
        String sql = "select * from tab_route where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        List<Route> list = null;
        List params = new ArrayList();
        if ((cid != 0 && rname == null) || (cid != 0 && rname.length() == 0) || (cid != 0 && "null".equals(rname))){
            sb.append(" and cid = ? ");
            params.add(cid);
        }


        if (rname != null && rname.length() != 0 && !"null".equals(rname)){
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        params.add(start);
        params.add(pageSize);

        sb.append(" limit ?,? ");
        sql = sb.toString();

        try {
            list = template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 根据rid查找路线具体信息
     * @param rid
     * @return
     */
    @Override
    public Route findOneByRid(int rid) {
        String sql = "select * from tab_route where rid = ?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }
}
