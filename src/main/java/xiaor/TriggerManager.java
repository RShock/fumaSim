package xiaor;

import xiaor.story.BuffType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static xiaor.Common.INFI;
import static xiaor.Trigger.*;

public class TriggerManager {
    public List<Skill> skills;

    private int IDGen = 0;

    public static int getNewID() {
        return getInstance().getIDGen();
    }

    public int getIDGen() {
        return IDGen++;
    }

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

    public static boolean registerBuff(Buff buff) {
        return TriggerManager.getInstance().addBuff(buff);
    }

    private boolean addBuff(Buff buff) {
        System.out.println("＋新增buff: " + buff);
        skills.add(buff);
        return true;
    }

    public boolean respondMessage(Trigger trigger, MessagePack pack) {
        boolean isRespond = false;
        //普通for循环防止迭代器问题
        for (int i = 0; i < skills.size(); i++) {
            if (!skills.get(i).getTrigger().equals(trigger)) continue;
            if (!skills.get(i).check(pack)) continue;
            isRespond = true;
            System.out.println("※触发" + trigger + " -> " + skills.get(i).toString());
            skills.get(i).cast(pack);
        }
        return isRespond;
    }

    public void registerNormalAttack(Chara caster, String name, double percent) {
        registerSkill(new BaseSkill(name, 普通攻击, pack -> pack.checkCaster(caster),
                pack -> new DamageCal(pack).normalAttack(percent), INFI));
    }

    public void registerSelfAttackInc(Chara caster, String buffName, Trigger trigger, double inc) {
        registerAttackInc(caster, caster, buffName, trigger, inc, INFI);
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
                .type(SkillTime.CONTINUIOUS)
                .trigger(攻击力计算)
                .check(pack -> pack.checkCaster(acceptor))
                .cast(pack -> pack.getDamageCal().changeDamage(BuffType.攻击力百分比增加, inc))
                .build();

        Skill skill = BaseSkill.builder()
                .trigger(trigger).check(pack -> true)
                .cast(pack -> registerBuff(atkIncBuff))
                .type(SkillTime.ONCE)       //增加攻击力只持续1次
                .name(caster + "要在" + trigger + "场合使用" + atkIncBuff)
                .build();

        registerSkill(skill);
    }

    public static boolean registerSkill(Skill skill) {
        System.out.println("＋新增：" + skill);
        getInstance().addSkill(skill);
        return true;
    }

    public static boolean sendMessage(Trigger trigger, MessagePack pack) {
        return getInstance().respondMessage(trigger, pack);
    }

    private boolean addSkill(Skill skill) {
        skills.add(skill);
        return true;
    }

}
