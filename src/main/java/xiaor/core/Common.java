package xiaor.core;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Common {
    public static final int INFINITY = 999;   //无限持续的技能

    public static final String 目录型输出path = "目录型输出.html";

    public static String getResourcePath(Class c, String resourceName) {
        return URLDecoder.decode(Objects.requireNonNull(c.getClassLoader().getResource(resourceName)).getPath()
                , StandardCharsets.UTF_8);
    }
}
