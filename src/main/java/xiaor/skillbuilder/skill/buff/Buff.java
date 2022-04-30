package xiaor.skillbuilder.skill.buff;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import xiaor.msgpack.MessagePack;
import xiaor.charas.Chara;
import xiaor.msgpack.Packable;
import xiaor.skillbuilder.skill.BaseSkill;
import xiaor.skillbuilder.skill.BuffType;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Buff extends BaseSkill {
    Chara caster;
    Chara acceptor;
    BuffType buffType;

    double multi;

    public double getMulti() {
        return multi;
    }

    @Override
    public void cast(Packable pack) {
        ((MessagePack)pack).buff = this;
        super.cast(pack);
    }

    //uniqueBuff子类需要一个调用父类cast的方法
    public void _cast(Packable pack) {
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
