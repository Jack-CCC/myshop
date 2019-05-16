package cn.myshop.controller;

import cn.myshop.domain.Customer;
import cn.myshop.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;

@RequestMapping("/customer")
@Controller
public class CustomerController {

    @Autowired
    private ICustomerService iCustomerService;

    //跳转注册页面
    @RequestMapping("/register")
    public String toRegister(){
        return "html/users/customers/customer_register";
    }

    //处理注册逻辑
    @RequestMapping("/handleRegister")
    public String handleRegister(String username,String password,Model model){
        Customer c = iCustomerService.findCustomerByName(username);
        if(username.trim().equals("")){
            model.addAttribute("badmsg","注册失败，用户名至少需要两个字符");
            return "html/users/customers/customer_register";
        }else if(c==null){
            iCustomerService.sign(username,password);
        } else{
            model.addAttribute("badmsg","注册失败，用户名"+username+"已经存在");
            return "html/users/customers/customer_register";
        }
        model.addAttribute("successSign","注册成功，请登录");
        return toLogin(model);
    }

    //转到登录页面
    @RequestMapping("/login")
    public String toLogin(Model model){
        return "html/users/customers/customer_login";
    }

    //处理登录逻辑
    @RequestMapping("/handleLogin")
    public String handleLogin(HttpServletRequest request,
                              HttpServletResponse response,
                              HttpSession session,
                              Model model){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String isRem = request.getParameter("isRem");

        model.addAttribute("PasswordErr","");
        model.addAttribute("UserNameErr","");
        model.addAttribute("successSign","");

        //密码用MD5加密，在登录时要用处理过的密码和数据库配对
        String pwd = DigestUtils.md5DigestAsHex(password.getBytes()).toUpperCase();

        if("".equals(username)){
            model.addAttribute("UserNameErr","请输入用户名");
            return "html/users/customers/customer_login";
        }else if("".equals(password)){
            model.addAttribute("UserNameVal",username);
            model.addAttribute("PasswordErr","请输入密码");
            return "html/users/customers/customer_login";
        }
        Customer customer = iCustomerService.findCustomerByName(username);
        if(customer==null){
            model.addAttribute("UserNameErr","用户名"+username+"不存在，请重新输入");
            return "html/users/customers/customer_login";
        }else if(!customer.getPassword().equals(pwd)){
            model.addAttribute("UserNameVal",username);
            model.addAttribute("PasswordErr","密码错误，请重新输入");
            return "html/users/customers/customer_login";
        }else {
            session.setAttribute("cid",customer.getId());
            session.setAttribute("customer",customer);
            session.setAttribute("customerName",customer.getUsername());
            ////当选择了记住密码，后台会添加cookie
            if(isRem!=null){
                Cookie cookie = new Cookie("cName",customer.getUsername());
                cookie.setPath("/");
                cookie.setMaxAge(365 * 24 * 60 * 60);
                response.addCookie(cookie);
            }
            return "redirect:/toIndex";
        }
    }

    //处理登出逻辑
    @RequestMapping("/handleLogOut")
    public String handleLogOut(HttpSession session,HttpServletRequest request,HttpServletResponse response){
        session.removeAttribute("cid");
        session.removeAttribute("customer");
        session.removeAttribute("customerName");
        Cookie cookie = new Cookie("cName","");//这边得用"",不能用null
        cookie.setPath("/");//设置成跟写入cookies一样的
        cookie.setMaxAge(0); //让这个cookie立刻销毁
        response.addCookie(cookie);
        return "redirect:/customer/login";
    }

    //转到更改密码页面
    @RequestMapping("/changePassword")
    public String toChangePassword(Model model){
        return "html/users/customers/customer_changePwd";
    }

    //处理更改密码逻辑
    @RequestMapping("/handleChangePassword")
    public String handleChangePassword(String oldPassword,String newPassword,HttpSession session,Model model){
        model.addAttribute("goodmsg","");
        model.addAttribute("badmsg","");
        String cid = (String) session.getAttribute("cid");
        oldPassword = DigestUtils.md5DigestAsHex(oldPassword.getBytes()).toUpperCase();
        newPassword = DigestUtils.md5DigestAsHex(newPassword.getBytes()).toUpperCase();
        if(!(iCustomerService.findCustomerById(cid).getPassword().equals(oldPassword))){
            model.addAttribute("badmsg","修改失败,旧密码错误");
            return "html/users/customers/customer_changePwd";
        }else if(iCustomerService.findCustomerById(cid).getPassword().equals(newPassword)){
            model.addAttribute("badmsg","修改失败,新密码不能与旧密码相同");
            return "html/users/customers/customer_changePwd";
        }else {
            iCustomerService.changePassword(cid,newPassword);
            model.addAttribute("successSign","密码修改成功，请重新登录");
        }
        return toLogin(model);
    }

    //转到个人信息页面
    @RequestMapping("/info/{id}")
    public String toCustomerIndex(@PathVariable String id,Model model){
        Customer customer = iCustomerService.findCustomerById(id);
        model.addAttribute("customer",customer);
        return "html/users/customers/customer_info";
    }

    //处理个人信息修改逻辑
    @RequestMapping("/changeInfo")
    public String handleChangeCustomerInfo(
            HttpSession session,Model model,HttpServletRequest request,
            String username, String gender, Integer age,String email, String phone) throws ParseException {
        String cid = (String) session.getAttribute("cid");
        String oldName = iCustomerService.findCustomerById(cid).getUsername();
        String message = iCustomerService.changeInfo(cid,username,gender,age,email,phone);
        //这里应该是自定义异常并进行异常处理，考虑到还需要改动前端，暂时只用字符串信息返回
        if (message.equals("更新成功")){
            model.addAttribute("success","更新成功");
        }else{
            model.addAttribute("failure","用户名已存在");
        }
        Customer customer = iCustomerService.findCustomerById(cid);
        model.addAttribute("customer",customer);
        return "html/users/customers/customer_info";
    }



}
