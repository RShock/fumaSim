package xiaor.core.skillbuilder.action;

import xiaor.core.charas.Chara;
import xiaor.core.charas.Element;
import xiaor.core.damageCal.DamageBase;
import xiaor.core.logger.LogType;
import xiaor.core.logger.Logger;
import xiaor.core.msgpack.MessagePack;
import xiaor.core.skillbuilder.skill.BuffType;
import xiaor.core.skillbuilder.skill.SkillStatus;
import xiaor.core.excel.ExcelCharaProvider;
import xiaor.core.msgpack.BuffCalPack;
import xiaor.core.skillbuilder.skill.buff.Buff;
import xiaor.core.skillbuilder.skill.buff.SwitchBuff;
import xiaor.core.skillbuilder.skill.buff.UniqueBuff;
import xiaor.core.trigger.TriggerManager;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import static xiaor.core.Common.*;
import static xiaor.core.charas.Role.*;
import static xiaor.core.skillbuilder.skill.BuffType.受到伤害;
import static xiaor.core.trigger.TriggerEnum.*;
import static xiaor.core.skillbuilder.skill.BuffType.*;

public class BuffAction {

    private BuffType buffType;
    private double multi;
    private Chara caster;
    private List<Chara> acceptors;
    private int turn;
    private boolean isUniqueBuff;
    protected int level;
    protected int maxLevel;
    private String name;
    private boolean isSwitchBuff = false;
    private List<Supplier<Boolean>> additionalCheckers;
    private String id;
    private int skillId;

    public static BuffAction create(Chara castor, BuffType type) {
        BuffAction buffAction = new BuffAction();
        buffAction.buffType = type;
        buffAction.caster = castor;
        buffAction.level = 1;
        return buffAction;
    }

    public BuffAction multi(double multi) {
        this.multi = multi;
        return this;
    }

    public BuffAction toSelf() {
        this.acceptors = Collections.singletonList(caster);
        return this;
    }

    public BuffAction lastedTurn(int turns) {
        this.turn = turns;
        return this;
    }

    public BuffAction name(String name) {
        this.name = name;
        return this;
    }

