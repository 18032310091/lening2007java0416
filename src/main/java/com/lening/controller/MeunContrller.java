package com.lening.controller;
/**
 * 创建时间: 2021-04-09 16:36
 * 机关单位: 乐柠教育
 */

import com.lening.entity.MeunBean;
import com.lening.service.MeunService;
import com.lening.utlis.ResultInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 创建时间: 2021-04-09 16:36
 * IT操作员: 陈港星
 */
@RestController
@RequestMapping("meun")
public class MeunContrller {
    @Resource
    private MeunService meunService;

    @RequestMapping("getMeunListByPid")
    public List<MeunBean> getMeunListByPid(@RequestParam(defaultValue = "1") Long pid){
        return meunService.getMeunListByPid(pid );
    }

    @RequestMapping("saveMeun")
    public ResultInfo saveMeun(@RequestBody MeunBean meunBean){
        try {
            meunService.saveMeun(meunBean);
            return new ResultInfo(true , "保存成功");
        }catch (Exception e){
            return new ResultInfo(false, "保存失败");
        }
    }

    @RequestMapping("deleteMeunById")
    public ResultInfo deleteMeunById(Long id){
        try {
            meunService.deleteMeunById(id);
            return new ResultInfo(true , "删除成功");
        }catch (Exception e){
            return new ResultInfo(false, "删除失败");
        }
    }

}
