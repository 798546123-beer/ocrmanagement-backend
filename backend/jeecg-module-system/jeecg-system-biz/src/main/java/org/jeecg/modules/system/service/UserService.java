package org.jeecg.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.system.entity.User;
import org.jeecg.modules.system.pojo.UserInfo;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zoey
 * \\_/__/
 * @Date: 2024/11/11/21:15
 * @Description:
 */
public interface UserService extends IService<User> {
	boolean checkUserIsEffective(String username);
	UserInfo getUserInfo(User user);

	org.jeecg.modules.system.vo.User getUserVO(UserInfo userInfo);

	boolean save(User user);


}
