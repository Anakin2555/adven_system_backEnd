package com.example.mybatisplus.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
@ApiModel(value="Application对象", description="")
public class Application extends Model<Application> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long activityId;

    private Long highSchoolId;

    private Integer collegePass;

    private Integer universityPass;

    private Boolean reject;

    private String userName;

    private String college;

    private String grade;

    private String type;

    private Float gpa;

    private String region;

    private String reason;

    @DateTimeFormat(pattern = "YYYY-MM-DD HH:MM:SS")
    private String applyDate;

    private String userType;

    @TableLogic
    private Boolean isDeleted;

    @TableField(exist = false)
    private Feedback feedback;

    @TableField(exist = false)
    private String schoolName;

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", userId=" + userId +
                ", activityId=" + activityId +
                ", highSchoolId=" + highSchoolId +
                ", collegePass=" + collegePass +
                ", universityPass=" + universityPass +
                ", reject=" + reject +
                ", college='" + college + '\'' +
                ", grade='" + grade + '\'' +
                ", type='" + type + '\'' +
                ", gpa=" + gpa +
                ", region='" + region + '\'' +
                ", reason='" + reason + '\'' +
                ", applyDate='" + applyDate + '\'' +
                ", isDeleted=" + isDeleted +
                ", feedback=" + feedback +
                '}';
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
