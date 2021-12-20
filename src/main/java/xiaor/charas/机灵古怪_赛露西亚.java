package xiaor.charas;


import xiaor.GameBoard;
import xiaor.skillbuilder.SkillBuilder;
import xiaor.skillbuilder.action.BuffAction;
import xiaor.skillbuilder.action.BuffType;
import xiaor.skillbuilder.action.DamageAction;
import xiaor.skillbuilder.trigger.SelfTrigger;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static xiaor.Common.INFI;
import static xiaor.GameBoard.getCurrentEnemy;
import static xiaor.skillbuilder.SkillType.*;
import static xiaor.skillbuilder.action.DamageAction.DamageType.*;
import static xiaor.tools.TriggerEnum.*;

public class 机灵古怪_赛露西亚 extends Chara {
    public static 机灵古怪_赛露西亚 init(String s) {
        机灵古怪_赛露西亚 小精灵王 = new 机灵古怪_赛露西亚();
        小精灵王.name = "小精灵王";
        小精灵王.element = Element.风属性;
        小精灵王.role = Role.攻击者;
        baseInit(小精灵王, s);
        return 小精灵王;
    }

    @Override
    public void initSkills() {
        double[] multi = {0, 3.3, 3.76, 4.22, 4.68, 5.14}; //宝具倍率
        //结算比队长技能早这样队长技能吃到10%加攻
        //6潜被动
        //使自身攻击+10%
        if (is6) {
            SkillBuilder.createNewSkill(this, 六潜被动)
                    .when(游戏开始时)
                    .act(BuffAction.create(this, BuffType.攻击力百分比增加)
                            .multi(0.1).toSelf().lastedTurn(INFI)
                            .name(this + "自身攻击+10%")
                            .build())
                    .build();
        }

        //2宝前
        //以自身攻击力376%对目标造成伤害,再使自身攻击力增加20%（3回合） cd4
        //5宝
        //使自身攻击力增加30%（3回合），且目标受到机灵古怪赛鲁西亚伤害增加15% 再以自身攻击力514%对目标造成伤害
        if (getSkillLevel() <= 2) {
            SkillBuilder.createNewSkill(this, 必杀技)
                    .when(SelfTrigger.act(this, 释放必杀))
                    .act(DamageAction.create(this, 必杀伤害)
                            .multi(multi).to(getCurrentEnemy()).build())
                    .and()
                    .act(
                            BuffAction.create(this, BuffType.攻击力百分比增加)
                                    .multi(0.2).toSelf().lastedTurn(3)
                                    .name(this + "攻击力+20%（来自大招）")
                                    .build())
                    .build();
        }
        //2宝后
        //使自身攻击力增加30%（3回合），再以自身攻击力422%对目标造成伤害，cd4
        else if (getSkillLevel() <= 4) {
            SkillBuilder.createNewSkill(this, 必杀技)
                    .when(SelfTrigger.act(this, 释放必杀))
                    .act(
                            BuffAction.create(this, BuffType.攻击力百分比增加)
                                    .multi(0.3).toSelf().lastedTurn(3)
                                    .name(this + "攻击力+30%（来自大招）")
                                    .build())
                    .and()
                    .act(DamageAction.create(this, 必杀伤害)
                            .multi(multi).to(getCurrentEnemy()).build())
                    .build();
        } else {
            SkillBuilder.createNewSkill(this, 必杀技)
                    .when(SelfTrigger.act(this, 释放必杀))
                    .act(
                            BuffAction.create(this, BuffType.攻击力百分比增加)
                                    .multi(0.3).toSelf().lastedTurn(3)
                                    .name(this + "攻击力+30%（来自大招）")
                                    .build())
                    .and()
                    .act(
                            BuffAction.create(this, BuffType.受到伤害增加)
                                    .multi(0.15).toCurrentEnemy().lastedTurn(3)
                                    .name(this + "受到伤害+15%(来自大招)")
                                    .build())
                    .and()
                    .act(DamageAction.create(this, 必杀伤害)
                            .multi(multi).to(getCurrentEnemy()).build())
                    .build();
        }

        //普通攻击
        SkillBuilder.createNewSkill(this, 普攻)
                .when(SelfTrigger.act(this, 释放普攻))
                .act(DamageAction.create(this, 普通伤害).build())
                .build();
        //队长技
        //使全体风属性角色最大hp增加35% 且获得队伍中有5名风属性角色时 发动 受到治疗回复量增加25% 攻击力增加100%效果
        //第一回合时，触发 以激灵古怪赛鲁西亚攻击力40%使我方全体风属性角色攻击力增加50回合效果
        //使精灵王赛鲁西亚获得必杀技最大CD减少2回合 以及 必杀后 触发目标受到伤害增加20% 3回合 效果
        if (isLeader) {
            SkillBuilder.createNewSkill(this, 队长技能)
                    .when(游戏开始时)
                    .check(() -> GameBoard.getAlly().stream().filter(chara -> chara.getElement() == Element.风属性).count() == 5)
                    .act(
                            BuffAction.create(this, BuffType.攻击力百分比增加)
                                    .multi(1).toAlly().lastedTurn(INFI)
                                    .name(this + "给全员攻击力+100%（来自队长技）")
                                    .build())
                    .build();
            SkillBuilder.createNewSkill(this, 队长技能)
                    .when(游戏开始时)
                    .act(
                            BuffAction.create(this, BuffType.攻击力数值增加)
                                    .multi(0.4).to(getWindAlly()).lastedTurn(50)
                                    .name(this + "以自身攻击力40%使我方全体风属性角色攻击力增加（来自队长技）")
                                    .build())
                    .build();
            Optional<Chara> 精灵王 = find精灵王();
            精灵王.ifPresent(chara -> SkillBuilder.createNewSkill(chara, 他人给予技能)
                    .when(SelfTrigger.act(chara, 释放必杀后))
                    .act(
                            BuffAction.create(chara, BuffType.受到伤害增加)
                                    .multi(0.2).toCurrentEnemy().lastedTurn(3)
                                    .name(this + "送的buff：" + chara + "必杀后 触发目标受到伤害增加20% 3回合")
                                    .build())
                    .build());
        }

        //被动
        //普攻时，触发 使目标受到我方攻击者伤害增加6%(最多4层) 且受到精灵王赛露西亚伤害增加6%（最多4层）效果
        SkillBuilder.createNewSkill(this, 一星被动)
                .when(SelfTrigger.act(this, 释放普攻后))
                .act(
                        BuffAction.create(this, BuffType.受到攻击者伤害增加)
                                .multi(0.06).toCurrentEnemy()
                                .level(1).maxLevel(4)
                                .name(this + "给对方受到攻击者伤害增加6%（最多4层）")
                                .build())
                .and()
                .act(
                        BuffAction.create(this, BuffType.受到精灵王伤害增加)
                                .multi(0.06).toCurrentEnemy()
                                .level(1).maxLevel(4)
                                .name(this + "给对方受到精灵王伤害增加6%（最多4层）")
                                .build())
                .build();
        //3星被动
        //普攻时，触发使目标受到普攻伤害增加7.5%（最多4层）效果
        SkillBuilder.createNewSkill(this, 三星被动)
                .when(SelfTrigger.act(this, 释放普攻后))
                .act(
                        BuffAction.create(this, BuffType.受到普攻伤害增加)
                                .multi(0.075).toCurrentEnemy()
                                .level(1).maxLevel(4)
                                .name(this + "受到普攻伤害增加7.5%（最多4层）")
                                .build())
                .build();

        //5星被动
        //必杀时，触发使我方全体风属性队员造成伤害增加15%（最多2层）效果

    }

    private Optional<Chara> find精灵王() {
        return GameBoard.getAlly().stream().filter(chara -> chara instanceof 精灵王_塞露西亚).findFirst();
    }

    private List<Chara> getWindAlly() {
        return GameBoard.getAlly().stream().filter(chara -> chara.getElement() == Element.风属性).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
