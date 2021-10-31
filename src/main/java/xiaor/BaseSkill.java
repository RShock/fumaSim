package xiaor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;
import java.util.function.Function;

@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class BaseSkill implements Skill{
    private Trigger trigger;
    private Function<MessagePack, Boolean> check;
    private Function<MessagePack, Boolean> cast;
    private SkillType type;
    private int time;   //持续时间
    private String name; //名字

    public BaseSkill(String name, Trigger trigger, Function<MessagePack, Boolean> check, Function<MessagePack, Boolean> cast
    ,int time) {
        this.name = name;
        this.trigger = trigger;
        this.check = check;
        this.cast = cast;
        this.time = time;
    }

    @Override
    public Trigger getTrigger() {
        return trigger;
    }

    @Override
    public boolean check(MessagePack pack) {
        return check.apply(pack);
    }

    @Override
    public boolean cast(MessagePack pack) {
        if(type == SkillType.ONCE){
            type = SkillType.DISABLED;
        }
        return cast.apply(pack);
    }

    public String toString() {
        return name +  " " + "持续" + time + "回合";
    }
}
