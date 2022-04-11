package xiaor.skillbuilder.trigger;

import lombok.Getter;
import lombok.Setter;
import xiaor.MessagePack;
import xiaor.charas.Chara;
import xiaor.trigger.TriggerEnum;

import java.util.function.Function;
import java.util.function.Supplier;

@Getter
@Setter
public class Trigger {
    /**
     * 一个trigger由触发类型(trigger enum)初步触发后，经过checker(主校验，可以拿到msgPack)和附加校验
     * (全局环境校验,只能调用全局变量)，都为true后决定是否真正触发
     */
    public Function<MessagePack, Boolean> checker;
    TriggerEnum triggerType;

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

    public static Trigger selfAct(Chara chara, TriggerEnum triggerEnum) {
        Trigger trigger = new Trigger();
        trigger.triggerType = triggerEnum;
        trigger.checker = (pack -> pack.checkCaster(chara));
        return trigger;
    }
}
