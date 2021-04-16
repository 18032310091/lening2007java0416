package com.lening.service;
/**
 * 创建时间: 2021-04-09 16:46
 * 机关单位: 乐柠教育
 */

import com.lening.entity.MeunBean;

import java.util.List;

/**
 * 创建时间: 2021-04-09 16:46 
 * IT操作员: 陈港星
 */
public interface MeunService {
    List<MeunBean> getMeunListByPid(Long pid);

    void saveMeun(MeunBean meunBean);

    void deleteMeunById(Long id);
}
