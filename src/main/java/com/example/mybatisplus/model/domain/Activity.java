package com.example.mybatisplus.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

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
@ApiModel(value="Activity对象", description="")
public class Activity extends Model<Activity> {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;

    private String title;


    private String information;

    private String banner;

    private String cover;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private String begin;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private String end;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private String applyEnd;

    private String type;

    private String manner;

    private Boolean collegeAudit;

    private Boolean universityAudit;

    @TableLogic
    private Boolean isDeleted;

    @TableField(exist = false)
    private List<ActivityRegion> regions;

    @TableField(exist = false)
    private List<ActivityAttachment> attachments;

    @TableField(exist = false)
    private List<ActivityRule> college;

    @TableField(exist = false)
    private List<ActivityRule> grade;

    @TableField(exist = false)
    private Integer collegePass;

    @TableField(exist = false)
    private Integer universityPass;

    @TableField(exist = false)
    private Boolean reject;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
