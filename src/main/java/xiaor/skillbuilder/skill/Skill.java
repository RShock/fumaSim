package xiaor.skillbuilder.skill;

import xiaor.msgpack.MessagePack;
import xiaor.msgpack.Packable;
import xiaor.trigger.TriggerEnum;

public interface Skill {
    TriggerEnum getTrigger();

    boolean check(Packable pack);

    void cast(Packable pack);

    //随着时间流逝Buff消退
    void decrease();

}
