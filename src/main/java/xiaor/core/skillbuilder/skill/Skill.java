package xiaor.core.skillbuilder.skill;

import xiaor.core.msgpack.Packable;
import xiaor.core.trigger.TriggerEnum;

public interface Skill<MsgType extends Packable>{
    TriggerEnum getTrigger();

    boolean check(MsgType pack);

    void cast(MsgType pack);

    //随着时间流逝Buff消退
    void decrease();

}
