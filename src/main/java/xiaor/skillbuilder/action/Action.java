package xiaor.skillbuilder.action;

import lombok.Getter;
import lombok.Setter;
import xiaor.msgpack.MessagePack;
import xiaor.msgpack.Packable;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Getter
@Setter
public class Action {
    private int time;
    private String name;
    private Consumer<Packable> action;

    public static Action getFreeAction(Supplier<Boolean> action) {
        Action action1 = new Action();
        action1.setAction((pack -> action.get()));
        return action1;
    }

}
