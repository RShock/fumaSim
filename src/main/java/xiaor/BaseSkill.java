package xiaor;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.function.Function;

@Builder
@AllArgsConstructor
public class BaseSkill implements Skill{
    private Trigger trigger;
    private Function<MessagePack, Boolean> check;
    private Function<MessagePack, Boolean> cast;

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
