package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

public interface FavoriteDao {
    //查询路线的收藏次数
    int countByRid(int rid);
    //判断线路是否被用户收藏过
    Favorite findFavoriteByRidAnduid(int rid, int uid);
    //收藏路线
    void addFavorite(int rid, int uid);
}
