package xiaor;

import java.util.HashMap;

import static xiaor.DamageBuffType.攻击力增加;

public class DamageCal {
    private final MessagePack pack;
    private HashMap<DamageBuffType, Double> damageBuffMap;

    public DamageCal(MessagePack pack) {
        damageBuffMap = new HashMap<>();
        this.pack = pack;
    }

    public boolean changeDamage(DamageBuffType type, double percent){
        if(damageBuffMap.containsKey(type)) {
            damageBuffMap.put(type, damageBuffMap.get(type) + percent);
        }
        return true;
    }

    public void finalDamage(double percent) {
        double baseDamage = getCurrentAttack() * percent;
        int finalDamage = damageBuffMap.values().stream().map(aDouble -> aDouble + 1)
                .reduce(baseDamage, (a, b) -> (a * b)).intValue();
        System.out.println("damage:" + finalDamage);
        int lifeRemain = pack.acceptor.getLife() - finalDamage;
        pack.acceptor.setLife(lifeRemain);
        System.out.println("life remain:" + lifeRemain);
    }

    public boolean normalAttack(double percent) {
        TriggerManager.getInstance().sendMessage(Trigger.ATTACK_DAMAGE_CAL, MessagePack.builder().damageCal(this).build());
        finalDamage(percent);
        return true;
    }

    public int getCurrentAttack() {
        TriggerManager.getInstance().sendMessage(Trigger.ATTACK_CAL, MessagePack.builder().damageCal(this).build());
        double baseAtk = pack.caster.getAttack();
        int finalAtk = damageBuffMap.entrySet().stream()
                .filter(s -> !s.getKey().equals(DamageBuffType.攻击力数值增加))
                .map(s -> s.getValue() + 1)
                .reduce(baseAtk, (a, b) -> (a * b)).intValue();
        if(damageBuffMap.containsKey(DamageBuffType.攻击力数值增加)) {
            finalAtk += damageBuffMap.get(DamageBuffType.攻击力数值增加);
        }
        return finalAtk;
    }
    public boolean skillAttack(double percent) {
        TriggerManager.getInstance().sendMessage(Trigger.SKILL_DAMAGE_CAL, MessagePack.builder().damageCal(this).build());
        finalDamage(percent);
        return true;
    }
}
