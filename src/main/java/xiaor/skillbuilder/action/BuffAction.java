package xiaor.skillbuilder.action;

import xiaor.*;
import xiaor.charas.Chara;
import xiaor.charas.Element;
import xiaor.charas.机灵古怪_赛露西亚;
import xiaor.charas.精灵王_塞露西亚;
import xiaor.skill.*;
import xiaor.tools.Tools;
import xiaor.tools.TriggerManager;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import static xiaor.Common.*;
import static xiaor.charas.Role.攻击者;
import static xiaor.tools.TriggerEnum.*;
import static xiaor.tools.TriggerEnum.伤害计算;
import static xiaor.skill.BuffType.*;

public class BuffAction extends ActionBuilder {

    private BuffType buffType;
    private double multi;
    private Chara caster;
    private List<Chara> acceptors;
    private int turn;
    private boolean isUniqueBuff;
    private String uniqueId;
    protected int level;
    protected int maxLevel;
    private String name;
    private boolean isSwitchBuff = false;
    private List<Supplier<Boolean>> additionalCheckers;

    public static BuffAction create(Chara castor, BuffType type) {
        BuffAction buffAction = new BuffAction();
        buffAction.buffType = type;
        buffAction.caster = castor;
        buffAction.level = 1;
        return buffAction;
    }

    public BuffAction multi(double[] multi) {
        this.multi = multi[caster.getSkillLevel()];
        return this;
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

    public BuffAction level(int level) {
        this.isUniqueBuff = true;
        this.uniqueId = "Buff: " + Tools.getNewID();
        this.level = level;
        return this;
    }

    public Action build() {
        Action action = new Action();
        if (acceptors == null) {
            throw new RuntimeException("技能%s的接收对象未定义,是不是没有加toSelf?".formatted(name));
        }
        action.setAction(pack2 -> {
            for (Chara acceptor : acceptors) {
                Buff buff;
                Buff.BuffBuilder<?, ?> tempBuff = getTempBuffBuilder(acceptor);

                switch (buffType) {
                    case 攻击力 -> buff = tempBuff.trigger(攻击力计算)
                            .cast(pack -> {
                                pack.getDamageCal().changeDamage(攻击力, multi * pack.level);
                                return true;
                            })
                            .build();
                    case 普攻伤害增加 -> buff = tempBuff.trigger(普攻伤害计算)
                            .cast(pack -> {
                                pack.getDamageCal().changeDamage(BuffType.普攻伤害增加, multi * pack.level);
                                return true;
                            })
                            .build();
                    case 造成伤害 -> buff = tempBuff.trigger(伤害计算)
                            .cast(pack -> {
                                pack.getDamageCal().changeDamage(BuffType.造成伤害, multi * pack.level);
                                return true;
                            })
                            .build();
                    case 必杀技伤害 -> buff = tempBuff.trigger(技能伤害计算)
                            .cast(pack -> {
                                pack.getDamageCal().changeDamage(必杀技伤害, multi * pack.level);
                                return true;
                            })
                            .build();
                    case 受到风属性伤害 -> buff = tempBuff.trigger(伤害计算)
                            .check(pack ->
                                    pack.checkAccepter(acceptor) && caster.getElement().equals(Element.风属性)
                            )
                            .cast(pack -> {
                                pack.getDamageCal().changeDamage(受到风属性伤害, multi * pack.level);
                                return true;
                            })
                            .build();
                    case 受到伤害增加 -> buff = tempBuff.trigger(伤害计算)
                            .check(pack ->
                                    pack.checkAccepter(acceptor)
                            )
                            .cast(pack -> {
                                pack.getDamageCal().changeDamage(受到伤害增加, multi * pack.level);
                                return true;
                            })
                            .build();
                    case 攻击力数值 -> {
                        //数值增加时，倍率需要乘以自身攻击力
                        //存在右侧效应（右侧角色收到加成更多）
                        DamageCal damageCal = new DamageCal(MessagePack.builder().caster(caster).build());
                        int incAtk = (int) (damageCal.getCurrentAttack() * multi);  //buff在施加后不会改变，所以攻击力是固定值
                        buff = tempBuff.trigger(攻击力计算).cast(pack -> {
                            pack.getDamageCal().changeDamage(攻击力数值, incAtk);
                            return true;
                        }).name(name + "具体数值为" + incAtk)
                                .build();
                    }
                    case 受到普攻伤害 -> buff = tempBuff.trigger(普攻伤害计算)
                            .check(pack ->
                                    pack.checkAccepter(acceptor)
                            ).cast(pack -> {
                                pack.getDamageCal().changeDamage(BuffType.受到普攻伤害, multi * pack.level);
                                return true;
                            })
                            .build();
                    case 受到攻击者伤害 -> buff = tempBuff.trigger(伤害计算)
                            .check(pack ->
                                    pack.checkAccepter(acceptor) && pack.caster.is(攻击者)
                            ).cast(pack -> {
                                pack.getDamageCal().changeDamage(受到攻击者伤害, multi * pack.level);
                                return true;
                            })
                            .build();
                    case 受到精灵王伤害 -> buff = tempBuff.trigger(伤害计算)
                            .check(pack ->
                                    pack.checkAccepter(acceptor) && (pack.caster.is(精灵王_塞露西亚.class) || pack.caster.getCharaId() == 精灵王ID)
                            ).cast(pack -> {
                                pack.getDamageCal().changeDamage(受到精灵王伤害, multi * pack.level);
                                return true;
                            })
                            .build();
                    case 受到小精灵王伤害 -> buff = tempBuff.trigger(伤害计算)
                            .check(pack ->
                                    pack.checkAccepter(acceptor) && pack.caster.getCharaId() == 幼精ID
                            ).cast(pack -> {
                                pack.getDamageCal().changeDamage(受到小精灵王伤害, multi * pack.level);
                                return true;
                            })
                            .build();
                    case 属性相克效果 -> buff = tempBuff.trigger(伤害计算)
                            .check(pack ->
                                    pack.checkAccepter(acceptor)
                            )
                            .time(INFI)
                            .cast(pack -> {
                                pack.getDamageCal().changeDamage(属性相克效果, multi);
                                return true;
                            })
                            .build();
                    default -> throw new RuntimeException("未支持的buff类型" + buffType);
                }

                TriggerManager.registerBuff(buff);
                callNext(action.getActionId());
            }
            return true;
        });
        return action;
    }


    protected Buff.BuffBuilder<?, ?> getTempBuffBuilder(Chara acceptor) {
        Buff.BuffBuilder<?, ?> tempBuff;
        if (isUniqueBuff) {
            tempBuff = UniqueBuff.builder()
                    .maxLevel(maxLevel)
                    .incLv(level)
                    .currentLevel(level)
                    .uniqueId(uniqueId);
        } else if (isSwitchBuff) {
            tempBuff = SwitchBuff.builder()
                    .enabledChecks(additionalCheckers);
        } else {
            tempBuff = Buff.builder();
        }
        tempBuff = tempBuff
                .caster(caster)
                .acceptor(acceptor)
                .buffType(buffType)
                .name(name)
                .time(turn)
                .skillStatus(SkillStatus.持续的)
                .check(pack -> pack.checkCaster(acceptor)); //对于buff来说，监测对象是acceptor
        return tempBuff;
    }

    public BuffAction toAlly() {
        this.acceptors = GameBoard.getAlly();
        return this;
    }

    public BuffAction maxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
        return this;
    }

    public BuffAction toCurrentEnemy() {
        this.acceptors = Collections.singletonList(GameBoard.getCurrentEnemy());
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
}
