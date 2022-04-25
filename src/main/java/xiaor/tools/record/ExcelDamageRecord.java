package xiaor.tools.record;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import xiaor.GameBoard;

import java.util.ArrayList;
import java.util.List;

/**
 * 一回合有5个伤害数据
 */
@Getter
@Setter
public class ExcelDamageRecord {
    private List<String> action;    //example:1a 2a 3a..
    private List<Integer> damage;    //example:114514

    public ExcelDamageRecord(List<String> action, List<Integer> damage) {
        this.action = action;
        this.damage = damage;
        convert();
    }

    /**
     * 伤害队列一般记录的是角色的行动顺序 比如2a 1a 3a...
     * 然后记录的伤害也是跟着这个顺序，先记录了2号位的伤害
     * 这导致列表显示伤害时无法直观显示每个角色的伤害，因为它们分布在不同的列
     * 为此需要重新排序成角色位置固定的情况 这种情况下action记录的是每个角色行动的次序。
     */
    private void convert() {
        List<String> newAction = new ArrayList<>(action);         //角色位固定的行动顺序
        List<Integer> newDamage = new ArrayList<>(damage);    //角色位固定的伤害
        for(int i=0;i<action.size();i++) {
            int turn = i/5; //回合数 从0开始
            int charaIndex = Integer.parseInt(action.get(i).substring(0, 1))-1;
            int realPos = turn*5+charaIndex;
            newAction.set(realPos, i%5+1+toActionString(action.get(i).substring(1)));
            newDamage.set(realPos, damage.get(i));
        }
        action = newAction;
        damage = newDamage;
    }

    private String toActionString(String s) {
        return switch (s) {
            case String ignored && s.toLowerCase().contains("a") -> "攻击";
            case String ignored && s.toLowerCase().contains("q") -> "必杀";
            case String ignored && s.toLowerCase().contains("d") -> "防御";
            default -> "未知";
        };
    }
}
