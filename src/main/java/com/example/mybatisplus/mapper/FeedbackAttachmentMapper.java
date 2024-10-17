package com.example.mybatisplus.mapper;

import com.example.mybatisplus.model.domain.FeedbackAttachment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zyc&rgl
 * @since 2022-02-27
 */
public interface FeedbackAttachmentMapper extends BaseMapper<FeedbackAttachment> {

    List<FeedbackAttachment> getByApplyId(Long applicationId);
}
