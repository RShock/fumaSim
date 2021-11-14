package xiaor.newStory.trigger;

import xiaor.TriggerEnum;

public class TriggerBuilder {
    public static Trigger when(TriggerEnum triggerEnum) {
        Trigger trigger = new Trigger();
        trigger.triggerType = triggerEnum;
        trigger.setChecker(pack -> true);
        return trigger;
    }
}
