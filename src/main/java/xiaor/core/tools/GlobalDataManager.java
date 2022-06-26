package xiaor.core.tools;

import java.util.HashMap;

/**
 * 全局变量类，任何方法都可以在这里申请与读取动态变量
 */
public class GlobalDataManager {
    private final HashMap<String, String> data = new HashMap<>();
    private final HashMap<String, Integer> intData = new HashMap<>();
    private static final GlobalDataManager manager = new GlobalDataManager();

    public static Integer getIntData(KeyEnum key) {
        return manager.intData.get(key.toString());
    }

    public static String getData(KeyEnum key) {
        return manager.data.get(key.toString());
    }

    public static void putData(KeyEnum key, String value) {
        manager.data.put(key.toString(), value);
    }

    public static void putIntData(KeyEnum key, Integer value) {
        manager.intData.put(key.toString(), value);
    }

    public static int incIntData(KeyEnum key) {
        int data = manager.intData.get(key.toString());
        manager.intData.put(key.toString(), data+1);
        return data+1;
    }

    public static void reset() {
        manager.data.clear();
        manager.intData.clear();
    }
}
