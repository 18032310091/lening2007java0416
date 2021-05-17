package com.lening.controller;
/**
 * 创建时间: 2021-04-08 16:37
 * 机关单位: 乐柠教育
 */

import com.lening.entity.DeptBean;
import com.lening.service.DeptService;
import com.lening.utlis.Page;
import com.lening.utlis.ResultInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 创建时间: 2021-04-08 16:37
 * IT操作员: 陈港星
 */
@RestController
@RequestMapping("/dept")
public class DeptController {
    @Resource
    private DeptService deptService;

    @RequestMapping("/getDeptListConn")
    public Page<DeptBean> getDeptListConn(@RequestBody DeptBean deptBean, @RequestParam(defaultValue = "1")Integer pageNum, @RequestParam(defaultValue = "5")Integer pageSize){
        return deptService.getDeptListConn(deptBean,pageNum,pageSize);
    }

    /**
     * 保存部门分配职位
     */
    @RequestMapping("/saveDeptPost")
    public ResultInfo saveDeptPost(Long deptid,@RequestBody Long[] postids){
        try {
            deptService.saveDeptPost(deptid,postids);
            return new ResultInfo(true, "编辑成功");
        }catch (Exception e){
            return  new ResultInfo(false, "编辑失败");
        }
    }

    @RequestMapping("/getDeptByDeptid")
    public DeptBean getDeptByDeptid(Long deptid){
        return deptService.getDeptByDeptid(deptid);
    }

}
