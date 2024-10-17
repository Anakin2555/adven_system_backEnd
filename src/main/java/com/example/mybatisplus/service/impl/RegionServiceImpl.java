package com.example.mybatisplus.service.impl;

import com.example.mybatisplus.model.domain.Region;
import com.example.mybatisplus.mapper.RegionMapper;
import com.example.mybatisplus.service.RegionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zyc&rgl
 * @since 2022-02-25
 */
@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements RegionService {

}
