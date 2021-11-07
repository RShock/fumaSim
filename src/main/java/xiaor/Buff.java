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
