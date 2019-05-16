package cn.myshop.domain.mapper;

import cn.myshop.domain.Cart;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CartMapper {

    @Delete("delete from cart where customer_id=#{customerId}")
    void delByCustomerId(String customerId);

    @Select("select id,commodity_id as commodityId,customer_id as customerId,commodity_num as commodityNum,commodity_price as commodityPrice from cart where customer_id=#{customerId}")
    List<Cart> findByCustomerId(String customerId);

    @Select("select id,commodity_id as commodityId,customer_id as customerId,commodity_num as commodityNum,commodity_price as commodityPrice from cart where customer_id=#{customerId} and commodity_id=#{commodityId}")
    Cart haveThisCC(String customerId, String commodityId);

    //String id, String commodityId, Integer commodityNum, String customerId, double commodityPrice
    @Insert("insert into cart values(#{id},#{commodityId},#{commodityNum},#{customerId},#{commodityPrice})")
    void insert(Cart cart);

    @Update("update cart set commodity_num=commodity_num+1 where customer_id=#{customerId} and commodity_id=#{commodityId}")
    void addNum(String commodityId, String customerId);

    @Delete("delete from cart where customer_id=#{customerId} and commodity_id=#{commodityId}")
    void delByCommodityId(String commodityId, String customerId);

    @Update("update cart set commodity_num=#{commodityNum} where id=#{id}")
    void updateCart(Cart cart);
}