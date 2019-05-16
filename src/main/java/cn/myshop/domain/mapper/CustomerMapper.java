package cn.myshop.domain.mapper;

import cn.myshop.domain.Customer;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

@Mapper
public interface CustomerMapper {

    @Select("select id,username,password,gender,age,birthday,email,phone,is_VIP as isVip from customer where username=#{username}")
    Customer findByName(String username);

    @Select("select id,username,password,gender,age,birthday,email,phone,is_VIP as isVip from customer where id=#{id}")
    Customer findById(String id);

    @Update("update customer set username=#{username},gender=#{gender},age=#{age},email=#{email},phone=#{phone} where id=#{id}")
    int changeInfo(Customer customer);

    @Insert("insert into customer values(#{id},#{username},#{password},null,0,#{birthday},null,null,'false')")
    void sign(String id, String username, String password, Date birthday);

    @Update("update customer set password=#{newPassword} where id=#{cid}")
    void changePassword(String cid, String newPassword);
}