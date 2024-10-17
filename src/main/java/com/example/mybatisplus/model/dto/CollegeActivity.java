package com.example.mybatisplus.model.dto;


import com.example.mybatisplus.model.domain.Activity;
import lombok.Data;

import java.util.List;

@Data
public class CollegeActivity {

    private Activity activity;

    private Long ActivityId;
    //该活动本学院的申请条数且正由该学院审核
    private Integer countApply;


    //该活动已经通过的额人数
    private  Integer countAuditedTeacher = 0;
    private  Integer countAuditedStudent = 0;
}
