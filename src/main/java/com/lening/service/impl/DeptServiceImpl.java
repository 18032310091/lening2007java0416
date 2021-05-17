package com.lening.service.impl;
/**
 * 创建时间: 2021-04-08 16:47
 * 机关单位: 乐柠教育
 */


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lening.entity.DeptBean;
import com.lening.entity.DeptBeanExample;
import com.lening.entity.PostBean;
import com.lening.mapper.DeptMapper;
import com.lening.mapper.PostMapper;
import com.lening.service.DeptService;
import com.lening.utlis.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 创建时间: 2021-04-08 16:47
 * IT操作员: 陈港星
 */
@Service
public class DeptServiceImpl implements DeptService {
    @Resource
    private DeptMapper deptMapper;
    @Resource
    private PostMapper postMapper;

    @Override
    public Page<DeptBean> getDeptListConn(DeptBean deptBean, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        DeptBeanExample example = new DeptBeanExample();
        DeptBeanExample.Criteria criteria = example.createCriteria();


        if(deptBean!=null){
            if(deptBean.getDeptname()!=null && deptBean.getDeptname().length()>=1){
                criteria.andDeptnameLike("%"+deptBean.getDeptname()+"%");
            }
        }

        List<DeptBean> list = deptMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(list);
        Long total = pageInfo.getTotal();
        Page<DeptBean> page = new Page(pageInfo.getPageNum()+"", total.intValue(), pageInfo.getPageSize()+"");
        page.setList(list);
        return page;
    }

    @Override
    public DeptBean getDeptByDeptid(Long deptid) {
        DeptBean deptBean = deptMapper.selectByPrimaryKey(deptid);
        /**
         * 查询这个部门原来的职位（需要在中间表）
         * 还要查询职位列表（直接使用职位的mapper去查询就OK啦）
         */
        List<PostBean> plist = postMapper.selectByExample(null);
        deptBean.setPlist(plist);

        Long[] postids = deptMapper.getDeptPostIds(deptid);
        deptBean.setPostids(postids);
        return deptBean;
    }

    @Override
    public void saveDeptPost(Long deptid, Long[] postids) {
        /**
         * 先删除后新增
         */
        deptMapper.depeteDeptPost(deptid);
        if(postids!=null&&postids.length>=1){
            for (Long postid : postids) {
                deptMapper.saveDeptPost(deptid,postid);
            }
        }
    }
}