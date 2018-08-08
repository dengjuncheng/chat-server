package com.peanut.pojo;

import java.util.Date;

public class Post {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column post.id
     *
     * @mbg.generated Mon Nov 27 21:43:39 CST 2017
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column post.stu_id
     *
     * @mbg.generated Mon Nov 27 21:43:39 CST 2017
     */
    private Integer stuId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column post.title
     *
     * @mbg.generated Mon Nov 27 21:43:39 CST 2017
     */

    private String title;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column post.browser_num
     *
     * @mbg.generated Mon Nov 27 21:43:39 CST 2017
     */
    private Integer browserNum;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column post.like_num
     *
     * @mbg.generated Mon Nov 27 21:43:39 CST 2017
     */
    private Integer likeNum;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column post.comment_num
     *
     * @mbg.generated Mon Nov 27 21:43:39 CST 2017
     */
    private Integer commentNum;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column post.publish_date
     *
     * @mbg.generated Mon Nov 27 21:43:39 CST 2017
     */
    private Date publishDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column post.is_top
     *
     * @mbg.generated Mon Nov 27 21:43:39 CST 2017
     */
    private Byte isTop;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column post.state
     *
     * @mbg.generated Mon Nov 27 21:43:39 CST 2017
     */
    private Byte state;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column post.delete_reason
     *
     * @mbg.generated Mon Nov 27 21:43:39 CST 2017
     */
    private String deleteReason;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column post.content
     *
     * @mbg.generated Mon Nov 27 21:43:39 CST 2017
     */
    private String content;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column post.id
     *
     * @return the value of post.id
     *
     * @mbg.generated Mon Nov 27 21:43:39 CST 2017
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column post.id
     *
     * @param id the value for post.id
     *
     * @mbg.generated Mon Nov 27 21:43:39 CST 2017
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column post.stu_id
     *
     * @return the value of post.stu_id
     *
     * @mbg.generated Mon Nov 27 21:43:39 CST 2017
     */
    public Integer getStuId() {
        return stuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column post.stu_id
     *
     * @param stuId the value for post.stu_id
     *
     * @mbg.generated Mon Nov 27 21:43:39 CST 2017
     */
    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column post.title
     *
     * @return the value of post.title
     *
     * @mbg.generated Mon Nov 27 21:43:39 CST 2017
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column post.title
     *
     * @param title the value for post.title
     *
     * @mbg.generated Mon Nov 27 21:43:39 CST 2017
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column post.browser_num
     *
     * @return the value of post.browser_num
     *
     * @mbg.generated Mon Nov 27 21:43:39 CST 2017
     */
    public Integer getBrowserNum() {
        return browserNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column post.browser_num
     *
     * @param browserNum the value for post.browser_num
     *
     * @mbg.generated Mon Nov 27 21:43:39 CST 2017
     */
    public void setBrowserNum(Integer browserNum) {
        this.browserNum = browserNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column post.like_num
     *
     * @return the value of post.like_num
     *
     * @mbg.generated Mon Nov 27 21:43:39 CST 2017
     */
    public Integer getLikeNum() {
        return likeNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column post.like_num
     *
     * @param likeNum the value for post.like_num
     *
     * @mbg.generated Mon Nov 27 21:43:39 CST 2017
     */
    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column post.comment_num
     *
     * @return the value of post.comment_num
     *
     * @mbg.generated Mon Nov 27 21:43:39 CST 2017
     */
    public Integer getCommentNum() {
        return commentNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column post.comment_num
     *
     * @param commentNum the value for post.comment_num
     *
     * @mbg.generated Mon Nov 27 21:43:39 CST 2017
     */
    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column post.publish_date
     *
     * @return the value of post.publish_date
     *
     * @mbg.generated Mon Nov 27 21:43:39 CST 2017
     */
    public Date getPublishDate() {
        return publishDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column post.publish_date
     *
     * @param publishDate the value for post.publish_date
     *
     * @mbg.generated Mon Nov 27 21:43:39 CST 2017
     */
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column post.is_top
     *
     * @return the value of post.is_top
     *
     * @mbg.generated Mon Nov 27 21:43:39 CST 2017
     */
    public Byte getIsTop() {
        return isTop;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column post.is_top
     *
     * @param isTop the value for post.is_top
     *
     * @mbg.generated Mon Nov 27 21:43:39 CST 2017
     */
    public void setIsTop(Byte isTop) {
        this.isTop = isTop;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column post.state
     *
     * @return the value of post.state
     *
     * @mbg.generated Mon Nov 27 21:43:39 CST 2017
     */
    public Byte getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column post.state
     *
     * @param state the value for post.state
     *
     * @mbg.generated Mon Nov 27 21:43:39 CST 2017
     */
    public void setState(Byte state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column post.delete_reason
     *
     * @return the value of post.delete_reason
     *
     * @mbg.generated Mon Nov 27 21:43:39 CST 2017
     */
    public String getDeleteReason() {
        return deleteReason;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column post.delete_reason
     *
     * @param deleteReason the value for post.delete_reason
     *
     * @mbg.generated Mon Nov 27 21:43:39 CST 2017
     */
    public void setDeleteReason(String deleteReason) {
        this.deleteReason = deleteReason;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column post.content
     *
     * @return the value of post.content
     *
     * @mbg.generated Mon Nov 27 21:43:39 CST 2017
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column post.content
     *
     * @param content the value for post.content
     *
     * @mbg.generated Mon Nov 27 21:43:39 CST 2017
     */
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", stuId=" + stuId +
                ", title='" + title + '\'' +
                ", browserNum=" + browserNum +
                ", likeNum=" + likeNum +
                ", commentNum=" + commentNum +
                ", publishDate=" + publishDate +
                ", isTop=" + isTop +
                ", state=" + state +
                ", deleteReason='" + deleteReason + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}