package xiaor.skillbuilder.trigger;

import xiaor.charas.Chara;
import xiaor.tools.TriggerEnum;

public class SelfTrigger extends TriggerBuilder {
    public static Trigger act(Chara chara, TriggerEnum triggerEnum) {
        Trigger trigger = new Trigger();
        trigger.triggerType = triggerEnum;
        trigger.checker = (pack -> pack.checkCaster(chara));
        return trigger;
    }
}
