package charaTest;

import org.junit.jupiter.api.Assertions;
import xiaor.GameBoard;
import xiaor.charas.Chara;
import xiaor.charas.ImportedChara;
import xiaor.logger.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static org.apache.logging.log4j.ThreadContext.isEmpty;

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
        List<String> split = Arrays.stream(action.split("\\s+")).filter(s -> !s.isEmpty()).toList();
        gameBoard.run("");
        int count = 0;
        for (int life : enemyLife) {
            gameBoard.continueRun(split.get(count++));
            if (count < split.size() && split.get(count).equals("|")) {
                gameBoard.continueRun("|");
                count++;
            }
            if (life == 0) continue;
            Assertions.assertEquals(life, 被打的角色.getLife(), 10);
        }
        Logger.INSTANCE.exportHtmlLog();
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
