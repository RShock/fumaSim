package xiaor.skill;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import xiaor.MessagePack;
import xiaor.charas.Chara;
import xiaor.skillbuilder.action.BuffType;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Buff extends BaseSkill {
    Chara caster;
    Chara acceptor;
    BuffType buffType;

    @Override
    public boolean cast(MessagePack pack) {
        pack.level = 1; //普通buff默认有一层
        return super.cast(pack);
    }

    //uniqueBuff子类需要一个调用父类cast的方法
    public boolean _cast(MessagePack pack) {
        return super.cast(pack);
    }

    public String toString() {
        String tempS;
        if(caster == acceptor)tempS = caster + "给自己";
        else tempS =  caster + "->" + acceptor;
        if(time > 50){
            return "%s[%s] (永久)".formatted(name, tempS);
        }
        return "%s[%s] 持续%d回合".formatted(name, tempS, time);
    }
}
