package top.mangod.netty.serialize;

public interface SerializerAlgorithm {
    /**
     * json 序列化
     */
    byte JSON = 1;

    /**
     * 自定义序列化
     */
    byte CUSTOMIZE = 2;
}
