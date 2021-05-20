package com.guigu.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guigu.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description       ：
 *
 * @author ：lvhan
 * @version ：1.0
 * @since ：2020/9/28 9:30
 */
public interface UserMapper extends BaseMapper<User> {

    User selectByPrimaryKey(Long id);

    Page<User> selectByPage(Page<User> page, @Param(Constants.WRAPPER) Wrapper<Object> wrapper);

    List<User> selectByUser(User user);

//    List<User> selectMy(@Param(Constants.WRAPPER) Wrapper<User> wrapper);

}
