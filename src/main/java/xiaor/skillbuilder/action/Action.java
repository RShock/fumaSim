package xiaor.skillbuilder.action;

import lombok.Getter;
import lombok.Setter;
import xiaor.MessagePack;
import xiaor.tools.Tools;

import java.util.function.Function;

@Getter
@Setter
public class Action {
    public Action() {
        this.actionId = Tools.getNewID();
    }

    private int time;

    private int actionId;
    private Function<MessagePack, Boolean> action;

}