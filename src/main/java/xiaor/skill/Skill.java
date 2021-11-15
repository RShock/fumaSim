package xiaor.skill;

import xiaor.MessagePack;
import xiaor.tools.TriggerEnum;

public interface Skill {
    public TriggerEnum getTrigger();

    boolean check(MessagePack pack);

    boolean cast(MessagePack pack);

    //随着时间流逝Buff消退
    void decrease();

}
