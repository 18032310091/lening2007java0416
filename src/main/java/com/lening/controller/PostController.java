package com.lening.controller;
/**
 * 创建时间: 2021-04-08 18:38
 * 机关单位: 乐柠教育
 */

import com.lening.entity.MeunBean;
import com.lening.entity.PostBean;
import com.lening.service.PostService;
import com.lening.utlis.Page;
import com.lening.utlis.ResultInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 创建时间: 2021-04-08 18:38
 * IT操作员: 陈港星
 */
@RestController
@RequestMapping("post")
public class PostController {
    @Resource
    private PostService postService;

    @RequestMapping("getPostListConn")
    public Page<PostBean> getPostListConn(@RequestBody PostBean postBean, @RequestParam(defaultValue = "1")Integer pageNum,@RequestParam(defaultValue = "5")Integer pageSize){
        return postService.getPostListConn(postBean,pageNum,pageSize);
    }

    @RequestMapping("getMeunListById")
    public List<MeunBean> getMeunListById(Long id){
        return postService.getMeunListById(id);
    }

    @RequestMapping("savePostMeun")
    public ResultInfo savePostMeun(Long postid, @RequestBody Long[] ids){
        try {
            postService.savePostMeun(postid,ids);
            return new ResultInfo(true, "保存成功");
        }catch (Exception e){
            return new ResultInfo(false, "保存失败");
        }
    }

}
