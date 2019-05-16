package cn.myshop.service;

import cn.myshop.domain.Commodity;

import java.util.List;

public interface ICommodityService {

    List<Commodity> findCommoditiesByTypeName(String queryStr);

    List<Commodity> findCommoditiesByQueryStr(String queryStr);

    List<Commodity> findAllCommodities();

    Commodity findCommodityById(String commodityId);
}
