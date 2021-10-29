package xiaor;

import java.util.ArrayList;
import java.util.List;

import static xiaor.Trigger.*;

public class TriggerManager {
    public List<Skill> skills;

    private static final TriggerManager triggerManager = new TriggerManager();

    private TriggerManager() {
        skills = new ArrayList<>();
    }

    public static TriggerManager getInstance() {
        return triggerManager;
    }

    public boolean registerSkill(Skill skill) {
        skills.add(skill);
        return true;
    }

    public void sendMessage(Trigger trigger, MessagePack pack) {
        //普通for循环防止迭代器问题
        for (int i = 0; i < skills.size(); i++) {
            if (!skills.get(i).getTrigger().equals(trigger)) continue;
            if (!skills.get(i).check(pack)) continue;
            skills.get(i).cast(pack);
        }
    }

    public void registerNormalAttack(Chara caster, double percent) {
        registerSkill(new BaseSkill(ATTACK, pack -> pack.caster.equals(caster),
                pack -> new DamageCal(pack).normalAttack(percent)));
    }

    public void registerSkillAttack(Chara caster, double multi) {
        registerSkill(new BaseSkill(SKILL, pack -> pack.caster.equals(caster),
                pack -> new DamageCal(pack).skillAttack(multi)));
    }

    public void registerSelfAttackInc(Chara caster, Trigger trigger, double inc) {
        Skill incAtkSkill = new BaseSkill(ATTACK_CAL, pack -> pack.caster.equals(caster),
                pack -> pack.getDamageCal().changeDamage(DamageBuffType.攻击力增加, inc));

        BaseSkill skill = BaseSkill.builder()
                .trigger(trigger).check(pack -> pack.caster.equals(caster))
                .cast(pack -> registerSkill(incAtkSkill))
                .type(SkillType.ONCE)       //增加攻击力只持续1次
                .build();

        registerSkill(skill);
    }
}
