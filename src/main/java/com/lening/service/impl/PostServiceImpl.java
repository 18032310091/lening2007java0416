package com.lening.service.impl;
/**
 * 创建时间: 2021-04-08 19:35
 * 机关单位: 乐柠教育
 */

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lening.entity.PostBean;
import com.lening.entity.PostBeanExample;
import com.lening.mapper.PostMapper;
import com.lening.service.PostService;
import com.lening.utlis.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 创建时间: 2021-04-08 19:35
 * IT操作员: 陈港星
 */
@Service
public class PostServiceImpl implements PostService {
    @Resource
    private PostMapper postMapper;

    @Override
    public Page<PostBean> getPostListConn(PostBean postBean, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PostBeanExample example = new PostBeanExample();
        PostBeanExample.Criteria criteria = example.createCriteria();
        if(postBean!=null){
            if(postBean.getPostname()!=null && postBean.getPostname().length()>=1){
                criteria.andPostnameLike("%"+postBean.getPostname()+"%");
            }
        }
        List<PostBean> list = postMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(list);
        Long total = pageInfo.getTotal();
        Page<PostBean> page = new Page(pageInfo.getPageNum()+"", total.intValue(), pageInfo.getPageSize()+"");
        page.setList(list);
        return page;
    }
}
