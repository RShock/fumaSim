package xiaor.skillbuilder.skill;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import xiaor.msgpack.MessagePack;
import xiaor.msgpack.Packable;
import xiaor.trigger.TriggerEnum;

import java.util.function.Consumer;
import java.util.function.Function;

import static xiaor.Common.INFINITY;

@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class BaseSkill implements Skill {
    TriggerEnum trigger;
    Function<Packable, Boolean> check;
    Consumer<Packable> cast;
    SkillStatus skillStatus;
    protected int time;   //持续时间
    protected String name; //名字

    @Override
    public TriggerEnum getTrigger() {
        return trigger;
    }

    @Override
    public boolean check(Packable pack) {
        if(skillStatus == SkillStatus.已经失效 || skillStatus == SkillStatus.未发动){
            return false;
        }
        return check.apply(pack);
    }

    @Override
    public void cast(Packable pack) {
        if(skillStatus == SkillStatus.已经失效) {
            return;
        }
        if(skillStatus == SkillStatus.仅生效一次){
            skillStatus = SkillStatus.已经失效;
        }
        cast.accept(pack);
    }

    @Override
    public void decrease() {    //限时buff随时间的衰减
        if(time == INFINITY) {
            return;
        }
        else time--;
        if(time == 0) {
            skillStatus = SkillStatus.已经失效;
        }
    }

    public String toString() {
        if(time > 50)
            return "%s (永久)".formatted(name);
        return "%s 持续%d回合".formatted(name, time);
    }
}
