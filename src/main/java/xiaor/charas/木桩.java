package xiaor.charas;

import lombok.experimental.SuperBuilder;
import xiaor.Chara;
import xiaor.MessagePack;

import java.util.function.Function;


@SuperBuilder(toBuilder = true)
public class 木桩 extends BaseChara {

    @Override
    public void defense(Chara acceptor) {

    }

    @Override
    public void initSkills() {

    }

    @Override
    public Function<MessagePack, Boolean> getSkill(int level) {
        return null;
    }
}
