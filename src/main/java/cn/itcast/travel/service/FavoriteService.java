package cn.itcast.travel.service;

public interface FavoriteService {
    //判断线路是否被用户收藏过
    boolean isFavorite(int parseInt, int uid);
    //收藏路线
    void addFavorite(String rid, int uid);
}
