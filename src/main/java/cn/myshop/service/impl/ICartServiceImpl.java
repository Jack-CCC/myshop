package cn.myshop.service.impl;

import cn.myshop.domain.Cart;
import cn.myshop.domain.mapper.CartMapper;
import cn.myshop.domain.mapper.CommodityMapper;
import cn.myshop.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ICartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private CommodityMapper commodityMapper;

    @Override
    public List<Cart> findCartByCustomerId(String customerId) {
        return cartMapper.findByCustomerId(customerId);
    }

    @Override
    public void addCommodity(String commodityId, String customerId) {
        String uuid = UUID.randomUUID().toString();
        double commodityPrice = commodityMapper.findById(commodityId).getPrice();
        if(cartMapper.haveThisCC(customerId,commodityId)==null){
            cartMapper.insert(new Cart(uuid,commodityId,1,customerId,commodityPrice));
        }else{
            cartMapper.addNum(commodityId,customerId);
        }
    }

    @Override
    public void delCartByCustomerId(String customerId) {
        cartMapper.delByCustomerId(customerId);
    }

    @Override
    public void delCartByCommodityId(String commodityId, String customerId) {
        Cart cart = cartMapper.haveThisCC(customerId,commodityId);
        if(cart.getCommodityNum()>1){
            cart.setCommodityNum(cart.getCommodityNum()-1);
            cartMapper.updateCart(cart);
        }else {
            cartMapper.delByCommodityId(commodityId,customerId);
        }
    }
}
