package xiaor.tools.record;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

/**
 * 一回合有5个伤害数据
 */
@Getter
@Setter
public class ExcelDamageRecord {
    public record DamagePair(String action, Integer damage) {
    }

    public List<DamagePair> damagePairs;

    public ExcelDamageRecord(List<String> action, List<Integer> damage) {
        if (action.size() != damage.size()) {
            throw new RuntimeException("行动与伤害数组大小不统一");
        }
        damagePairs = IntStream.range(0, action.size()).mapToObj(i -> new DamagePair(action.get(i), damage.get(i)))
                .toList();
        convert();
    }

    /**
     * 伤害队列一般记录的是角色的行动顺序 比如2a 1a 3a...
     * 然后记录的伤害也是跟着这个顺序，先记录了2号位的伤害
     * 这导致列表显示伤害时无法直观显示每个角色的伤害，因为它们分布在不同的列
     * 为此需要重新排序成角色位置固定的情况 这种情况下action记录的是每个角色行动的次序。
     */
    private void convert() {
        List<DamagePair> newPair = new ArrayList<>(damagePairs);    //角色位固定的伤害
        for (int i = 0; i < damagePairs.size(); i++) {
            int turn = i / 5; //回合数 从0开始
            int charaIndex = Integer.parseInt(damagePairs.get(i).action.substring(0, 1)) - 1;
            int realPos = turn * 5 + charaIndex;
            String actionWord = i % 5 + 1 + toActionString(damagePairs.get(i).action.substring(1));
            newPair.set(realPos, new DamagePair(actionWord, damagePairs.get(i).damage));
        }
        damagePairs = newPair;
    }

    private String toActionString(String s) {
        if (s.toLowerCase().contains("a")) return "攻击";
        if (s.toLowerCase().contains("q")) return "必杀";
        if (s.toLowerCase().contains("d")) return "防御";
        return "未知";
    }

    public Long[] getDamagePart() {
        AtomicLong 必杀行动伤害 = new AtomicLong();
        AtomicLong 普攻行动伤害 = new AtomicLong();
        damagePairs.forEach(
                pair -> {
                    if (pair.action.contains("必杀")) {
                        必杀行动伤害.addAndGet(pair.damage);
                    }
                    if (pair.action.contains("攻击")) {
                        普攻行动伤害.addAndGet(pair.damage);
                    }
                }
        );
        return new Long[]{普攻行动伤害.get(), 必杀行动伤害.get()};
    }
}
