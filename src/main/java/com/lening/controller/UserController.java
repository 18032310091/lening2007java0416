package com.lening.controller;
/**
 * 创建时间: 2021-04-06 20:05
 * 机关单位: 乐柠教育
 */

import com.lening.entity.MeunBean;
import com.lening.entity.UserBean;
import com.lening.service.UserService;
import com.lening.utlis.Page;
import com.lening.utlis.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**z
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
    public ResultInfo getLogin(@RequestBody UserBean ub, HttpServletRequest request){
        UserBean userBean = userService.getLogin(ub);
        if(userBean==null){
            return new ResultInfo(false, "登录失败,用户名或者密码错误");
        }else{
            request.getSession().setAttribute("ub", userBean);
            return new ResultInfo(true, "登录成功");
        }
    }

    @RequestMapping("getMeunList")
    public List<MeunBean> getMeunList(HttpServletRequest request){
        //在这里需要知道，是谁登陆的    还要查询出不是按钮的菜单
        UserBean ub = (UserBean) request.getSession().getAttribute("ub");
        //查询这个用户拥有的url
        Set<String> urls = userService.getUserMeunUrlById(ub);
        request.getSession().setAttribute("urls", urls);

        return userService.getMeunList(ub);
    }

    /**
     * 去给用户选择部门
     * @return
     */
    @RequestMapping("getUserVoById")
    public UserBean getUserVoById(Long id){
        return userService.getUserVoById(id);
    }

    @RequestMapping("saveUserDept")
    public ResultInfo saveUserDept(Long id,@RequestBody Long[] deptids){
        try {
            userService.saveUserDept(id,deptids);
            return new ResultInfo(true, "编制成功");
        }catch (Exception e){
            return new ResultInfo(false, "编制失败");
        }
    }

    /**
     * 去给用户分配职位
     * @param id
     * @return
     */
    @RequestMapping("getUserInfo")
    public UserBean getUserInfo(Long id){
        return userService.getUserInfo(id);
    }

    @RequestMapping("/saveUserPost")
    public ResultInfo saveUserPost(@RequestBody UserBean userBean){
        try {
            userService.saveUserPost(userBean);
            return new ResultInfo(true, "编辑成功");
        }catch (Exception e){
            return new ResultInfo(false, "编辑失败");
        }
    }

    @RequestMapping("deleteUserById")
    public ResultInfo deleteUserById(Long id){
        try {
            userService.deleteUserById(id);
            return new ResultInfo(true , "删除成功");
        }catch (Exception e){
            return new ResultInfo(false, "删除失败");
        }
    }
}