    public Action build() {
        Action action = new Action();
        if (acceptors == null) {
            throw new RuntimeException("技能%s的接收对象未定义,是不是没有加toSelf?".formatted(name));
        }
        action.setAction(pack2 -> {
            for (Chara acceptor : acceptors) {
                Buff<BuffCalPack> buff;
                var tempBuff = getTempBuffBuilder(acceptor);
                tempBuff = tempBuff.skillId(skillId);
                switch (buffType) {
                    case 攻击力 -> {
                        buff = tempBuff.trigger(攻击力计算)
                                .cast(pack -> pack.addBuff(攻击力,pack.buff.getMulti()))
                                .build();
                        acceptor.shouldUpdateAtk();
                    }
                    case 普攻伤害 -> buff = tempBuff.trigger(普攻伤害计算)
                            .cast(pack -> pack.addBuff(BuffType.普攻伤害,  pack.buff.getMulti()))
                            .build();
                    case 造成伤害 -> buff = tempBuff.trigger(伤害计算)
                            .cast(pack -> pack.addBuff(BuffType.造成伤害, pack.buff.getMulti()))
                            .build();
                    case 必杀技伤害 -> buff = tempBuff.trigger(技能伤害计算)
                            .cast(pack -> pack.addBuff(必杀技伤害, pack.buff.getMulti()))
                            .build();
                    case 受到风属性伤害 -> buff = tempBuff.trigger(伤害计算)
                            .check(pack -> pack.checkAccepter(acceptor) && pack.caster.getElement().equals(Element.风属性))
                            .cast(pack -> pack.addBuff(受到风属性伤害, pack.buff.getMulti()))
                            .build();
                    case 受到水属性伤害 -> buff = tempBuff.trigger(伤害计算)
                            .check(pack -> pack.checkAccepter(acceptor) && pack.caster.getElement().equals(Element.水属性))
                            .cast(pack -> pack.addBuff(受到水属性伤害, pack.buff.getMulti()))
                            .build();
                    case 受到光属性伤害 -> buff = tempBuff.trigger(伤害计算)
                            .check(pack -> pack.checkAccepter(acceptor) && pack.caster.getElement().equals(Element.光属性))
                            .cast(pack -> pack.addBuff(受到光属性伤害, pack.buff.getMulti()))
                            .build();
                    case 受到暗属性伤害 -> buff = tempBuff.trigger(伤害计算)
                            .check(pack -> pack.checkAccepter(acceptor) && pack.caster.getElement().equals(Element.暗属性))
                            .cast(pack -> pack.addBuff(受到暗属性伤害, pack.buff.getMulti()))
                            .build();
                    case 受到火属性伤害 -> buff = tempBuff.trigger(伤害计算)
                            .check(pack -> pack.checkAccepter(acceptor) && pack.caster.getElement().equals(Element.火属性))
                            .cast(pack -> pack.addBuff(受到火属性伤害, pack.buff.getMulti()))
                            .build();
                    case 受到伤害 -> buff = tempBuff.trigger(伤害计算)
                            .check(pack -> pack.checkAccepter(acceptor))
                            .cast(pack -> pack.addBuff(受到伤害, pack.buff.getMulti()))
                            .build();
                    case 攻击力数值 -> {
                        //数值增加时，倍率需要乘以自身攻击力
                        //存在右侧效应（右侧角色收到加成更多）
                        int incAtk = (int) (caster.getCurrentAttack() * multi);  //buff在施加后不会改变，所以攻击力是固定值
                        buff = tempBuff.trigger(攻击力计算).cast(pack -> pack.addBuff(攻击力数值, incAtk)).name(name + "具体数值为" + incAtk)
                                .build();
                        acceptor.shouldUpdateAtk();
                    }
                    case 受到普攻伤害 -> buff = tempBuff.trigger(普攻伤害计算)
                            .check(pack -> pack.checkAccepter(acceptor))
                            .cast(pack -> pack.addBuff(BuffType.受到普攻伤害, pack.buff.getMulti()))
                            .build();
                    case 受到必杀伤害 -> buff = tempBuff.trigger(技能伤害计算)
                            .check(pack -> pack.checkAccepter(acceptor))
                            .cast(pack -> pack.addBuff(BuffType.受到必杀伤害, pack.buff.getMulti()))
                            .build();
                    case 受到攻击者伤害 -> buff = tempBuff.trigger(伤害计算)
                            .check(pack -> pack.checkAccepter(acceptor) && pack.caster.is(攻击者))
                            .cast(pack -> pack.addBuff(受到攻击者伤害, pack.buff.getMulti()))
                            .build();
                    case 受到精灵王伤害 -> buff = tempBuff.trigger(伤害计算)
                            .check(pack -> pack.caster.getCharaId() == ExcelCharaProvider.searchIdByCharaName("精灵王 塞露西亚"))
                            .cast(pack -> pack.addBuff(受到精灵王伤害, pack.buff.getMulti()))
                            .build();
                    case 属性相克效果 -> buff = tempBuff.trigger(伤害计算)
                            .check(pack -> pack.checkAccepter(acceptor))
                            .time(INFINITY)
                            .cast(pack -> pack.addBuff(属性相克效果, multi))
                            .build();
                    case 受到连环陷阱属性伤害 -> {        // 特殊逻辑，和连环陷阱buff联动。代码写的很丑。
                        double level = TriggerManager.queryBuff(连环陷阱, caster).map(Buff::getMulti).orElse(0.0);
                        buff = tempBuff.trigger(伤害计算)
                            .check(pack -> pack.checkAccepter(acceptor) && (pack.caster.is(Element.水属性) || pack.caster.is(Element.火属性)) )
                            .cast(pack -> pack.addBuff(受到连环陷阱属性伤害, multi*level))
                            .build();
                        Logger.INSTANCE.log(LogType.其他, "实际连环陷阱层数附加："+ (int)level);
                    }
                    case 必杀技CD -> {
                        return;
                    }
                    case 生命值 -> {
                        buff = tempBuff.trigger(生命值计算)
                                .time(INFINITY)
                                .cast(pack -> pack.addBuff(生命值, multi))
                                .build();
                        acceptor.shouldUpdateLife();
                    }
                    case 受到自身伤害 -> buff = tempBuff.trigger(伤害计算)
                            .check(pack ->
                                    pack.checkAccepter(acceptor) && pack.checkCastor(caster)
                            ).cast(pack -> pack.addBuff(受到自身伤害, pack.buff.getMulti()))
                            .build();
                    case 受到治疗回复量 -> buff = tempBuff.trigger(没做)
                            .check(pack -> false)
                            .cast(pack -> {})
                            .build();
                    //dot伤害比较独特需要仔细处理
                    case 持续伤害 -> {
                        long dot =  (long) (caster.getCurrentAttack() * multi);
                        buff = tempBuff.trigger(回合结束)
                                .check(pack -> true)
                                .cast(pack -> DamageAction.create(DamageAction.DamageType.流血伤害)
                                        .to(pack1 -> pack1)
                                        .damageBase(DamageBase.攻击).dotDamage(dot)
                                        .build())
                                .build();
                    }
                    case 魔法少女之力, 连环陷阱, 妾身蛇后小白脸 -> // 睡托的特殊Buff，只有计数作用
                            buff = tempBuff.trigger(没做)
                                .check(pack -> false)
                                .cast(pack -> {})
                                .build();
                    case 触发伤害 -> buff = tempBuff.trigger(触发伤害计算)
                            .check(pack ->
                                    pack.checkAccepter(acceptor) && pack.checkCastor(caster)
                            ).cast(pack -> pack.addBuff(触发伤害, pack.buff.getMulti()))
                            .build();
                    default -> throw new RuntimeException("未支持的buff类型" + buffType);
                }

                TriggerManager.registerBuff(buff);
                acceptor.addSkill(buff);
            }
        });
        return action;
    }

