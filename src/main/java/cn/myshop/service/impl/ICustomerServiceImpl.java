package cn.myshop.service.impl;

import cn.myshop.domain.Customer;
import cn.myshop.domain.mapper.CustomerMapper;
import cn.myshop.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

@Service
public class ICustomerServiceImpl implements ICustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public Customer findCustomerByName(String username) {
        return customerMapper.findByName(username);
    }

    @Override
    public Customer findCustomerById(String id) {
        return customerMapper.findById(id);
    }

    @Override
    public String changeInfo(String cid,String username, String gender, Integer age, String email, String phone) {
        Customer customer = new Customer(cid,username,"",gender,age,null,email,phone,"");
        Customer c = customerMapper.findByName(username);
        if(c!=null&&(!c.getId().equals(cid))){
            return "用户名已存在";
        }
        customerMapper.changeInfo(customer);
        return "更新成功";
    }

    @Override
    public void sign(String username, String password) {
        String id = UUID.randomUUID().toString();
        String pwd = DigestUtils.md5DigestAsHex(password.getBytes()).toUpperCase();
        Date birthday = new Date();
        customerMapper.sign(id,username,pwd,birthday);
    }

    @Override
    public void changePassword(String cid, String newPassword) {
        customerMapper.changePassword(cid,newPassword);
    }

}
