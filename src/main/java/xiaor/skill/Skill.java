package xiaor.skill;

import xiaor.MessagePack;
import xiaor.TriggerEnum;

public interface Skill {
    public TriggerEnum getTrigger();

    boolean check(MessagePack pack);

    boolean cast(MessagePack pack);
}
