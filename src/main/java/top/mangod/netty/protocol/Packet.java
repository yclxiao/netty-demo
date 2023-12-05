package top.mangod.netty.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import top.mangod.netty.serialize.SerializerAlgorithm;

@Data
public abstract class Packet {
    /**
     * 协议版本
     */
    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1;

    /**
     * 指令
     *
     * @return
     */
    @JSONField(serialize = false)
    public abstract Byte getCommand();

    /**
     * 获取算法，如果使用其余算法，子类重写些方法
     *
     * @return
     */
    public Byte getSerializeAlgorithm() {
        return SerializerAlgorithm.JSON;
    }
}
