package org.jeecg.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.system.entity.Project;
import org.jeecg.modules.system.mapper.ProjectMapper;
import org.jeecg.modules.system.service.ProjectService;
import org.springframework.stereotype.Service;

/**
 * 项目服务实现类
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

}
