package xiaor.story;

import xiaor.*;
import xiaor.skill.Buff;
import xiaor.skill.SkillTime;
import xiaor.skill.UniqueBuff;

import java.util.Collections;

import static xiaor.TriggerEnum.*;
import static xiaor.story.BuffType.攻击力百分比增加;
import static xiaor.story.BuffType.造成伤害增加;

public class BuffBuilder extends BaseBuilder {
    public double multi;
    protected BuffType buffType;

    //这四个参数用于可堆叠buff
    private int uniqueId;
    private int level;
    private boolean isUnique;
    private int maxLevel;

    public BuffBuilder(BaseBuilder preBuilder) {
        super(preBuilder);
    }

    public BuffBuilder() {
        super();
    }

    public BuffBuilder(Chara caster) {
        this.caster = caster;
    }

    public BuffBuilder buffType(BuffType buffType) {
        this.buffType = buffType;
        return this;
    }

    public BuffBuilder multi(double multi) {
        this.multi = multi;
        return this;
    }

    public BuffBuilder type(SkillType type) {
        this.type = type;
        return this;
    }

    public BuffBuilder name(String name) {
        this.name = name;
        return this;
    }

    public static BuffBuilder createSkill(Chara caster) {
        return new BuffBuilder().caster(caster);
    }

    protected BuffBuilder caster(Chara caster) {
        this.caster = caster;
        return this;
    }

    public BuffBuilder toSelf() {
        acceptors = Collections.singletonList(caster);
        return this;
    }

    public BuffBuilder lasted(int turn) {
        this.lasted = turn;
        return this;
    }

    public BuffBuilder toAlly() {
        this.acceptors = GameBoard.getAlly();
        return this;
    }

    @Override
    public BaseBuilder buildThis() {
        if (acceptors == null) {
            throw new RuntimeException("技能%s的接收对象未定义".formatted(name));
        }
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
                                callNext();
                                return true;
                            })
                            .build();
                }
                case 普攻伤害增加 -> {
                    buff = tempBuff.trigger(普攻伤害计算)
                            .cast(pack -> {
                                pack.getDamageCal().changeDamage(BuffType.普攻伤害增加, multi);
                                callNext();
                                return true;
                            })
                            .build();
                }
                case 造成伤害增加 -> {
                    buff = tempBuff.trigger(伤害计算)
                            .cast(pack -> {
                                pack.getDamageCal().changeDamage(造成伤害增加, multi);
                                callNext();
                                return true;
                            })
                            .build();
                }
                default -> {
                    throw new RuntimeException("未支持的buff类型" + buffType);
                }
            }
            TriggerManager.registerBuff(buff);
        }
        if (nextBuilder != null)
            return nextBuilder.buildThis();
        else {
            return this;
        }
    }

    public BuffBuilder level(int level) {
        this.isUnique = true;
        this.uniqueId = Tools.getNewID();
        this.level = level;
        return this;
    }

    public BuffBuilder maxLevel(int max) {
        this.maxLevel = max;
        return this;
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
                .time(lasted)
                .type(SkillTime.CONTINUIOUS)
                .check(pack -> pack.checkCaster(caster));
        return tempBuff;
    }
}
