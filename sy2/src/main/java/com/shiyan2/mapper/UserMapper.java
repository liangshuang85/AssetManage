package com.shiyan2.mapper;

import com.shiyan2.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 梁爽
 * @since 2023-04-17
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
