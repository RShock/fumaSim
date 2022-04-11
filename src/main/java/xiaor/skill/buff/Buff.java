package xiaor.skill.buff;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import xiaor.MessagePack;
import xiaor.charas.Chara;
import xiaor.skill.BaseSkill;
import xiaor.skill.BuffType;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Buff extends BaseSkill {
    Chara caster;
    Chara acceptor;
    BuffType buffType;


    @Override
    public void cast(MessagePack pack) {
        pack.level = 1; //普通buff默认有一层
        super.cast(pack);
    }

    //uniqueBuff子类需要一个调用父类cast的方法
    public void _cast(MessagePack pack) {
        super.cast(pack);
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
