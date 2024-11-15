package com.example.mybatisplus.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2022-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Feedback对象", description="")
public class Feedback extends Model<Feedback> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long applicationId;

    private String feedback;

    private Long userId;

    private Long activityId;

    private String college;
    private String activityName;

    @DateTimeFormat(pattern = "YYYY-MM-DD HH:MM:SS")
    private String publishDate;

    @TableLogic
    private Boolean isDeleted;

    @TableField(exist = false)
    private List<FeedbackAttachment> attachments;

    private String userName;

    private String type;

    @TableField(exist = false)
    private String region;
    @TableField(exist = false)
    private String highSchool;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
