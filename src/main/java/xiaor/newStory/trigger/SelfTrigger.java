package xiaor.newStory.trigger;

import xiaor.Chara;
import xiaor.TriggerEnum;
import xiaor.charas.胆小纸袋狼_沃沃;

public class SelfTrigger extends TriggerBuilder {
    public static Trigger act(Chara chara, TriggerEnum triggerEnum) {
        Trigger trigger = new Trigger();
        trigger.triggerType = triggerEnum;
        trigger.checker = (pack -> pack.checkCaster(chara));
        return trigger;
    }
}
