package com.lening.utlis;
/**
 * 创建时间: 2021-04-13 16:56
 * 机关单位: 乐柠教育
 */

import lombok.Data;

import java.io.Serializable;

/**
 * 创建时间: 2021-04-13 16:56 
 * IT操作员: 陈港星
 */
@Data
public class ResultInfo implements Serializable {
    private boolean flag;
    private String msg;

    public ResultInfo() {
    }

    public ResultInfo(boolean flag, String msg) {
        this.flag = flag;
        this.msg = msg;
    }
}
