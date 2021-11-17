package xiaor.charas;


import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
public class 精灵王_塞露西亚 extends Chara {

    public 精灵王_塞露西亚() {
        this("精灵王");
    }

    public 精灵王_塞露西亚(String name) {
        super();
        this.name = name;
        this.element = Element.风属性;
        this.isLeader = false;
    }

    @Override
    public void initSkills() {
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
