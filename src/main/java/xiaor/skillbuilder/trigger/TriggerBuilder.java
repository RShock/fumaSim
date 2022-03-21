package xiaor.skillbuilder.trigger;

import xiaor.tools.TriggerEnum;

import java.util.function.Supplier;

public class TriggerBuilder {
    public static Trigger when(TriggerEnum triggerEnum) {
        Trigger trigger = new Trigger();
        trigger.triggerType = triggerEnum;
        trigger.setChecker(pack -> true);
        return trigger;
    }

    public static Trigger when(TriggerEnum triggerEnum, Supplier<Boolean> checker) {
        Trigger trigger = new Trigger();
        trigger.triggerType = triggerEnum;
        trigger.setChecker(pack -> checker.get());
        return trigger;
    }
}
