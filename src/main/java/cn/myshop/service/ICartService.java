package cn.myshop.service;

import cn.myshop.domain.Cart;

import java.util.List;

public interface ICartService {
    List<Cart> findCartByCustomerId(String customerId);

    void addCommodity(String commodityId, String customerId);

    void delCartByCustomerId(String customerId);

    void delCartByCommodityId(String commodityId, String customerId);
}
