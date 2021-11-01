package xiaor.story;

import xiaor.Chara;
import xiaor.Trigger;

import java.util.function.Function;

public class Story {
    public Trigger trigger;
    public Function<Package, Boolean> check;
    public Function<Package, Boolean> cast;
    public int during;
    public String name;
    public Chara castor;
    public Chara acceptor;
    public double multi;

    public static BaseBuilder newSKill() {
        return new BaseBuilder();
    }
}
