package org.jeecg.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.sql.rowset.serial.SerialBlob;
import org.springframework.stereotype.Component;

@Data
@Component
@TableName("avatar")
@NoArgsConstructor
@AllArgsConstructor
public class Avatar {
    @TableId(value = "id")
    private Integer avatarId;
    @TableField(value = "data")
    private SerialBlob avatar;
}
