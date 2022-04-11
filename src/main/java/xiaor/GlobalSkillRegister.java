package xiaor;

import xiaor.charas.Chara;
import xiaor.skill.BaseSkill;
import xiaor.skill.BuffType;
import xiaor.skill.Skill;
import xiaor.tools.GlobalDataManager;
import xiaor.tools.KeyEnum;
import xiaor.tools.Tools;
import xiaor.tools.record.DamageRecorder;
import xiaor.trigger.TriggerEnum;
import xiaor.trigger.TriggerManager;

import static xiaor.Common.INFI;
import static xiaor.tools.GlobalDataManager.getIntData;

public class GlobalSkillRegister {
    public static void registerSkill() {
        Skill skill = BaseSkill.builder().name("【系统规则】属性克制 优势方+50%伤害").trigger(TriggerEnum.伤害计算)
                .time(INFI)
                .check(pack -> {
                    if (pack.damageCal == null) return false;
                    return pack.damageCal.pack.caster.counter(pack.damageCal.pack.acceptors.get(0)) == 1;
                }).cast(pack -> {
                    pack.getDamageCal().changeDamage(BuffType.属性克制, 0.5);
                }).build();
        TriggerManager.registerSkill(skill);
        skill = BaseSkill.builder().name("【系统规则】属性克制 劣势方-25%伤害").trigger(TriggerEnum.伤害计算)
                .time(INFI)
                .check(pack -> {
                    if (pack.damageCal == null) return false;
                    return pack.damageCal.pack.caster.counter(pack.damageCal.pack.acceptors.get(0)) == -1;
                }).cast(pack -> {
                    pack.getDamageCal().changeDamage(BuffType.属性克制, -0.25);
                }).build();
        TriggerManager.registerSkill(skill);
        //游戏开始前其实还有第0回合，不过暂时先把游戏开始当作整个系统启动的第一事件吧
        skill = BaseSkill.builder().name("【系统规则】第一回合").trigger(TriggerEnum.游戏开始时)
                .time(INFI)
                .check(pack -> true)
                .cast(pack -> {
                    GlobalDataManager.putIntData(KeyEnum.GAMETURN, 1);
                    Tools.log(Tools.LogColor.RED, "第1回合开始");
                    TriggerManager.sendMessage(TriggerEnum.回合开始, null);
                    GameBoard.getAlly().forEach(chara -> chara.setStatus(Chara.CharaStatus.ACTIVE));
                }).build();
        TriggerManager.registerSkill(skill);
        //目前设计 回合结束后默认没敌人的回合 继续是我方回合
        skill = BaseSkill.builder().name("【系统规则】角色行动完进入下一回合").trigger(TriggerEnum.角色行动结束)
                .time(INFI)
                .check(pack -> GameBoard.getAlly().stream().noneMatch(chara -> chara.getStatus().equals(Chara.CharaStatus.ACTIVE)))
                .cast(pack -> {
                    TriggerManager.sendMessage(TriggerEnum.回合结束, null);
                }).build();
        TriggerManager.registerSkill(skill);
        //buff消退：回合结束时所有非永久buff都会消退1层
        skill = BaseSkill.builder().name("【系统规则】回合结束时所有非永久buff都会消退1层").trigger(TriggerEnum.回合结束)
                .time(INFI)
                .check(pack -> true)
                .cast(pack -> {
                    TriggerManager.getInstance().getSkills().forEach(Skill::decrease);
                    GlobalDataManager.putIntData(KeyEnum.GAMETURN, getIntData(KeyEnum.GAMETURN) + 1);
                    Tools.log(Tools.LogColor.RED, "第" + (getIntData(KeyEnum.GAMETURN)) + "回合开始");
                    TriggerManager.sendMessage(TriggerEnum.回合开始, null);
                }).build();
        TriggerManager.registerSkill(skill);
        skill = BaseSkill.builder().name("【系统规则】回合开始时所有角色恢复可用状态").trigger(TriggerEnum.回合开始)
                .time(INFI)
                .check(pack -> true)
                .cast(pack -> {
                    GameBoard.getAlly().forEach(chara -> chara.setStatus(Chara.CharaStatus.ACTIVE));
                }).build();
        TriggerManager.registerSkill(skill);
    }
}
