package com.peanut.dao.mapper;

import com.peanut.pojo.PostLike;
import org.apache.ibatis.annotations.Param;

public interface PostLikeMapper {
    Integer insert(PostLike postLike);

    Integer verifyLike(@Param("postId") Integer postId, @Param("userId") Integer userId);
}
