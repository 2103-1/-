package com.sdut.controller;

import com.sdut.pojo.Menu;
import com.sdut.pojo.User;
import com.sdut.service.IMenuService;
import com.sdut.service.IOrdersService;
import com.sdut.util.PageInfoForMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IOrdersService ordersService;
    @RequestMapping("/sellectAllByPage")
    public String selectByPage(@RequestParam(defaultValue = "1") Integer pageNo,
                               @RequestParam(defaultValue = "5") Integer pageSize, Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Integer id = user.getId();
        List<Menu> tuijian = ordersService.tuijian(id);
        PageInfoForMenu pageInfo = menuService.selectAllByPage(pageNo, pageSize);
        System.out.println("pageInfo:" + pageInfo);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("tuijian",tuijian);
        return "/menu_list";
    }
    @RequestMapping("/sellectAllByPage_Admin")
    public String selectByPage_Admin(@RequestParam(defaultValue = "1") Integer pageNo,
                               @RequestParam(defaultValue = "5") Integer pageSize, Model model) {
        System.out.println("UserController.selectByPage");
        PageInfoForMenu pageInfo = menuService.selectAllByPage(pageNo, pageSize);
        System.out.println("pageInfo:" + pageInfo);
        model.addAttribute("pageInfo", pageInfo);
        return "/menu_listForAdmin";
    }
    @RequestMapping("/toAdd")
    public String toAdd(){
        return "menu_add";
    }
    @RequestMapping("/add")
    public String add(Menu menu){
        menuService.addMenu(menu);
        return "redirect:/menu/sellectAllByPage_Admin";
    }
    @RequestMapping("update")
    public String update(Menu menu){
        menuService.update(menu);
        return "redirect:/menu/sellectAllByPage_Admin";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(Integer id,Model model){
        Menu menu = menuService.selectByid(id);
        model.addAttribute("menu",menu);
        return "/menu_update";
    }
}
