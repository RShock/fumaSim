package xiaor;

import java.util.function.Function;

public class Buff extends BaseSkill{
    public Buff(Trigger trigger, Function<MessagePack, Boolean> check, Function<MessagePack, Boolean> cast) {
        super(trigger, check, cast);
    }
}
