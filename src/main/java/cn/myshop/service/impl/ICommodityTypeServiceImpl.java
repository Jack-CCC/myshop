package cn.myshop.service.impl;

import cn.myshop.domain.CommodityType;
import cn.myshop.domain.mapper.CommodityTypeMapper;
import cn.myshop.service.ICommodityTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ICommodityTypeServiceImpl implements ICommodityTypeService {

    @Autowired
    private CommodityTypeMapper commodityTypeMapper;

    @Override
    public List<CommodityType> findCommodityTypes() {
        return commodityTypeMapper.findAllCommodityTypes();
    }
}
