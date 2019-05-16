package cn.myshop.controller;

import cn.myshop.domain.Cart;
import cn.myshop.domain.Commodity;
import cn.myshop.domain.Customer;
import cn.myshop.service.ICartService;
import cn.myshop.service.ICommodityService;
import cn.myshop.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class BuyController {

    @Autowired
    private ICartService iCartService;

    @Autowired
    private ICommodityService iCommodityService;

    @Autowired
    private ICustomerService iCustomerService;

    /**
     * 查找指定用户的订单
     * @param customerId
     */
    @RequestMapping("/cid")
    public String toCustomerCart(String customerId, Model model){
        List<Cart> carts =  iCartService.findCartByCustomerId(customerId);
        double totalPay = 0.00;
        for(Cart cart : carts){
            Commodity commodity = iCommodityService.findCommodityById(cart.getCommodityId());
            cart.setId(commodity.getName());
            totalPay += cart.getPay();
        }
        model.addAttribute("carts",carts);
        model.addAttribute("totalPay",totalPay);
        double zhekou = 1;
        Customer customer = iCustomerService.findCustomerById(customerId);
        if(customer.getIsVip().equals("true")){
            zhekou = 0.8;
        }
        model.addAttribute("zhekou",totalPay*zhekou);
        return "html/index/cartIndex";
    }

    /**
     * 添加商品到购物车
     */
    @RequestMapping("/add")
    public String addCommodity(String commodityId, HttpSession session,Model model){
        String customerId = (String) session.getAttribute("cid");
        iCartService.addCommodity(commodityId,customerId);
        return toCustomerCart(customerId,model);
    }

    /**
     * 清空购物车
     */
    @RequestMapping("/del")
    public String delCartByCustomerId(String customerId,Model model){
        iCartService.delCartByCustomerId(customerId);
        return toCustomerCart(customerId,model);
    }


    /**
     * 删除购物车中的某一商品系列
     */
    @RequestMapping("/delCommodity")
    public String delCartByCommodityId(String commodityId,Model model,HttpSession session){
        String customerId = (String) session.getAttribute("cid");
        iCartService.delCartByCommodityId(commodityId,customerId);
        return toCustomerCart(customerId,model);
    }

}
