package xiaor.skillbuilder.skill.buff;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import xiaor.msgpack.MessagePack;
import xiaor.msgpack.Packable;

import java.util.List;
import java.util.function.Supplier;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
/*
 * 开关buff 未满足/满足时均会在信息栏提示玩家
 */
public class SwitchBuff<MsgType extends Packable> extends Buff<MsgType>{
    List<Supplier<Boolean>> enabledChecks;

    @Override
    public void cast(MsgType pack) {
        pack.setBuff(this);
        if(isEnabled())
            super._cast(pack);
    }

    @Override
    public String toString() {
        String tempS;
        if(caster == acceptor)tempS = caster + "给自己";
        else tempS =  caster + "->" + acceptor;
        String enabled = isEnabled()?"启用中":"未启动";

        return "(%s)%s[%s]".formatted(enabled, name, tempS);
    }

    public boolean isEnabled() {
        return enabledChecks.stream().allMatch(Supplier::get);
    }
}
