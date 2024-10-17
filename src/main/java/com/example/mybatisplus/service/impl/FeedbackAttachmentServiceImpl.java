package com.example.mybatisplus.service.impl;

import com.example.mybatisplus.mapper.FeedbackMapper;
import com.example.mybatisplus.model.domain.FeedbackAttachment;
import com.example.mybatisplus.mapper.FeedbackAttachmentMapper;
import com.example.mybatisplus.service.FeedbackAttachmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zyc&rgl
 * @since 2022-02-27
 */
@Service
public class FeedbackAttachmentServiceImpl extends ServiceImpl<FeedbackAttachmentMapper, FeedbackAttachment> implements FeedbackAttachmentService {


    @Autowired
    FeedbackAttachmentMapper mapper;

    @Override
    public List<FeedbackAttachment> getByApplyId(Long applicationId) {


        return mapper.getByApplyId(applicationId);

    }
}
