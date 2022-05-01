package charaTest;

import org.junit.jupiter.api.Assertions;
import xiaor.GameBoard;
import xiaor.charas.Chara;
import xiaor.charas.ImportedChara;

import java.util.List;
import java.util.function.Predicate;

public class TestTools {
    /**
     * 逐步检测伤害，不支持 | 符号强行结束回合
     *
     * @param gameBoard instance of gameBoard
     * @param action    角色行动轴
     * @param enemyLife 预期敌人血量
     * @param 被打的角色     被打的角色
     */
    public static void stepCheckRun(GameBoard gameBoard, String action, List<Integer> enemyLife, Chara 被打的角色) {
        String[] split = action.split("\\s+");
        gameBoard.run("");
        for (int i = 0; i < enemyLife.size(); i++) {
            gameBoard.continueRun(split[i]);
            if (enemyLife.get(i) == 0) continue;
            Assertions.assertEquals(enemyLife.get(i), 被打的角色.getLife(), 10);
        }
    }

    public static void fullCheck(String action, List<String> troop) {
        Chara 测试角色 = ImportedChara.initChara("测试_角色 生命7758206");
    }

    /**
     * 用来用二分法求解攻击力的
     *
     * @param predicate 二分判断函数
     * @return 结果
     */
    public static int biSearch(Predicate<Integer> predicate) {
        int low = 500000;
        int high = 1000000;
        while (low < high) {
            int mid = low + ((high - low) / 2);
            if (predicate.test(mid)) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return high;
    }
}
