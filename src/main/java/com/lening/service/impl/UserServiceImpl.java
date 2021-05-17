package com.lening.service.impl;
/**
 * 创建时间: 2021-04-06 20:07
 * 机关单位: 乐柠教育
 */


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lening.entity.*;
import com.lening.mapper.DeptMapper;
import com.lening.mapper.MeunMapper;
import com.lening.mapper.UserMapper;
import com.lening.redis.RedisUtil;
import com.lening.service.UserService;
import com.lening.utlis.MD5key;
import com.lening.utlis.Page;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

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

    @Autowired
    private DeptMapper deptMapper;

    @Resource
    private RedisUtil redisUtil;


    @Override
    public List<UserBean> findAll() {
        return userMapper.selectByExample(null);
    }

    @Override
    public Page<UserBean> getUserListConn(UserBean ub, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        UserBeanExample example = new UserBeanExample();
        UserBeanExample.Criteria criteria = example.createCriteria();

        if (ub != null) {
            //用户模糊
            if (ub.getUname() != null && ub.getUname().length() >= 1) {
                criteria.andUnameLike("%" + ub.getUname() + "%");
            }
            //年龄模糊
            if (ub.getAge() != null) {
                criteria.andAgeGreaterThanOrEqualTo(ub.getAge());
            }
            //年龄模糊  条件查询时候上限
            //  eage
            if (ub.getEage() != null) {
                criteria.andAgeLessThanOrEqualTo(ub.getEage());
            }
        }
        List<UserBean> list = userMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(list);

        Long total = pageInfo.getTotal();
        Page page = new Page(pageInfo.getPageNum() + "", total.intValue(), pageInfo.getPageSize() + "");
        page.setList(list);
        return page;
    }

    //    @Override
//    public List<MeunBean> getMeunList(UserBean ub) {
//        if(ub!=null){
//            //菜单先展示不是按钮的菜单
//            MeunBeanExample example  = new MeunBeanExample();
//            MeunBeanExample.Criteria criteria = example.createCriteria();
//            criteria.andIsbuttonEqualTo(0);
//            List<MeunBean> list = meunMapper.selectByExample(example);
//            return list;
//        }
//        return null;
//    }

    /**
     * 不同人登录 不同效果
     * @param ub
     * @return
     */
    @Override
    public List<MeunBean> getMeunList(UserBean ub) {
        if (ub != null) {
            List<MeunBean> list = null;
            //从缓存中获取用户列表
            Object userListCache = redisUtil.getObject(ub.getId());

            //判断缓存中是否存在
            if (userListCache != null) {//不空，则强转返回
                System.out.println("redis中存在，直接返回");
                list = (List<MeunBean>) userListCache;

            }else{
                System.out.println("redis缓存中不存在，从数据库中取出，并且放入缓存");
                //查询数据库，取出
                list = userMapper.getUserMeunListById(ub.getId());
                //放入redis缓存
                redisUtil.putObject(ub.getId(), list);
            }
            return list;
        }
        return null;
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
    @Override
    public UserBean getUserVoById(Long id) {
        UserBean userBean = userMapper.selectByPrimaryKey(id);
        Long[] deptids = userMapper.getUserDeptidsById(id);
        userBean.setDeptids(deptids);
        List<DeptBean> deptBeanList = deptMapper.selectByExample(null);
        userBean.setDlist(deptBeanList);
        return userBean;
    }

    @Override
    public void saveUserDept(Long id, Long[] deptids) {
        //修改科室,要把该用户原来的科室删除,再次新增
        //还需要把原来的职位也删除掉(科室都变了,职位没了)
        userMapper.deleteUserPostById(id);
        userMapper.deleteUserDeptById(id);
        if (deptids != null && deptids.length >= 1) {
            for (Long deptid : deptids) {
                userMapper.insertUserDept(id, deptid);
            }
        }
    }

    @Override
    public UserBean getUserInfo(Long id) {
        UserBean userBean = userMapper.selectByPrimaryKey(id);
        //开始去查询这个用户有没有部门
        List<DeptBean> dlist = userMapper.getUserDeptById(id);
        //开始循环部门，查询部门里面的职位
        if (dlist != null && dlist.size() >= 1) {
            for (DeptBean deptBean : dlist) {
                List<PostBean> plist = deptMapper.getDeptPostList(deptBean.getId());
                Long[] postids = deptMapper.getUserPostByIdAndDeptid(id, deptBean.getId());
                deptBean.setPostids(postids);
                deptBean.setPlist(plist);
            }
        }
        userBean.setDlist(dlist);
        return userBean;
    }

    @Override
    public void saveUserPost(UserBean userBean) {
        //先去删除用户的职位
        if (userBean != null) {
            userMapper.deleteUserPostById(userBean.getId());
            if (userBean.getDlist() != null && userBean.getDlist().size() >= 1) {
                for (DeptBean deptBean : userBean.getDlist()) {
                    if (deptBean.getPostids() != null && deptBean.getPostids().length >= 1) {
                        for (Long postid : deptBean.getPostids()) {
                            userMapper.saveUserPost(userBean.getId(), postid);
                        }
                    }
                }
            }
        }
    }

    @Override
    public UserBean getLogin(UserBean ub) {
        //先用户用户名或者手机号，都是用用户名接收的，有可能用户输入的手机号，都是strint类型，无所谓
        if (ub != null) {
            List<UserBean> list = userMapper.getLogin(ub);
            if (list != null && list.size() == 1) {
                //到了这里表示用用户名或者手机号码查询到了
                //开始比对密码，比对密码之前需要加盐加密
                //加密算法有很多，常用的md5，bscript等
                UserBean userBean = list.get(0);
                //页面用户输入的密码，进行加密加盐后和数据库里面的密码进行比较，相等正确，不相等就错误
                String pwd = ub.getPwd();
                //也就是这里的加盐加密规则和注册的要一致，就好了，都是一个人负责的
                pwd = userBean.getPwdsalt() + pwd + userBean.getPwdsalt();
                MD5key md5key = new MD5key();
                String newpwd = md5key.getkeyBeanofStr(pwd);
                //相等就返回，其他都是空
                if (newpwd.equals(userBean.getPwd())) {
                    return userBean;
                }
            }
        }
        return null;
    }

    @Override
    public void deleteUserById(Long id) {
        userMapper.deleteUserById(id);
    }

    @Override
    public Set<String> getUserMeunUrlById(UserBean ub) {
        if (ub != null){
//            List<MeunBean> list = userMapper.getUserMeunUrlsById(ub.getId());
            Set<String> urls = userMapper.getUserMeunUrlsById(ub.getId());
            return urls;
        }
        return null;
    }

    @Test
    public void test() {
        String pwd = "123456";
        pwd = "123456" + pwd + "123456";
        MD5key md5key = new MD5key();
        String newpwd = md5key.getkeyBeanofStr(pwd);
        System.out.println(newpwd);
    }
}
