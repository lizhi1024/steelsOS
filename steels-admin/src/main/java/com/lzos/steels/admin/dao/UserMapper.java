package com.lzos.steels.admin.dao;

import com.lzos.steels.admin.entity.dto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User getUserById(int id);

}
