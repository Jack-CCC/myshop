package cn.myshop.domain.mapper;

import cn.myshop.domain.CommodityType;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommodityTypeMapper {

    @Select("select id,type_name as typeName,num from commodity_type")
    List<CommodityType> findAllCommodityTypes();

    @Select("select id,type_name as typeName,num from commodity_type where type_name=#{typeName}")
    CommodityType findByName(String typeName);

    @Insert("insert into commodity_type values(#{id},#{typeName},#{num})")
    boolean saveType(CommodityType commodityType);

    @Delete("delete from commodity_type where id=#{id}")
    void delete(String id);

    @Select("select id,type_name as typeName,num from commodity_type where id=#{id}")
    CommodityType findById(String id);

    @Update("update commodity_type set type_name=#{typeName} where id=#{id}")
    boolean changeTypeName(String id, String typeName);

    @Update("update commodity_type set num=#{num} where id=#{id}")
    void updateNum(CommodityType type);
}