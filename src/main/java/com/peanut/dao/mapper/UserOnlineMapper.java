package com.peanut.dao.mapper;

import com.peanut.pojo.UserOnline;

public interface UserOnlineMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_online
     *
     * @mbg.generated Mon Nov 27 00:17:24 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_online
     *
     * @mbg.generated Mon Nov 27 00:17:24 CST 2017
     */
    int insert(UserOnline record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_online
     *
     * @mbg.generated Mon Nov 27 00:17:24 CST 2017
     */
    int insertSelective(UserOnline record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_online
     *
     * @mbg.generated Mon Nov 27 00:17:24 CST 2017
     */
    UserOnline selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_online
     *
     * @mbg.generated Mon Nov 27 00:17:24 CST 2017
     */
    int updateByPrimaryKeySelective(UserOnline record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_online
     *
     * @mbg.generated Mon Nov 27 00:17:24 CST 2017
     */
    int updateByPrimaryKey(UserOnline record);
}