package xiaor;

import java.util.HashMap;

public class AttackDamageCal {
    private final MessagePack pack;
    private HashMap<DamageBuffType, Double> damageBuffMap;

    public AttackDamageCal(MessagePack pack) {
        damageBuffMap = new HashMap<>();
        this.pack = pack;
    }

    public void changeDamage(DamageBuffType type, double percent){
        if(damageBuffMap.containsKey(type)) {
            damageBuffMap.put(type, damageBuffMap.get(type) + percent);
        }
    }

    public void finalDamage() {
        double baseDamage = pack.caster.getAttack();
        int finalDamage = damageBuffMap.values().stream().map(aDouble -> aDouble + 1)
                .reduce(baseDamage, (a, b) -> (a * b)).intValue();
        System.out.println("damage:" + finalDamage);

    }

    public boolean handle() {
        TriggerManager.getInstance().sendMessage(Trigger.ATTACK_DAMAGE_CAL, MessagePack.builder().attackDamageCal(this).build());
        finalDamage();
        return true;
    }
}
