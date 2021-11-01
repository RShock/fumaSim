package xiaor.story;

import xiaor.Chara;
import xiaor.Trigger;

import java.util.function.Function;

public class SkillAtkBuilder extends BaseBuilder {
    Story story;

    public SkillAtkBuilder(BaseBuilder preBuilder) {
        super(preBuilder);
        this.story = new Story();
    }

    public BaseBuilder when(Trigger trigger) {
        story.trigger = trigger;
        return this;
    }

    public BaseBuilder castor(Chara castor) {
        story.castor = castor;
        return this;
    }

    public BaseBuilder acceptor(Chara acceptor) {
        story.acceptor = acceptor;
        return this;
    }

    public BaseBuilder check(Function<Package, Boolean> check) {
        story.check = check;
        return this;
    }

    public BaseBuilder cast(Function<Package, Boolean> cast) {
        story.cast = cast;
        return this;
    }

    public BaseBuilder multi(double multi) {
        story.multi = multi;
        return this;
    }

    public BaseBuilder during(int during) {
        story.during = during;
        return this;
    }

    public BaseBuilder name(String name) {
        story.name = name;
        return this;
    }
}
