package xiaor.skill;

import xiaor.MessagePack;
import xiaor.trigger.TriggerEnum;

public interface Skill {
    TriggerEnum getTrigger();

    boolean check(MessagePack pack);

    void cast(MessagePack pack);

    //随着时间流逝Buff消退
    void decrease();

}
