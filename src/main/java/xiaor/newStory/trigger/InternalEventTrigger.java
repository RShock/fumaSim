package xiaor.newStory.trigger;

import xiaor.TriggerEnum;

/**
 * 内部事件构造工厂
 */
public class InternalEventTrigger extends TriggerBuilder{
    public static Trigger getTrigger(int eventId) {
        Trigger trigger = new Trigger();
        trigger.triggerType = TriggerEnum.内部事件;
        trigger.checker = (pack -> pack.id == eventId);
        return trigger;
    }
}
