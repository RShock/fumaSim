package xiaor.core.skillbuilder.skill.buff;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import xiaor.core.charas.Chara;
import xiaor.core.msgpack.Packable;
import xiaor.core.skillbuilder.skill.BaseSkill;
import xiaor.core.skillbuilder.skill.BuffType;

import java.util.function.Consumer;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Buff<MsgType extends Packable> extends BaseSkill<MsgType> {
    Chara caster;
    Chara acceptor;
    BuffType buffType;

    Consumer<MsgType> buffCast;
    double multi;
    @Override
    public void cast(MsgType pack) {
        pack.setBuff(this);
        super.cast(pack);
    }

    //uniqueBuff子类需要一个调用父类cast的方法
    public void _cast(MsgType pack) {
        super.cast(pack);
    }

    public String toString() {
        String tempS;
        if (caster == acceptor) tempS = caster + "给自己";
        else tempS = caster + "->" + acceptor;
        if (time > 50) {
            return "%s[%s] (永久)".formatted(name, tempS);
//            return "%s[%s] (永久) 实际值%s%%".formatted(name, tempS, multi*100);
        }
        return "%s[%s] 持续%d回合".formatted(name, tempS, time);
    }
}
