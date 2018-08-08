package com.peanut.dao.mapper;

import com.peanut.pojo.UserRel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FriendMapper {
    List<String> selectFriendIds(@Param("stuId") String stuId);
    int insert(UserRel record);
}
