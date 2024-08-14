package xiaor.core.damageCal;

import xiaor.core.charas.Chara;
import xiaor.core.logger.LogType;
import xiaor.core.logger.Logger;
import xiaor.core.msgpack.BuffCalPack;
import xiaor.core.msgpack.DamageRecordPack;
import xiaor.core.msgpack.MessagePack;
import xiaor.core.skillbuilder.skill.BuffType;
import xiaor.core.tools.record.DamageRecord;
import xiaor.core.trigger.TriggerEnum;
import xiaor.core.trigger.TriggerManager;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static xiaor.core.damageCal.DamageCal.InternalBuffType.*;

public class DamageCal {
    public final MessagePack pack;

    public DamageCal(MessagePack pack) {
        this.pack = pack;
    }

    public void finalDamage(Chara acceptor, double percent, DamageBase baseType, int times, TriggerEnum skillTypeEnum) {
        Logger.INSTANCE.log(LogType.触发BUFF, "伤害倍率%s%%，伤害类型:%s，%s次".formatted(percent * 100, baseType, times));
        double baseDamage = percent * switch (baseType) {
            case 攻击 -> pack.caster.getCurrentAttack();
            case 生命 -> pack.caster.getCurrentMaxLife();
        };
        /**
         * 伤害分为5种 普攻 必杀 普攻触发 必杀触发 反伤触发 流血
         */
        BuffCalPack damageCalPack = new BuffCalPack(pack.caster, acceptor);

        switch (skillTypeEnum) {
            case 普攻伤害计算, 技能伤害计算 -> TriggerManager.sendMessage(skillTypeEnum, damageCalPack);
            case 普攻触发伤害计算 -> {
                TriggerManager.sendMessage(TriggerEnum.普攻伤害计算, damageCalPack);
                TriggerManager.sendMessage(TriggerEnum.触发伤害计算, damageCalPack);
            }
            case 技能触发伤害计算 -> {
                TriggerManager.sendMessage(TriggerEnum.技能伤害计算, damageCalPack);
                TriggerManager.sendMessage(TriggerEnum.触发伤害计算, damageCalPack);
            }
        }
        TriggerManager.sendMessage(TriggerEnum.伤害计算, damageCalPack);
        var buffMap = damageCalPack.getBuffMap();
        int finalDamage = (int) damageCalPack.getBuffMap().entrySet().stream()
                .collect(Collectors.groupingBy(entry -> buffTypeMap.get(entry.getKey())))
                .values()
                .stream()
                .mapToDouble(entries -> entries.stream().mapToDouble(Map.Entry::getValue).sum())
                .peek(num -> Logger.INSTANCE.log(LogType.测试用, String.valueOf(num)))
                .reduce(baseDamage, (a, b) -> (a * (1 + b)));

        //属性克制特殊逻辑
//        double 属性克制 = 0;
//        if (buffMap.containsKey(BuffType.属性克制)) {
//
//            属性克制 = buffMap.get(BuffType.属性克制);
//        }
//        double 属性相克效果增减 = 0;
//        if (buffMap.containsKey(BuffType.属性相克效果)) {
//            属性相克效果增减 = buffMap.get(BuffType.属性相克效果);
//        }
//        finalDamage *= (1 + 属性克制 * (1 - 属性相克效果增减));
        /*

        注意：属性相克效果增减没做
         */

        for (int i = 0; i < times; i++) {
            int currentES = acceptor.getShield();
            long lifeRemain = acceptor.getLife();
            String msg;
            if (currentES != 0) {
                if (finalDamage > currentES) {
                    msg = "%s对%s造成了%d(%d)伤害".formatted(pack.caster, pack.acceptors, finalDamage - currentES, currentES);
                    lifeRemain -= finalDamage - currentES;
                    acceptor.setShield(0);
                } else {
                    msg = "%s对%s造成了0(%d)伤害".formatted(pack.caster, pack.acceptors, finalDamage);
                    acceptor.setShield(currentES - finalDamage);
                }
            } else {
                msg = "%s对%s造成了%d伤害".formatted(pack.caster, pack.acceptors, finalDamage);
                lifeRemain -= finalDamage;
            }
            Logger.INSTANCE.log(LogType.造成伤害, msg);
            acceptor.setLife(lifeRemain);
            TriggerManager.sendMessage(TriggerEnum.造成伤害, new DamageRecordPack(
                    new DamageRecord(skillTypeEnum, msg, pack.caster, acceptor, finalDamage)));
            TriggerManager.sendMessage(TriggerEnum.受到攻击时, MessagePack.builder().caster(acceptor).acceptors(Collections.singletonList(pack.caster)).build());

        }
        Logger.INSTANCE.log(LogType.造成伤害, acceptor + "剩余" + acceptor.getLife());
        buffMap.clear();
    }

    public void dotAttack(double multi, DamageBase baseType) {
        /**
         * dot攻击的特点是伤害并非事实结算，而是附加了一个只吃易伤的dot buff
         * 回合结束时。buff才会真正的调用dotAttack
         */
    }

    public void normalAddAttack(List<Chara> acceptors, double multi, DamageBase baseType, int times) {
        acceptors.forEach(acceptor -> finalDamage(acceptor, multi, baseType, times, TriggerEnum.普攻触发伤害计算));
    }

    public void skillAddAttack(List<Chara> acceptors, double multi, DamageBase baseType, int times) {
        acceptors.forEach(acceptor -> finalDamage(acceptor, multi, baseType, times, TriggerEnum.技能触发伤害计算));
    }

    /*
        伤害计算分为7大区
        基本攻击力计算
        易伤
        属性易伤
        造成伤害增加
        杂项
        属性克制
        触发
        内部是加减法，外部是乘法，这里需要分类
     */
    public enum InternalBuffType {
        基本攻击力计算,
        易伤,
        属性易伤,
        造成伤害增加,
        杂项,
        属性克制,
        触发
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
        buffMap.put(BuffType.受到水属性伤害, 属性易伤);
        buffMap.put(BuffType.受到光属性伤害, 属性易伤);
        buffMap.put(BuffType.受到暗属性伤害, 属性易伤);
        buffMap.put(BuffType.受到火属性伤害, 属性易伤);
        buffMap.put(BuffType.属性克制, 属性克制);
        buffMap.put(BuffType.必杀技伤害, 杂项);
        buffMap.put(BuffType.受到普攻伤害, 杂项);
        buffMap.put(BuffType.受到必杀伤害, 杂项);
        buffMap.put(BuffType.触发伤害, 触发);
        buffMap.put(BuffType.受到连环陷阱属性伤害, 属性易伤);
        return buffMap;
    }

    public void skillAttack(List<Chara> acceptors, double multi, DamageBase baseType, int times) {
        acceptors.forEach(acceptor -> finalDamage(acceptor, multi, baseType, times, TriggerEnum.技能伤害计算));
    }

    //普攻
    public void normalAttack(List<Chara> acceptors, double percent, DamageBase baseType, int times) {
        acceptors.forEach(acceptor -> finalDamage(acceptor, percent, baseType, times, TriggerEnum.普攻伤害计算));
    }
}
