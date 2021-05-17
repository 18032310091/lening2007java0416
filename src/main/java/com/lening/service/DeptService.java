package com.lening.service;
/**
 * 创建时间: 2021-04-08 16:43
 * 机关单位: 乐柠教育
 */


import com.lening.entity.DeptBean;
import com.lening.utlis.Page;

/**
 * 创建时间: 2021-04-08 16:43
 * IT操作员: 陈港星
 */
public interface DeptService {
    Page<DeptBean> getDeptListConn(DeptBean deptBean, Integer pageNum, Integer pageSize);

    DeptBean getDeptByDeptid(Long deptid);

    void saveDeptPost(Long deptid, Long[] postids);
}

