package xiaor.core;

import xiaor.core.charas.Chara;
import xiaor.core.logger.LogType;
import xiaor.core.logger.Logger;
import xiaor.core.msgpack.BuffCalPack;
import xiaor.core.skillbuilder.skill.BaseSkill;
import xiaor.core.skillbuilder.skill.BuffType;
import xiaor.core.skillbuilder.skill.Skill;
import xiaor.core.tools.GlobalDataManager;
import xiaor.core.tools.KeyEnum;
import xiaor.core.trigger.TriggerEnum;
import xiaor.core.trigger.TriggerManager;

import static xiaor.core.Common.INFINITY;
import static xiaor.core.tools.GlobalDataManager.incIntData;

public class GlobalSkillRegister {
    public static void registerSkill() {
        Skill skill = BaseSkill.builder().name("属性克制 优势方+50%伤害").trigger(TriggerEnum.伤害计算)
                .time(INFINITY)
                .check(pack -> {
                    if (pack instanceof BuffCalPack pack1) {
                        return pack1.caster.counter(pack1.acceptors.get(0)) == 1;
                    }
                    return false;
                }).cast(pack -> ((BuffCalPack)pack).addBuff(BuffType.属性克制, 0.5)).build();
        TriggerManager.registerSkill(skill);
        skill = BaseSkill.builder().name("属性克制 劣势方-25%伤害").trigger(TriggerEnum.伤害计算)
                .time(INFINITY)
                .check(pack -> {
                    if (pack instanceof BuffCalPack pack1) {
                        return pack1.caster.counter(pack1.acceptors.get(0)) == -1;
                    }
                    return false;
                }).cast(pack -> ((BuffCalPack)pack).addBuff(BuffType.属性克制, -0.25)).build();
        TriggerManager.registerSkill(skill);
        //游戏开始前其实还有第0回合，不过暂时先把游戏开始当作整个系统启动的第一事件吧
        skill = BaseSkill.builder().name("【系统规则】第一回合").trigger(TriggerEnum.游戏开始时)
                .time(INFINITY)
                .check(pack -> true)
                .cast(pack -> {
                    GlobalDataManager.putIntData(KeyEnum.GAME_TURN, 1);
                    Logger.INSTANCE.log(LogType.回合开始, "第1回合开始");
                    GameBoard.getAlly().forEach(chara -> chara.setStatus(Chara.CharaStatus.ACTIVE));
                }).build();
        TriggerManager.registerSkill(skill);
        //目前设计 回合结束后默认没敌人的回合 继续是我方回合
        skill = BaseSkill.builder().name("【系统规则】角色行动完进入下一回合").trigger(TriggerEnum.角色行动结束)
                .time(INFINITY)
                .check(pack -> GameBoard.getAlly().stream().noneMatch(chara -> chara.getStatus().equals(Chara.CharaStatus.ACTIVE)))
                .cast(pack -> TriggerManager.sendMessage(TriggerEnum.回合结束, null)).build();
        TriggerManager.registerSkill(skill);
        //buff消退：回合结束时所有非永久buff都会消退1层
        skill = BaseSkill.builder().name("【系统规则】回合结束时所有非永久buff都会消退1层").trigger(TriggerEnum.回合结束)
                .time(INFINITY)
                .check(pack -> true)
                .cast(pack -> {
                    TriggerManager.getInstance().getSkills().forEach(Skill::decrease);
                    Logger.INSTANCE.log(LogType.回合开始, "第" + (incIntData(KeyEnum.GAME_TURN)) + "回合开始");
                    TriggerManager.sendMessage(TriggerEnum.回合开始时, null);
                }).build();
        TriggerManager.registerSkill(skill);
        //回合开始时所有角色CD推进
        skill = BaseSkill.builder().name("【系统规则】回合开始时所有角色CD推进").trigger(TriggerEnum.回合开始时)
                .time(INFINITY)
                .check(pack -> true)
                .cast(pack -> {
                    GameBoard.getAlly().forEach(chara -> chara.cdChange(+1));
                }).build();
        TriggerManager.registerSkill(skill);
        skill = BaseSkill.builder().name("【系统规则】回合开始时所有角色恢复可用状态").trigger(TriggerEnum.回合开始时)
                .time(INFINITY)
                .check(pack -> true)
                .cast(pack -> GameBoard.getAlly().forEach(chara -> {
                    chara.setStatus(Chara.CharaStatus.ACTIVE);
                    chara.shouldUpdateAtk();
                })).build();
        TriggerManager.registerSkill(skill);
    }
}
