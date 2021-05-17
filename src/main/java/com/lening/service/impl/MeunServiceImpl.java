package com.lening.service.impl;
/**
 * 创建时间: 2021-04-09 16:47
 * 机关单位: 乐柠教育
 */


import com.lening.entity.MeunBean;
import com.lening.entity.MeunBeanExample;
import com.lening.mapper.MeunMapper;
import com.lening.service.MeunService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间: 2021-04-09 16:47
 * IT操作员: 陈港星
 */
@Service
public class MeunServiceImpl implements MeunService {
    @Resource
    private MeunMapper meunMapper;

    @Override
    public List<MeunBean> getMeunListByPid(Long pid) {
        MeunBeanExample example = new MeunBeanExample();
        MeunBeanExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(pid);
        return meunMapper.selectByExample(example);
    }

    @Override
    public void saveMeun(MeunBean meunBean) {
        if(meunBean!=null){
            if (meunBean.getId()!=null){
                meunMapper.updateByPrimaryKeySelective(meunBean);
            }else {
                meunMapper.insertSelective(meunBean);
            }
        }
    }
    List<Long> ids = new ArrayList<>();
    @Override
    public void deleteMeunById(Long id) {
        getDeleteMeunListByPid(id);
        for (Long id1 : ids) {
            meunMapper.deleteByPrimaryKey(id1);
        }
    }

    private void getDeleteMeunListByPid(Long pid) {
        ids.add(pid);
        MeunBeanExample example = new MeunBeanExample();
        MeunBeanExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(pid);
        List<MeunBean> list = meunMapper.selectByExample(example);

        if (list!=null && list.size()>=1){
            for (MeunBean bean : list) {
                getDeleteMeunListByPid(bean.getId());
            }
        }
    }
}
