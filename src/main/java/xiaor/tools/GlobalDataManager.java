package xiaor.tools;

import java.util.HashMap;
import java.util.Optional;

/**
 * 全局变量类，任何方法都可以在这里申请与读取动态变量
 */
public class GlobalDataManager {
    private HashMap<String, String> data = new HashMap<>();
    private HashMap<String, Integer> intData = new HashMap<>();
    private static GlobalDataManager manager = new GlobalDataManager();

    public static GlobalDataManager getInstance() {
        return manager;
    }

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

}
