package xiaor.charas;

import lombok.experimental.SuperBuilder;
import xiaor.MessagePack;


public class 木桩 extends Chara {
    public static 木桩 init(String s) {
        木桩 木桩 = new 木桩();
        木桩.name = "木桩";
        baseInit(木桩, s);
        return 木桩;
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
    public void initSkills() {

    }
}
