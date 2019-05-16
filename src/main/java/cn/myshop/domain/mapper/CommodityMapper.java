package cn.myshop.domain.mapper;

import cn.myshop.domain.Commodity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommodityMapper {

    @Select("select id,name,price,image_url as imageUrl,description,stock,sold,t_id as tId from commodity where t_id=#{typeId}")
    List<Commodity> findByType(String typeId);

    @Insert("insert into commodity values(#{id},#{name},#{price},#{imageUrl},#{description},#{stock},#{sold},#{tId})")
    boolean save(Commodity commodity);

    @Select("select id,name,price,image_url as imageUrl,description,stock,sold,t_id as tId from commodity where name=#{name}")
    Commodity findByName(String name);

    @Delete("delete from commodity where t_id=#{typeId}")
    void deleteByTypeId(String typeId);

    @Delete("delete from commodity where id=#{id}")
    boolean delete(String id);

    @Select("select id,name,price,image_url as imageUrl,description,stock,sold,t_id as tId from commodity where id=#{id}")
    Commodity findById(String id);

    @Select("select id,name,price,image_url as imageUrl,description,stock,sold,t_id as tId from commodity where name like concat(concat('%',#{queryStr}),'%')")
    List<Commodity> findByQueryStr(String queryStr);

    @Select("select id,name,price,image_url as imageUrl,description,stock,sold,t_id as tId from commodity")
    List<Commodity> findAll();

}