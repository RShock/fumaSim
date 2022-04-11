package xiaor.skillbuilder.action;

import lombok.Getter;
import xiaor.MessagePack;
import xiaor.trigger.TriggerEnum;
import xiaor.trigger.TriggerManager;

import java.util.function.Supplier;

@Getter
public class ActionBuilder {

    public ActionBuilder() {
    }

    public static Action getEmptyAction() {
        Action action = new Action();
        action.setAction(pack -> { });
        return action;
    }

    public static Action getFreeAction(Supplier<Boolean> action) {
        Action action1 = new Action();
        action1.setAction((pack -> action.get()));
        return action1;
    }
}
