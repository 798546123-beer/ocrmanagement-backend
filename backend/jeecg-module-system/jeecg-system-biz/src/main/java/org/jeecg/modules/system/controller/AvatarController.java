package org.jeecg.modules.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.jeecg.common.api.vo.Result;
import org.jeecg.config.shiro.IgnoreAuth;
import org.jeecg.modules.system.entity.Avatar;
import org.jeecg.modules.system.pojo.AvatarData;
import org.jeecg.modules.system.service.AvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/avatar")
@Api(tags = "头像相关接口")
public class AvatarController {

    @Autowired
    private AvatarService avatarService;

    @PostMapping("/upload")
    @ApiOperation("上传头像")
    @IgnoreAuth
    public Result<?> uploadAvatar(@RequestBody AvatarData avatarData) {
        try {
            Avatar avatar = AvatarData.toAvatar(avatarData.getBase64Data());
            avatarService.save(avatar);
            return Result.OK("头像上传成功");
        } catch (Exception e) {
            log.error("头像上传失败", e);
            return Result.error("头像上传失败: " + e.getMessage());
        }
    }

    @GetMapping("/get/{id}")
    @ApiOperation("获取头像")
    @IgnoreAuth
    public Result<?> getAvatar(@PathVariable Integer id) {
        try {
            Avatar avatar = avatarService.getById(id);
            if (avatar == null) {
                return Result.error("头像不存在");
            }
            AvatarData avatarData = new AvatarData(avatar);
            return Result.OK(avatarData);
        } catch (Exception e) {
            log.error("获取头像失败", e);
            return Result.error("获取头像失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除头像")
    @IgnoreAuth
    public Result<?> deleteAvatar(@Param("id") Integer id) {
        try {
            if (avatarService.removeById(id)) {
                return Result.OK("删除成功");
            }
            return Result.error("删除失败，头像不存在");
        } catch (Exception e) {
            log.error("删除头像失败", e);
            return Result.error("删除头像失败: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    @ApiOperation("更新头像")
    @IgnoreAuth
    public Result<?> updateAvatar(@RequestBody AvatarData avatarData, @RequestParam Integer id) {
        try {
            Avatar avatar = AvatarData.toAvatar(avatarData.getBase64Data());
            avatar.setAvatarId(id);
            if (avatarService.updateById(avatar)) {
                return Result.OK("更新成功");
            }
            return Result.error("更新失败，头像不存在");
        } catch (Exception e) {
            log.error("更新头像失败", e);
            return Result.error("更新头像失败: " + e.getMessage());
        }
    }
}
