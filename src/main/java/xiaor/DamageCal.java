package xiaor;

import xiaor.story.BuffType;

import java.util.HashMap;

public class DamageCal {
    private final MessagePack pack;
    private HashMap<BuffType, Double> damageBuffMap;

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

    public void finalDamage(double percent) {
        double baseDamage = getCurrentAttack() * percent;
        int finalDamage = damageBuffMap.values().stream().map(aDouble -> aDouble + 1)
                .reduce(baseDamage, (a, b) -> (a * b)).intValue();
        System.out.println(pack.caster + "对" + pack.acceptor + "造成了" + finalDamage + "伤害");
        int lifeRemain = pack.acceptor.getLife() - finalDamage;
        pack.acceptor.setLife(lifeRemain);
        System.out.println(pack.acceptor + "剩余" + lifeRemain);
        damageBuffMap.clear();
    }

    //普攻
    public boolean normalAttack(double percent) {
        TriggerManager.getInstance().sendMessage(Trigger.ATTACK_DAMAGE_CAL, MessagePack.builder().damageCal(this).build());
        finalDamage(percent);

        return true;
    }

    //计算基本攻击力
    public int getCurrentAttack() {
        TriggerManager.getInstance().sendMessage(Trigger.攻击力计算, MessagePack.builder()
                .caster(pack.caster)
                .damageCal(this).build());
        double baseAtk = pack.caster.getAttack();
        int finalAtk = damageBuffMap.entrySet().stream()
                .filter(s -> !s.getKey().equals(BuffType.攻击力数值增加))
                .map(s -> s.getValue() + 1)
                .reduce(baseAtk, (a, b) -> (a * b)).intValue();
        if(damageBuffMap.containsKey(BuffType.攻击力数值增加)) {
            finalAtk += damageBuffMap.get(BuffType.攻击力数值增加);
        }
        damageBuffMap.clear();
        System.out.println(pack.caster + "当前攻击力是" + finalAtk);
        return finalAtk;
    }

    public boolean skillAttack(double percent) {
        TriggerManager.getInstance().sendMessage(Trigger.SKILL_DAMAGE_CAL, MessagePack.builder().damageCal(this).build());
        finalDamage(percent);
        TriggerManager.getInstance().sendMessage(Trigger.技能释放结束后, pack);
        return true;
    }
}
