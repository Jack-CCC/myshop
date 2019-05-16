package cn.myshop.service;

import cn.myshop.domain.Customer;

public interface ICustomerService {

    public Customer findCustomerByName(String username);

    Customer findCustomerById(String id);

    String changeInfo(String cid, String username, String gender, Integer age, String email, String phone);

    void sign(String username, String password);

    void changePassword(String cid, String newPassword);
}
