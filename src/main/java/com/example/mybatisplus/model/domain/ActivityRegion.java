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
@ApiModel(value="ActivityRegion对象", description="")
public class ActivityRegion extends Model<ActivityRegion> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long activityId;//

    private Long regionId;//

    private String regionName;//

    @TableLogic
    private Boolean isDeleted;//

    private Integer maxTeacher;//

    private Integer maxStudent;//

    private Integer curTeacher;//

    private Integer curStudent;//

    @TableField(exist = false)
    private List<HighSchool> highSchoolList;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
