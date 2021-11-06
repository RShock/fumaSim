package xiaor.charas;

import lombok.experimental.SuperBuilder;
import xiaor.Chara;
import xiaor.MessagePack;

import java.util.function.Function;


@SuperBuilder(toBuilder = true)
public class 木桩 extends BaseChara {

    public 木桩() {
        super("木桩", false);
    }

    @Override
    public void attack(Chara acceptor) {
        super.attack(acceptor);
    }

    @Override
    public void skill(Chara acceptor) {
        super.skill(acceptor);
    }

    @Override
    public boolean self(MessagePack pack) {
        return super.self(pack);
    }

    @Override
    public void defense(Chara acceptor) {

    }

    @Override
    public void initSkills() {

    }
}
