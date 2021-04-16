package com.lening.service.impl;
/**
 * 创建时间: 2021-04-08 16:47
 * 机关单位: 乐柠教育
 */

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lening.entity.DeptBean;
import com.lening.entity.DeptBeanExample;
import com.lening.mapper.DeptMapper;
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
}
