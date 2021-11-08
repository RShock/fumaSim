package xiaor.story;

import xiaor.*;

import java.util.Collections;
import java.util.function.Function;

import static xiaor.Common.INFI;

public class SkillBuilder extends BaseBuilder {
    public Function<Package, Boolean> check;
    public Function<Package, Boolean> cast;


    public SkillBuilder() {
        super();
    }

    public static BuffBuilder createBuffSkill(Chara caster) {
        return new BuffBuilder().caster(caster);
    }

    public SkillBuilder type(SkillType type) {
        this.type = type;
        return this;
    }

    public SkillBuilder acceptor(Chara acceptor) {
        this.acceptors = Collections.singletonList(acceptor);
        return this;
    }

//    public SkillAtkBuilder check(Function<Package, Boolean> check) {
//        this.check = check;
//        return this;
//    }
//
//    public SkillAtkBuilder cast(Function<MessagePack, Boolean> cast) {
//        this.cast = cast;
//        return this;
//    }

    public SkillBuilder name(String name) {
        this.name = name;
        return this;
    }

    public static DamageBuilder createDamageSkill(Chara caster) {
        return new DamageBuilder().caster(caster).lasted(INFI);
    }

    public static SkillBuilder createSkill(Chara caster) {
        return new SkillBuilder().caster(caster);
    }

    private SkillBuilder caster(Chara caster) {
        this.caster = caster;
        return this;
    }


    public SkillBuilder lasted(int lasted) {
        this.lasted = lasted;
        return this;
    }
}
