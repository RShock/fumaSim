package xiaor.charas;


public class 精灵王_塞露西亚 extends Chara {

    public static 精灵王_塞露西亚 init(String s) {
        精灵王_塞露西亚 精灵王 = new 精灵王_塞露西亚();
        精灵王.name = "精灵王";
        精灵王.element = Element.风属性;
        精灵王.role = Role.攻击者;
        baseInit(精灵王, s);
        return 精灵王;
    }

    @Override
    public void initSkills() {
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
