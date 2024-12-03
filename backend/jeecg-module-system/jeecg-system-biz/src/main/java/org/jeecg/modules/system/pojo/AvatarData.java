package org.jeecg.modules.system.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.system.entity.Avatar;
import javax.sql.rowset.serial.SerialBlob;
import java.sql.SQLException;
import java.util.Base64;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvatarData {
    private String base64Data;
    
    public AvatarData(Avatar avatar) throws SQLException {
        if (avatar != null && avatar.getAvatar() != null) {
            byte[] bytes = avatar.getAvatar().getBytes(1, (int) avatar.getAvatar().length());
            this.base64Data = Base64.getEncoder().encodeToString(bytes);
        }
    }
    
    public static Avatar toAvatar(String base64Data) throws SQLException {
        if (base64Data != null && !base64Data.isEmpty()) {
            byte[] bytes = Base64.getDecoder().decode(base64Data);
            SerialBlob blob = new SerialBlob(bytes);
            return new Avatar(null, blob);
        }
        return null;
    }
}
