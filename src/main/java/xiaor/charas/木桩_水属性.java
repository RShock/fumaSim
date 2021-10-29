package xiaor.charas;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import xiaor.BaseChara;
import xiaor.Chara;
import xiaor.GameBoard;
import xiaor.MessagePack;

import java.util.function.Function;


public class 木桩_水属性 extends BaseChara {

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
