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
@RequestMapping("/commodity")
public class CommodityController {

    @Autowired
    private ICommodityService iCommodityService;

    @Autowired
    private ICommodityTypeService typeService;

    /**
     * 模糊查询商品
     * @param queryStr
     */
    @RequestMapping("/Query")
    public String QuerySelectCommodities(String queryStr, Model model){
        List<CommodityType> types = typeService.findCommodityTypes();
        model.addAttribute("types",types);
        for(CommodityType type : types){
            if(queryStr.equals(type.getTypeName())){
                List<Commodity> commodities = iCommodityService.findCommoditiesByTypeName(queryStr);
                model.addAttribute("commodities",commodities);
                return "html/index/index";
            }
        }
        List<Commodity> commodities = iCommodityService.findCommoditiesByQueryStr(queryStr);
        model.addAttribute("commodities",commodities);
        return "html/index/index";
    }

}
