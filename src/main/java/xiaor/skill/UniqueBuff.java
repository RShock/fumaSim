package xiaor.skill;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import xiaor.MessagePack;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UniqueBuff extends Buff{

    public int uniqueId;
    public int maxLevel;
    public int currentLevel;
    public int incLv;   //添加时buff添加几层

    @Override
    public boolean cast(MessagePack pack) {
        pack.level = currentLevel;
        return cast.apply(pack);
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
