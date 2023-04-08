package com.hncboy.chatgpt.base.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hncboy.chatgpt.base.enums.UserSecretTypeEnum;
import lombok.Data;

import java.util.Date;

/**
 * @author hncboy
 * @date 2023/3/25 16:14
 * 聊天室表实体类
 */
@Data
@TableName("user_secret")
public class UserSecretDO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String userName;
    private String secret;
    private UserSecretTypeEnum type;
    private Long initValue;
    private Long balance;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
