package xiaor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static xiaor.Common.INFI;
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

    public void reset() {
        triggerManager = new TriggerManager();
    }

    public boolean registerSkill(Skill skill) {
        System.out.println("＋新增：" + skill);
        skills.add(skill);
        return true;
    }

    public boolean registerBuff(Buff buff) {
        System.out.println("＋新增buff: " + buff);
        skills.add(buff);
        return true;
    }

    public void sendMessage(Trigger trigger, MessagePack pack) {
        //普通for循环防止迭代器问题
        for (int i = 0; i < skills.size(); i++) {
            if (!skills.get(i).getTrigger().equals(trigger)) continue;
            if (!skills.get(i).check(pack)) continue;
            System.out.println("※触发" + trigger + " -> " + skills.get(i).toString());
            skills.get(i).cast(pack);
        }
    }

    public void registerNormalAttack(Chara caster, String name, double percent) {
        registerSkill(new BaseSkill(name, 普通攻击, pack -> pack.checkCaster(caster),
                pack -> new DamageCal(pack).normalAttack(percent), INFI));
    }

    public void registerSkillAttack(Chara caster, String name, double multi, Function<MessagePack, Boolean> callback) {
        BaseSkill atkSkill = BaseSkill.builder().name(name).trigger(SKILL).check(pack -> pack.checkCaster(caster))
                .cast(pack -> {
                    new DamageCal(pack).skillAttack(multi);
                    return callback.apply(pack);
                })
                .time(INFI)
                .build();
        registerSkill(atkSkill);
    }

    public void registerSelfAttackInc(Chara caster, String buffName, Trigger trigger, double inc) {
        registerAttackInc(caster, caster, buffName, trigger, inc, INFI);
    }

    public void registerSelfAttackInc(Chara caster, String buffName, Trigger trigger, double inc, int during) {
        registerAttackInc(caster, caster, buffName, trigger, inc, during);
    }

    public void registerAttackInc(Chara caster, Chara acceptor, String buffName, Trigger trigger, double inc) {
        registerAttackInc(caster, acceptor, buffName, trigger, inc, 50);
    }

    public void registerAttackInc(Chara caster, Chara acceptor, String buffName, Trigger trigger, double inc, int during) {
        Buff atkIncBuff = Buff.builder()
                .caster(caster)
                .acceptor(acceptor)
                .buffName(buffName)
                .time(during)
                .type(SkillType.CONTINUIOUS)
                .trigger(攻击力计算)
                .check(pack -> pack.checkCaster(acceptor))
                .cast(pack -> pack.getDamageCal().changeDamage(BuffType.攻击力增加, inc))
                .build();

        Skill skill = BaseSkill.builder()
                .trigger(trigger).check(pack -> true)
                .cast(pack -> registerBuff(atkIncBuff))
                .type(SkillType.ONCE)       //增加攻击力只持续1次
                .name(caster + "要在" + trigger + "场合使用" + atkIncBuff)
                .build();

        registerSkill(skill);
    }

    public void addAtkIncImi(Chara caster, Chara acceptor, String buffName, double inc, int during) {
        Buff atkIncBuff = Buff.builder()
                .caster(caster)
                .acceptor(acceptor)
                .buffName(buffName)
                .time(during)
                .type(SkillType.CONTINUIOUS)
                .trigger(攻击力计算)
                .check(pack -> pack.checkCaster(acceptor))
                .cast(pack -> pack.getDamageCal().changeDamage(BuffType.攻击力增加, inc))
                .build();
        registerBuff(atkIncBuff);
    }

}
