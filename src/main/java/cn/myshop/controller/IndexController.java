package cn.myshop.controller;

import cn.myshop.domain.Commodity;
import cn.myshop.domain.CommodityType;
import cn.myshop.service.ICommodityService;
import cn.myshop.service.ICommodityTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private ICommodityService iCommodityService;

    @Autowired
    private ICommodityTypeService iCommodityTypeService;

    @RequestMapping("/toIndex")
    public String toIndex(Model model){
        List<Commodity> commodities = iCommodityService.findAllCommodities();
        model.addAttribute("commodities",commodities);
        List<CommodityType> commodityTypeList = iCommodityTypeService.findCommodityTypes();
        model.addAttribute("types",commodityTypeList);

        return "html/index/index";
    }
}
