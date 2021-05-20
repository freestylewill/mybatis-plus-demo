package com.guigu.bean;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;


/**
 * Description       ：
 *
 * @author ：lvhan
 * @version ：1.0
 * @since ：2020/9/28 9:30
 */
@Data
public class User {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String name;
    private Integer age;
    private String email;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @Version
    private Integer version;

    /** 逻辑删除: 1表示删除 0 表示未删除*/
    @TableLogic
    private Integer isDeleted;
}
