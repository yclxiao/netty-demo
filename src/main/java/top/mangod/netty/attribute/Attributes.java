package top.mangod.netty.attribute;

import io.netty.util.AttributeKey;
import top.mangod.netty.session.Session;

public interface Attributes {
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
