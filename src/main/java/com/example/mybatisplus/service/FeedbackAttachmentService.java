package com.example.mybatisplus.service;

import com.example.mybatisplus.model.domain.FeedbackAttachment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zyc&rgl
 * @since 2022-02-27
 */
public interface FeedbackAttachmentService extends IService<FeedbackAttachment> {

    List<FeedbackAttachment> getByApplyId(Long applicationId);
}
