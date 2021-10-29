package xiaor;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.function.Function;

@Builder
@AllArgsConstructor
public class BaseSkill implements Skill{
    private Trigger trigger;
    private Function<MessagePack, Boolean> check;
    private Function<MessagePack, Boolean> cast;
    private SkillType type;
    private int time;   //持续时间

    public BaseSkill(Trigger trigger, Function<MessagePack, Boolean> check, Function<MessagePack, Boolean> cast) {
        this.trigger = trigger;
        this.check = check;
        this.cast = cast;
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
}
