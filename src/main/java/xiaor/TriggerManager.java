package xiaor;

import xiaor.charas.胆小纸袋狼_沃沃;

import java.util.ArrayList;
import java.util.List;

import static xiaor.Trigger.*;

public class TriggerManager {
    public List<Skill> skills;

    private static TriggerManager triggerManager = new TriggerManager();

    private TriggerManager() {
        skills = new ArrayList<>();
    }

    public static TriggerManager getInstance() {
        return triggerManager;
    }

    public void registerSkill(Skill skill) {
        skills.add(skill);
    }

    public void sendMessage(Trigger trigger, MessagePack pack) {
        skills.stream().filter(skill -> skill.getTrigger().equals(trigger))
                .filter(skill -> skill.check(pack))
                .forEach(skill -> skill.cast(pack));
    }

    public void registerNormalAttack(Chara caster, double percent) {
        registerSkill(new BaseSkill(ATTACK, pack -> pack.caster.equals(caster),
                pack -> new DamageCal(pack).normalAttack(percent)));
    }

    public void registerSkillAttack(Chara caster, double multi) {
        registerSkill(new BaseSkill(SKILL, pack -> pack.caster.equals(caster),
                pack -> new DamageCal(pack).skillAttack(multi)));
    }

    public void registerSelfAttackInc(Chara caster, double inc) {
        registerSkill(new BaseSkill(ATTACK_CAL, pack -> pack.caster.equals(caster),
                pack -> pack.getDamageCal().changeDamage(DamageBuffType.攻击力增加, inc)));
    }
}
