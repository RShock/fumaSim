package xiaor.core.skillbuilder.skill.buff;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import xiaor.core.msgpack.Packable;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
/*
 * 可叠加buff 一般是无法消失的
 */
public class UniqueBuff<MsgType extends Packable> extends Buff<MsgType>{

    public String uniqueId;
    public int maxLevel;
    public int currentLevel;
    public int incLv;   //添加时buff添加几层

    public double getMulti() {
        return currentLevel * multi;
    }
    @Override
    public void cast(MsgType pack) {
        pack.setBuff(this);
        super._cast(pack);
    }

    public void add(UniqueBuff<MsgType> buff) {
        currentLevel = Math.min(currentLevel+buff.incLv, maxLevel);
        if(buff.caster != this.caster) {
            this.caster = buff.caster;  //多个人施加同一个buff,就以最后一个人为准吧！
        }
    }

    public void add(int buffLevel) {
        currentLevel = Math.min(currentLevel+buffLevel, maxLevel);
    }

    @Override
    public String toString() {
        String tempS;
        if(caster == acceptor)tempS = caster + "给自己";
        else tempS =  caster + "->" + acceptor;
        return "%s[%s] 当前层数%d 最高层数%d".formatted(name, tempS, currentLevel, maxLevel);
    }
}
