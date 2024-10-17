package com.example.mybatisplus.model.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
@ApiModel(value="HighSchool对象", description="")
public class HighSchool extends Model<HighSchool> {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;

    private String schoolName;

    private String badge;

    private Long regionId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