    protected Buff.BuffBuilder<BuffCalPack, ?, ?> getTempBuffBuilder(Chara acceptor) {
        Buff.BuffBuilder<BuffCalPack, ?, ?> tempBuff;
        if (isUniqueBuff) {
            tempBuff = UniqueBuff.<BuffCalPack>builder()
                    .maxLevel(maxLevel)
                    .incLv(level)
                    .currentLevel(level)
                    .uniqueId(id + " " +acceptor.uniqueId());
        } else if (isSwitchBuff) {
            tempBuff = SwitchBuff.<BuffCalPack>builder().enabledChecks(additionalCheckers);
        } else {
            tempBuff = Buff.builder();
        }
        tempBuff = tempBuff
                .caster(caster)
                .acceptor(acceptor)
                .buffType(buffType)
                .multi(multi)
                .name(name)
                .time(turn)
                .skillStatus(SkillStatus.持续的)
                .check(pack -> pack.checkCaster(acceptor)); //对于buff来说，监测对象是acceptor
        return tempBuff;
    }

    public BuffAction maxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
        this.isUniqueBuff = true;
        return this;
    }

    public BuffAction to(List<Chara> chara) {
        this.acceptors = chara;
        return this;
    }

    public BuffAction enabledCheck(List<Supplier<Boolean>> additionalCheckers) {
        this.isSwitchBuff = true;
        this.additionalCheckers = additionalCheckers;
        return this;
    }

    public BuffAction id(String id) {
        this.id = id;
        return this;
    }

    public BuffAction skillId(int skillId) {
        this.skillId = skillId;
        return this;
    }
}
