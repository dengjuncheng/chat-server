package com.peanut.pojo;

public class CrowdUserRel {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column crowd_user_rel.id
     *
     * @mbg.generated Mon Nov 27 00:14:40 CST 2017
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column crowd_user_rel.crowd_id
     *
     * @mbg.generated Mon Nov 27 00:14:40 CST 2017
     */
    private Integer crowdId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column crowd_user_rel.user_id
     *
     * @mbg.generated Mon Nov 27 00:14:40 CST 2017
     */
    private Integer userId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column crowd_user_rel.id
     *
     * @return the value of crowd_user_rel.id
     *
     * @mbg.generated Mon Nov 27 00:14:40 CST 2017
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column crowd_user_rel.id
     *
     * @param id the value for crowd_user_rel.id
     *
     * @mbg.generated Mon Nov 27 00:14:40 CST 2017
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column crowd_user_rel.crowd_id
     *
     * @return the value of crowd_user_rel.crowd_id
     *
     * @mbg.generated Mon Nov 27 00:14:40 CST 2017
     */
    public Integer getCrowdId() {
        return crowdId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column crowd_user_rel.crowd_id
     *
     * @param crowdId the value for crowd_user_rel.crowd_id
     *
     * @mbg.generated Mon Nov 27 00:14:40 CST 2017
     */
    public void setCrowdId(Integer crowdId) {
        this.crowdId = crowdId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column crowd_user_rel.user_id
     *
     * @return the value of crowd_user_rel.user_id
     *
     * @mbg.generated Mon Nov 27 00:14:40 CST 2017
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column crowd_user_rel.user_id
     *
     * @param userId the value for crowd_user_rel.user_id
     *
     * @mbg.generated Mon Nov 27 00:14:40 CST 2017
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}