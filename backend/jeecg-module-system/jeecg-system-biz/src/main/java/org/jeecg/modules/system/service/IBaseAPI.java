package org.jeecg.modules.system.service;

import org.jeecg.common.constant.CommonConstant;
import org.springframework.cache.annotation.Cacheable;

public interface IBaseAPI {
    @Cacheable(cacheNames= CommonConstant.SYS_USER_ID_MAPPING_CACHE, key="#username")
    String getUserIdByName(String username);
}
