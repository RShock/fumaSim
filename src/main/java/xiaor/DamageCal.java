package xiaor;

import xiaor.charas.Chara;
import xiaor.skillbuilder.skill.BuffType;
import xiaor.tools.Tools;
import xiaor.tools.record.DamageRecord;
import xiaor.trigger.TriggerEnum;
import xiaor.trigger.TriggerManager;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static xiaor.DamageCal.InternalBuffType.*;

public class DamageCal {
    public final MessagePack pack;
    public final HashMap<BuffType, Double> damageBuffMap;

    public DamageCal(MessagePack pack) {
        damageBuffMap = new HashMap<>();
        this.pack = pack;
    }

    public void changeDamage(BuffType type, double percent){
        if(damageBuffMap.containsKey(type)) {
            damageBuffMap.put(type, damageBuffMap.get(type) + percent);
        }else {
            damageBuffMap.put(type, percent);
        }
    }

    public void finalDamage(Chara acceptor, double percent, TriggerEnum skillTypeEnum) {
        double baseDamage = pack.caster.getBaseAttack() * percent;
        TriggerManager.sendMessage(skillTypeEnum, MessagePack.damagePack(this, acceptor));
        TriggerManager.sendMessage(TriggerEnum.伤害计算, MessagePack.damagePack(this, acceptor));
        int finalDamage = (int)damageBuffMap.entrySet().stream()
                .filter(entry -> buffTypeMap.get(entry.getKey()) != 属性克制)
                .collect(Collectors.groupingBy(entry -> buffTypeMap.get(entry.getKey())))
                .values()
                .stream()
                .mapToDouble(entries -> entries.stream().mapToDouble(Map.Entry::getValue).sum())
                .reduce(baseDamage, (a, b) -> (a * (1 + b)));

        //属性克制特殊逻辑
        double 属性克制 = 0;
        if(damageBuffMap.containsKey(BuffType.属性克制)) {
            属性克制 = damageBuffMap.get(BuffType.属性克制);
        }
        double 属性相克效果增减 = 0;
        if(damageBuffMap.containsKey(BuffType.属性相克效果)) {
            属性相克效果增减 = damageBuffMap.get(BuffType.属性相克效果);
        }
        finalDamage *= (1+属性克制 * (1-属性相克效果增减));

        int currentES = acceptor.getShield();
        int lifeRemain = acceptor.getLife();
        String msg;
        if(currentES != 0) {
            if(finalDamage > currentES) {
                msg = "%s对%s造成了%d(%d)伤害".formatted(pack.caster, pack.acceptors, finalDamage-currentES, currentES);
                lifeRemain -= finalDamage-currentES;
                acceptor.setShield(0);
            }else{
                msg = "%s对%s造成了0(%d)伤害".formatted(pack.caster, pack.acceptors, finalDamage);
                acceptor.setShield(currentES - finalDamage);
            }
        }else{
            msg = "%s对%s造成了%d伤害".formatted(pack.caster, pack.acceptors, finalDamage);
            lifeRemain -= finalDamage;
        }
        Tools.log(Tools.LogColor.BLUE, msg);
        acceptor.setLife(lifeRemain);
        TriggerManager.sendMessage(TriggerEnum.造成伤害, MessagePack.builder().result(
                new DamageRecord(skillTypeEnum, msg, pack.caster, acceptor, finalDamage)).build());
        Tools.log(Tools.LogColor.GREEN, acceptor + "剩余" + lifeRemain);
        damageBuffMap.clear();
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
        buffMap.put(BuffType.攻击力数值, 基本攻击力计算);
        buffMap.put(BuffType.普攻伤害, 杂项);
        buffMap.put(BuffType.攻击力, 基本攻击力计算);
        buffMap.put(BuffType.造成伤害, 造成伤害增加);
        buffMap.put(BuffType.受到伤害, 易伤);
        buffMap.put(BuffType.受到攻击者伤害, 杂项);
        buffMap.put(BuffType.受到精灵王伤害, 杂项);
        buffMap.put(BuffType.受到自身伤害, 杂项);
        buffMap.put(BuffType.受到风属性伤害, 属性易伤);
        buffMap.put(BuffType.属性克制, 属性克制);
        buffMap.put(BuffType.必杀技伤害, 杂项);
        buffMap.put(BuffType.受到普攻伤害, 杂项);
        buffMap.put(BuffType.属性相克效果, 属性克制);
        return buffMap;
    }

    //计算基本攻击力
    public int getCurrentAttack() {
        TriggerManager.sendMessage(TriggerEnum.攻击力计算, MessagePack.damagePack(this, null));
        double baseAtk = pack.caster.getBaseAtk();

        int finalAtk = damageBuffMap.entrySet().stream()
                .filter(s -> !s.getKey().equals(BuffType.攻击力数值))
                .map(s -> s.getValue() + 1)
                .reduce(baseAtk, (a, b) -> (a * b)).intValue();
        if(damageBuffMap.containsKey(BuffType.攻击力数值)) {
            finalAtk += damageBuffMap.get(BuffType.攻击力数值);
        }
        damageBuffMap.clear();

        Tools.log("%s当前攻击力是%d".formatted(pack.caster, finalAtk));
        return finalAtk;
    }

    public void skillAttack(double multi) {
        pack.acceptors.forEach(acceptor -> finalDamage(acceptor, multi, TriggerEnum.技能伤害计算));
    }

    //普攻
    public void normalAttack(double percent) {
        pack.acceptors.forEach(acceptor -> finalDamage(acceptor, percent, TriggerEnum.普攻伤害计算));
    }
}
