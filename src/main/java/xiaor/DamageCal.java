package xiaor;

import xiaor.skillbuilder.action.BuffType;
import xiaor.tools.Tools;
import xiaor.tools.TriggerEnum;
import xiaor.tools.TriggerManager;

import java.util.HashMap;

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
        int finalDamage = damageBuffMap.values().stream().map(aDouble -> aDouble + 1)
                .reduce(baseDamage, (a, b) -> (a * b)).intValue();
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
