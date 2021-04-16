package com.lening.service;
/**
 * 创建时间: 2021-04-06 20:06
 * 机关单位: 乐柠教育
 */

import com.lening.entity.MeunBean;
import com.lening.entity.UserBean;
import com.lening.utlis.Page;

import java.util.List;

/**
 * 创建时间: 2021-04-06 20:06
 * IT操作员: 陈港星
 */
public interface UserService {
    List<UserBean> findAll();

    Page<UserBean> getUserListConn(UserBean ub, Integer pageNum, Integer pageSize);

    List<MeunBean> getMeunList();
}
