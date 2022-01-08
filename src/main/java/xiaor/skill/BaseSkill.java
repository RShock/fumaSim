package xiaor.skill;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import xiaor.MessagePack;
import xiaor.tools.TriggerEnum;

import java.util.function.Function;

import static xiaor.Common.INFI;

@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class BaseSkill implements Skill {
    TriggerEnum trigger;
    Function<MessagePack, Boolean> check;
    Function<MessagePack, Boolean> cast;
    SkillStatus skillStatus;
    int time;   //持续时间
    String name; //名字

    @Override
    public TriggerEnum getTrigger() {
        return trigger;
    }

    @Override
    public boolean check(MessagePack pack) {
        if(skillStatus == SkillStatus.已经失效 || skillStatus == SkillStatus.未发动){
            return false;
        }
        return check.apply(pack);
    }

    @Override
    public boolean cast(MessagePack pack) {
        if(skillStatus == SkillStatus.已经失效) {
            return false;
        }
        if(skillStatus == SkillStatus.仅生效一次){
            skillStatus = SkillStatus.已经失效;
        }
        return cast.apply(pack);
    }

    @Override
    public void decrease() {
        if(time == INFI) {
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
