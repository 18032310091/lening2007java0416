package com.lening.service;
/**
 * 创建时间: 2021-04-08 19:34
 * 机关单位: 乐柠教育
 */


import com.lening.entity.MeunBean;
import com.lening.entity.PostBean;
import com.lening.utlis.Page;

import java.util.List;

/**
 * 创建时间: 2021-04-08 19:34
 * IT操作员: 陈港星
 */
public interface PostService {
    Page<PostBean> getPostListConn(PostBean postBean, Integer pageNum, Integer pageSize);

    List<MeunBean> getMeunListById(Long id);

    void savePostMeun(Long postid, Long[] ids);
}
