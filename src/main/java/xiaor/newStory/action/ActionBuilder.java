package xiaor.newStory.action;

import lombok.Getter;
import xiaor.MessagePack;
import xiaor.Tools;
import xiaor.TriggerEnum;
import xiaor.TriggerManager;

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
}
