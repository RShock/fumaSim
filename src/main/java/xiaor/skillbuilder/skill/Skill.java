package xiaor.skillbuilder.skill;

import xiaor.msgpack.MessagePack;
import xiaor.msgpack.Packable;
import xiaor.trigger.TriggerEnum;

public interface Skill<MsgType extends Packable>{
    TriggerEnum getTrigger();

    boolean check(MsgType pack);

    void cast(MsgType pack);

    //随着时间流逝Buff消退
    void decrease();

}
