package com.lening.controller;
/**
 * 创建时间: 2021-04-06 20:05
 * 机关单位: 乐柠教育
 */

import com.lening.entity.MeunBean;
import com.lening.entity.UserBean;
import com.lening.service.UserService;
import com.lening.utlis.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 创建时间: 2021-04-06 20:05
 * IT操作员: 陈港星
 */
@RestController
@RequestMapping("user")
public class UserController {


    @Autowired
    private UserService userService;

    @RequestMapping("findAll")
    public List<UserBean> findAll(){
        return userService.findAll();
    }

    @RequestMapping("getUserListConn")
    public Page<UserBean> getUserListConn(@RequestBody UserBean ub, @RequestParam(defaultValue = "1")Integer pageNum, @RequestParam(defaultValue = "5")Integer pageSize ){
        return userService.getUserListConn(ub,pageNum,pageSize);
    }


    @RequestMapping("getLogin")
    public String getLogin(UserBean ub){
        return "ok";
    }

    @RequestMapping("getMeunList")
    public List<MeunBean> getMeunList(){
        return userService.getMeunList();
    }
}
