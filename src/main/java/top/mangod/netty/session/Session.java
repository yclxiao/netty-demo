package top.mangod.netty.session;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录之后记录用户的session信息
 */
@Data
@NoArgsConstructor
public class Session {
    // 用户唯一性标识
    private String userId;
    private String userName;

    public Session(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return userId + ":" + userName;
    }

}
