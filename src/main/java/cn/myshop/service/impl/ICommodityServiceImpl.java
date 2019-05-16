package cn.myshop.service.impl;

import cn.myshop.domain.Commodity;
import cn.myshop.domain.CommodityType;
import cn.myshop.domain.mapper.CommodityMapper;
import cn.myshop.domain.mapper.CommodityTypeMapper;
import cn.myshop.service.ICommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ICommodityServiceImpl implements ICommodityService {

    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private CommodityTypeMapper commodityTypeMapper;

    @Override
    public List<Commodity> findCommoditiesByTypeName(String queryStr) {
        CommodityType type = commodityTypeMapper.findByName(queryStr);
        return commodityMapper.findByType(type.getId());
    }

    @Override
    public List<Commodity> findCommoditiesByQueryStr(String queryStr) {
        return commodityMapper.findByQueryStr(queryStr);
    }

    @Override
    public List<Commodity> findAllCommodities() {
        return commodityMapper.findAll();
    }

    @Override
    public Commodity findCommodityById(String commodityId) {
        return commodityMapper.findById(commodityId);
    }
}
