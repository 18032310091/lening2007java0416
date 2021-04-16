package com.lening.service.impl;
/**
 * 创建时间: 2021-04-06 20:07
 * 机关单位: 乐柠教育
 */

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lening.entity.MeunBean;
import com.lening.entity.UserBean;
import com.lening.entity.UserBeanExample;
import com.lening.mapper.MeunMapper;
import com.lening.mapper.UserMapper;
import com.lening.service.UserService;
import com.lening.utlis.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 创建时间: 2021-04-06 20:07
 * IT操作员: 陈港星
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MeunMapper meunMapper;

    @Override
    public List<UserBean> findAll() {
        return userMapper.selectByExample(null);
    }

    @Override
    public Page<UserBean> getUserListConn(UserBean ub, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        UserBeanExample example = new UserBeanExample();
        UserBeanExample.Criteria criteria = example.createCriteria();

        if (ub!=null){
            //用户模糊
            if (ub.getUname()!=null && ub.getUname( ).length()>=1){
                criteria.andUnameLike("%"+ub.getUname()+"%");
            }
            //年龄模糊
            if(ub.getAge()!=null){
                criteria.andAgeGreaterThanOrEqualTo(ub.getAge());
            }
            //年龄模糊  条件查询时候上限
            //  eage
            if(ub.getEage()!=null){
                criteria.andAgeLessThanOrEqualTo(ub.getEage());
            }
        }
        List<UserBean> list = userMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(list);

        Long total = pageInfo.getTotal();
        Page page = new Page(pageInfo.getPageNum()+"",total.intValue(),pageInfo.getPageSize()+"");
        page.setList(list);
        return page;
    }

    @Override
    public List<MeunBean> getMeunList() {
        List<MeunBean> list = meunMapper.selectByExample(null);
        return list;
    }
//    @Override
//    public List<MeunBean> getMeunList() {
//        Long[] ids = {1L,2L,3L};
//        List<MeunBean> list = meunMapper.selectByExample(null);
//        //双重forech循环
//        for(Long id : ids){
//            for (MeunBean bean : list) {
//                if (id.equals(bean.getId())){
//                    bean.setChecked(true);
//                    break;
//                }
//            }
//        }
//        return list;
//    }
}
