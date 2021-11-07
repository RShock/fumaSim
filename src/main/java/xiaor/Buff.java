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
        String tempS;
        if(caster == acceptor)tempS = caster + "给自己";
        else tempS =  caster + "->" + acceptor;
        return name + '[' + tempS + "] 持续" + time +"回合";
    }
}
