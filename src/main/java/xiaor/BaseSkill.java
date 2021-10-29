package xiaor;

import java.util.function.Function;

public class BaseSkill implements Skill{
    private Trigger trigger;
    private Function<MessagePack, Boolean> check;
    private Function<MessagePack, Boolean> cast;

    public BaseSkill(Trigger trigger, Function<MessagePack, Boolean> check, Function<MessagePack, Boolean> cast) {
        this.trigger = trigger;
        this.check = check;
        this.cast = cast;

    }

    @Override
    public Trigger getTrigger() {
        return trigger;
    }

    @Override
    public boolean check(MessagePack pack) {
        return check.apply(pack);
    }

    @Override
    public boolean cast(MessagePack pack) {
        return cast.apply(pack);
    }
}
