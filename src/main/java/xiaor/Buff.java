package xiaor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.function.Function;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Buff extends BaseSkill{
    String buffName;
    Chara caster;
    Chara acceptor;

//    public Buff(Trigger trigger, Function<MessagePack, Boolean> check, Function<MessagePack, Boolean> cast, SkillType type, int time, String buffName, Chara caster, Chara acceptor) {
//        super(trigger, check, cast, type, time);
//        this.buffName = buffName;
//        this.caster = caster;
//        this.acceptor = acceptor;
//    }

//    public Buff(Trigger trigger, Function<MessagePack, Boolean> check, Function<MessagePack, Boolean> cast) {
//        super(trigger, check, cast);
//    }

    public String toString() {
        return buffName + '[' + caster + "->" + acceptor + ']';
    }
}
