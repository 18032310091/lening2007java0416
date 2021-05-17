package com.lening.entityvo;
/**
 * 创建时间: 2021-04-20 14:59
 * 机关单位: 乐柠教育
 */

import com.lening.entity.DeptBean;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 创建时间: 2021-04-20 14:59 
 * IT操作员: 陈港星
 */
@Data
public class UserVo implements Serializable {
    private Long[] deptids;
    private List<DeptBean> dlist;
}
