package com.lening.service.impl;
/**
 * 创建时间: 2021-04-08 19:35
 * 机关单位: 乐柠教育
 */


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lening.entity.MeunBean;
import com.lening.entity.PostBean;
import com.lening.entity.PostBeanExample;
import com.lening.mapper.MeunMapper;
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

    @Resource
    private MeunMapper meunMapper;

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

    @Override
    public List<MeunBean> getMeunListById(Long postid) {
        //全查菜单
        List<MeunBean> list = meunMapper.selectByExample(null);

        //当前职位拥有哪些菜单，去中间表查询，只要id就好了
        List<Long> meunids = postMapper.getPostMeunIds(postid);
        //要是原来有菜单的，需要回显，ztree回显，在后台直接回显就OK
        if(meunids!=null&&meunids.size()>=1){
            for (Long meunid : meunids) {
                for (MeunBean bean : list) {
                    if(meunid.equals(bean.getId())){
                        bean.setChecked(true);
                        break;
                    }
                }
            }
        }
        return list;
    }

    @Override
    public void savePostMeun(Long postid, Long[] ids) {
        //先去删除，中间表的数据
        postMapper.deletePostMeunByPostid(postid);

        if(ids!=null && ids.length>=1){
            for (Long meunid : ids) {
                postMapper.savePostMeun(postid,meunid);
            }
        }

    }
}
