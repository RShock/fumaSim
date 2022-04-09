package xiaor.skillbuilder.trigger;

import lombok.Getter;
import lombok.Setter;
import xiaor.MessagePack;
import xiaor.trigger.TriggerEnum;

import java.util.function.Function;

@Getter
@Setter
public class Trigger {
    /**
     * 一个trigger由触发类型(trigger enum)初步触发后，经过checker(主校验，可以拿到msgPack)和附加校验
     * (全局环境校验,只能调用全局变量)，都为true后决定是否真正触发
     */
    public Function<MessagePack, Boolean> checker;
    TriggerEnum triggerType;

}
