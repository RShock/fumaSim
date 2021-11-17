package xiaor;

import xiaor.skillbuilder.action.BuffType;
import xiaor.tools.Tools;
import xiaor.tools.TriggerEnum;
import xiaor.tools.TriggerManager;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DamageCal {
    public final MessagePack pack;
    public HashMap<BuffType, Double> damageBuffMap;

    public DamageCal(MessagePack pack) {
        damageBuffMap = new HashMap<>();
        this.pack = pack;
    }

    public boolean changeDamage(BuffType type, double percent){
        if(damageBuffMap.containsKey(type)) {
            damageBuffMap.put(type, damageBuffMap.get(type) + percent);
        }else
        {
            damageBuffMap.put(type, percent);
        }
        return true;
    }

    public void finalDamage(double percent, TriggerEnum triggerEnum) {
        double baseDamage = getCurrentAttack() * percent;
        TriggerManager.sendMessage(triggerEnum, MessagePack.damagePack(this));
        TriggerManager.sendMessage(TriggerEnum.伤害计算, MessagePack.damagePack(this));
        int finalDamage = (int)damageBuffMap.entrySet().stream()
                .collect(Collectors.groupingBy(entry -> buffTypeMap.get(entry.getKey())))
                .values()
                .stream()
                .mapToDouble(entries -> entries.stream().mapToDouble(Map.Entry::getValue).sum())
                .reduce(baseDamage, (a, b) -> (a * (1 + b)));
        Tools.log(Tools.LogColor.BLUE, pack.caster + "对" + pack.acceptor + "造成了" + finalDamage + "伤害");
        int lifeRemain = pack.acceptor.getLife() - finalDamage;
        pack.acceptor.setLife(lifeRemain);
        System.out.println(pack.acceptor + "剩余" + lifeRemain);
        damageBuffMap.clear();
    }

    //普攻
    public boolean normalAttack(double percent) {
        finalDamage(percent, TriggerEnum.普攻伤害计算);
        TriggerManager.sendMessage(TriggerEnum.释放普攻后, pack);
        return true;
    }

    /*
        伤害计算分为6大区
        基本攻击力计算
        易伤
        属性易伤
        造成伤害增加
        杂项
        属性克制
        内部是加减法，外部是乘法，这里需要分类
     */
    public enum InternalBuffType {
        基本攻击力计算,
        易伤,
        属性易伤,
        造成伤害增加,
        杂项,
        属性克制
    }

    private static final HashMap<BuffType, InternalBuffType> buffTypeMap = getBuffTypeMap();

    private static HashMap<BuffType, InternalBuffType> getBuffTypeMap() {
        HashMap<BuffType, InternalBuffType> buffMap = new HashMap<>();
        buffMap.put(BuffType.攻击力数值增加, InternalBuffType.基本攻击力计算);
        buffMap.put(BuffType.普攻伤害增加, InternalBuffType.杂项);
        buffMap.put(BuffType.攻击力百分比增加, InternalBuffType.基本攻击力计算);
        buffMap.put(BuffType.造成伤害增加, InternalBuffType.造成伤害增加);
        buffMap.put(BuffType.受到伤害增加, InternalBuffType.易伤);
        buffMap.put(BuffType.受到攻击者伤害增加, InternalBuffType.杂项);
        buffMap.put(BuffType.受到精灵王伤害增加, InternalBuffType.杂项);
        buffMap.put(BuffType.受到风属性伤害增加, InternalBuffType.属性易伤);
        buffMap.put(BuffType.属性克制, InternalBuffType.属性克制);
        buffMap.put(BuffType.必杀技伤害增加, InternalBuffType.杂项);
        return buffMap;
    }

    //计算基本攻击力
    public int getCurrentAttack() {
        TriggerManager.sendMessage(TriggerEnum.攻击力计算, MessagePack.damagePack(this));
        double baseAtk = pack.caster.getAttack();

        int finalAtk = damageBuffMap.entrySet().stream()
                .filter(s -> !s.getKey().equals(BuffType.攻击力数值增加))
                .map(s -> s.getValue() + 1)
                .reduce(baseAtk, (a, b) -> (a * b)).intValue();
        if(damageBuffMap.containsKey(BuffType.攻击力数值增加)) {
            finalAtk += damageBuffMap.get(BuffType.攻击力数值增加);
        }
        damageBuffMap.clear();
        System.out.println(pack.caster + "基础攻击力是" + finalAtk);
        return finalAtk;
    }

    public boolean skillAttack(double multi) {
        finalDamage(multi, TriggerEnum.技能伤害计算);
        TriggerManager.sendMessage(TriggerEnum.释放必杀后, pack);
        return true;
    }

    public boolean appendNormalAttack(double multi) {
        finalDamage(multi, TriggerEnum.普攻伤害计算);
        TriggerManager.sendMessage(TriggerEnum.释放追击普攻后, pack);
        return true;
    }
}
