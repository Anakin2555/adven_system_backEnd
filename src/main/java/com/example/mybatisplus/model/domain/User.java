package com.example.mybatisplus.model.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zyc&rgl
 * @since 2022-02-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="User对象", description="")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;

    private String userAccount;

    private String grade;

    private String userPassword;

    private String type;

    private String college;

    private String userName;

    private Float gpa;

    private String email;

    private Integer messageNumber;

    @TableLogic
    private Boolean isDeleted;

    private String identifyNumber;

    @TableField(exist = false)
    private Token token;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
