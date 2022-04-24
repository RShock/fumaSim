package xiaor;

import java.nio.charset.Charset;

public class Main {

    @SuppressWarnings("EmptyMethod")
    public static void main(String[] args) {
	// 目前程序入口为各种test
        System.out.println(System.getProperty("file.encoding"));
        System.out.println(Charset.defaultCharset());
        System.out.println("你好");
    }
}
