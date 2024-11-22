package org.jeecg.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.modules.base.service.BaseCommonService;
import org.jeecg.modules.system.entity.User;
import org.jeecg.modules.system.mapper.UserMapper;
import org.jeecg.modules.system.pojo.UserInfo;
import org.jeecg.modules.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zoey
 * \\_/__/
 * @Date: 2024/11/11/21:17
 * @Description:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{
    public UserService userService;
    @Autowired
    public BaseCommonService baseCommonService;
    @Override
	public Result<org.jeecg.modules.system.vo.User> checkUserIsEffective(User user) {
		Result<org.jeecg.modules.system.vo.User> result = new Result<>();
		//情况1：根据用户信息查询，该用户不存在
		if (user == null) {
			result.error500("该用户不存在，请注册");
			baseCommonService.addLog("用户登录失败，用户不存在！", CommonConstant.LOG_TYPE_1, null);
			return result;
		}
		result.setResult(new org.jeecg.modules.system.vo.User(new UserInfo(user)));
        return result.success("查询成功");
		//情况2：根据用户信息查询，该用户已注销
		//update-begin---author:王帅   Date:20200601  for：if条件永远为falsebug------------
//		if (CommonConstant.DEL_FLAG_1.equals(user.getDelFlag())) {
//		//update-end---author:王帅   Date:20200601  for：if条件永远为falsebug------------
//			baseCommonService.addLog("用户登录失败，用户名:" + sysUser.getUsername() + "已注销！", CommonConstant.LOG_TYPE_1, null);
//			result.error500("该用户已注销");
//			return result;
//		}
//		//情况3：根据用户信息查询，该用户已冻结
//		if (CommonConstant.USER_FREEZE.equals(user.getStatus())) {
//			baseCommonService.addLog("用户登录失败，用户名:" + user.getUsername() + "已冻结！", CommonConstant.LOG_TYPE_1, null);
//			result.error500("该用户已冻结");
//			return result;
//		}
	}
}
