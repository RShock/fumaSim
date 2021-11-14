package xiaor.newStory.trigger;

import lombok.Getter;
import lombok.Setter;
import xiaor.MessagePack;
import xiaor.TriggerEnum;

import java.util.function.Function;

@Getter
@Setter
public class Trigger {
    public Function<MessagePack, Boolean> checker;
    TriggerEnum triggerType;

}
