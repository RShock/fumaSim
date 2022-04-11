package xiaor.skill.buff;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import xiaor.MessagePack;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
/*
 * 可叠加buff 一般是无法消失的
 */
public class UniqueBuff extends Buff{

    public String uniqueId;
    public int maxLevel;
    public int currentLevel;
    public int incLv;   //添加时buff添加几层

    @Override
    public void cast(MessagePack pack) {
        pack.level = currentLevel;
        super._cast(pack);
    }

    public void add(UniqueBuff buff) {
        currentLevel = Math.min(currentLevel+buff.incLv, maxLevel);
    }

    @Override
    public String toString() {
        String tempS;
        if(caster == acceptor)tempS = caster + "给自己";
        else tempS =  caster + "->" + acceptor;
        return "%s[%s] 当前层数%d 最高层数%d".formatted(name, tempS, currentLevel, maxLevel);
    }
}
