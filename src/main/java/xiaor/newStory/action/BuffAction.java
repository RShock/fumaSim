package xiaor.newStory.action;

import xiaor.*;
import xiaor.skill.Buff;
import xiaor.skill.SkillTime;
import xiaor.skill.UniqueBuff;

import java.util.Collections;
import java.util.List;

import static xiaor.TriggerEnum.*;
import static xiaor.TriggerEnum.伤害计算;
import static xiaor.newStory.action.BuffType.*;

public class BuffAction extends ActionBuilder {

    private BuffType buffType;
    private double multi;
    private Chara caster;
    private List<Chara> acceptors;
    private int turn;
    private boolean isUnique;
    private String uniqueId;
    protected int level;
    protected int maxLevel;
    private String name;

    public static BuffAction create(Chara castor, BuffType type) {
        BuffAction buffAction = new BuffAction();
        buffAction.buffType = type;
        buffAction.caster = castor;
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
        this.isUnique = true;
        this.uniqueId = "Buff: " + Tools.getNewID();
        this.level = level;
        return this;
    }
    public Action build() {
        Action action = new Action();
        if (acceptors == null) {
            throw new RuntimeException("技能%s的接收对象未定义".formatted(name));
        }
        action.setAction(pack2 -> {
            for (Chara acceptor : acceptors) {
                Buff buff;
                Buff.BuffBuilder<?, ?> tempBuff = getTempBuffBuilder(acceptor);

                switch (buffType) {
                    case 攻击力百分比增加 -> {
                        buff = tempBuff.trigger(攻击力计算)
                                .cast(pack -> {
                                    if(pack.level > 1){
                                        pack.getDamageCal().changeDamage(攻击力百分比增加, multi* pack.level);
                                    }
                                    pack.getDamageCal().changeDamage(攻击力百分比增加, multi);
                                    return true;
                                })
                                .build();
                    }
                    case 普攻伤害增加 -> {
                        buff = tempBuff.trigger(普攻伤害计算)
                                .cast(pack -> {
                                    pack.getDamageCal().changeDamage(BuffType.普攻伤害增加, multi);
                                    return true;
                                })
                                .build();
                    }
                    case 造成伤害增加 -> {
                        buff = tempBuff.trigger(伤害计算)
                                .cast(pack -> {
                                    pack.getDamageCal().changeDamage(造成伤害增加, multi);
                                    return true;
                                })
                                .build();
                    }
                    case 必杀技伤害增加 -> {
                        buff = tempBuff.trigger(技能伤害计算)
                                .cast(pack -> {
                                    pack.getDamageCal().changeDamage(必杀技伤害增加, multi);
                                    return true;
                                })
                                .build();
                    }
                    case 受到风属性伤害增加 -> {
                        buff = tempBuff.trigger(伤害计算)
                                .check(pack ->
                                        pack.checkAccepter(acceptor) && caster.getElement().equals(Element.风属性)
                                )
                                .cast(pack -> {
                                    pack.getDamageCal().changeDamage(受到风属性伤害增加, multi);
                                    return true;
                                })
                                .build();
                    }
                    default -> {
                        throw new RuntimeException("未支持的buff类型" + buffType);
                    }
                }

                TriggerManager.registerBuff(buff);
                callNext(action.getActionId());
            }
            return true;
        });
        return action;
    }


    protected Buff.BuffBuilder getTempBuffBuilder(Chara acceptor) {
        Buff.BuffBuilder<?, ?> tempBuff;
        if (isUnique) {
            tempBuff = UniqueBuff.builder()
                    .maxLevel(maxLevel)
                    .incLv(level)
                    .currentLevel(level)
                    .uniqueId(uniqueId);
        } else {
            tempBuff = Buff.builder();
        }
        tempBuff = tempBuff
                .caster(caster)
                .acceptor(acceptor)
                .name(name)
                .time(turn)
                .skillTime(SkillTime.持续的)
                .check(pack -> pack.checkCaster(acceptor)); //对于buff来说，监测对象是acceptor
        return tempBuff;
    }

    public BuffAction toAlly() {
        this.acceptors = GameBoard.getAlly();
        return this;
    }

    public BuffAction maxLevel(int maxLevel) {
        this.maxLevel = level;
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
}
