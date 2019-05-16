package cn.myshop.filters;

import cn.myshop.domain.Customer;
import cn.myshop.service.ICustomerService;
import cn.myshop.service.impl.ICustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "UserAccessFilter",urlPatterns = {"/*"})
public class UserAccessFilter implements Filter {

    @Autowired
    private ICustomerService iCustomerService = new ICustomerServiceImpl();

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest servletRequest = (HttpServletRequest)req;
        HttpServletResponse servletResponse = (HttpServletResponse)resp;
        HttpSession session = servletRequest.getSession();
        Cookie[] cookies = servletRequest.getCookies();
        String path = servletRequest.getRequestURI();


        String cid = (String) session.getAttribute("cid");
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("cName")) {
                    String cname = cookies[i].getValue();
                    Customer customer = iCustomerService.findCustomerByName(cname);
                    if (customer!=null) {
                        session.setAttribute("cid",customer.getId());
                        session.setAttribute("customer",customer);
                        session.setAttribute("customerName",customer.getUsername());
                        chain.doFilter(req, resp);
                        return;
                    }
                }
            }
        }
        if(path.equals("/customer/login")||path.equals("/toIndex")||path.equals("/customer/register")
                ||path.equals("/customer/handleRegister")||path.equals("/customer/handleLogin")
                ||path.contains("/image")||path.contains("Query")){
            chain.doFilter(req, resp);
            return;
        }
        if (cid == null) {
            servletResponse.sendRedirect("/customer/login");
            return;
        }
        chain.doFilter(req, resp);
    }













    public void init(FilterConfig config) throws ServletException { }

    public void destroy() { }

}
