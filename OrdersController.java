package com.sdut.controller;

import com.sdut.mapper.OrdersMapper;
import com.sdut.pojo.Menu;
import com.sdut.pojo.Orders;
import com.sdut.pojo.User;
import com.sdut.service.IMenuService;
import com.sdut.service.IOrdersService;
import com.sdut.service.IUserService;
import com.sdut.util.PageInfoForOrders;
import com.sdut.util.PageInfoForUser;
import com.sdut.util.TopDish;
import com.sdut.util.TopUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private IOrdersService ordersService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IMenuService menuService;
    @RequestMapping("/selectByPage")
    public String selectByPage(@RequestParam(defaultValue = "1") Integer pageNo,
                               @RequestParam(defaultValue = "5") Integer pageSize, Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Integer userID = user.getId();
        System.out.println("**********))))))))))))))))***********"+userID);
        PageInfoForOrders pageInfo = ordersService.selectAllByPage(userID,pageNo, pageSize);
        model.addAttribute("pageInfo", pageInfo);
        return "/orders_listForUser";
    }
    @RequestMapping("/sellectAllByPage_Admin")
    public String sellectAllByPage_Admin(@RequestParam(defaultValue = "1") Integer pageNo,
                               @RequestParam(defaultValue = "5") Integer pageSize, Model model) {
        PageInfoForOrders pageInfo = ordersService.selectAllByPage_Admin(pageNo, pageSize);
        model.addAttribute("pageInfo", pageInfo);
        return "/orders_listForAdmin";
    }
   @RequestMapping("/sellectAllByPagewherestatus1")
   public String selectByPageWherestatus1(@RequestParam(defaultValue = "1") Integer pageNo,
                                          @RequestParam(defaultValue = "5") Integer pageSize, Model model,HttpServletRequest request){
       HttpSession session = request.getSession();
       User user = (User) session.getAttribute("user");
       Integer userID = user.getId();
       System.out.println("*********************"+userID);
       PageInfoForOrders pageInfo = ordersService.selectAllByPageWhereStatus1(userID,pageNo, pageSize);
       System.out.println("pageInfo:" + pageInfo);
       model.addAttribute("pageInfo", pageInfo);
        return "/orders_status1";
   }

    @RequestMapping("/add")
    public String add(@RequestParam("userID") int userID, @RequestParam("totalprice") double totalPrice, @RequestParam("dishId") int dishId) {
        ordersService.addOrders(userID, totalPrice, dishId);
        return "redirect:/orders/sellectAllByPagewherestatus1";
    }
    @RequestMapping("/sellctByid")
    public String sellctByid(Model model,int orderid,int dishid){
        Orders orders = ordersService.selectByid(orderid);
        Menu menu=menuService.selectByid(dishid);
        model.addAttribute(menu);
        model.addAttribute(orders);
        return "/order_details";
    }
    @RequestMapping("/sellctByidForAdmin")
    public String sellectByidForAdmin(Model model,int orderid,int dishid,int userID){
        Orders orders=ordersService.selectByid(orderid);
        Menu menu = menuService.selectByid(dishid);
        User user = userService.selectByid(userID);
        model.addAttribute(menu);
        model.addAttribute(orders);
        model.addAttribute(user);
        return "/order_detailsForAdmin";
    }
    @RequestMapping("/updateAll")
    public String updateAll(Integer[] ids) {
        ordersService.updateAll(ids);
        return "redirect:/orders/sellectAllByPage_Admin";
    }
    @RequestMapping("/Top3")
    public String Top3(Model model){
        List<TopDish> TopDish= ordersService.Top3();
        model.addAttribute("topdish",TopDish);
        List<TopUser> topUser=ordersService.Top3_U();
        model.addAttribute("user",topUser);
        int cnt=ordersService.cnt();
        model.addAttribute("cnt",cnt);
        return "/result";
    }

}
