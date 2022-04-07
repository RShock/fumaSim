package xiaor.skillbuilder.action;

import lombok.Getter;
import xiaor.MessagePack;
import xiaor.tools.TriggerEnum;
import xiaor.tools.TriggerManager;

import java.security.Provider;
import java.util.function.Function;
import java.util.function.Supplier;

@Getter
public class ActionBuilder {

    public ActionBuilder() {
    }

    public static void callNext(int actionId) {
        TriggerManager.sendMessage(TriggerEnum.内部事件, MessagePack.newIdPack(actionId));
    }

    public static Action getEmptyAction() {
        Action action = new Action();
        action.setAction(pack -> {
            callNext(action.getActionId());
            return true;
        });
        return action;
    }

    public static Action getFreeAction(Supplier<Boolean> action) {
        Action action1 = new Action();
        action1.setAction((pack -> action.get()));
        return action1;
    }
}
