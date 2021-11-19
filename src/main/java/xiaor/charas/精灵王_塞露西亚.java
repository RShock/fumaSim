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
        //大招 以攻击力x对目标造成伤害，并使得我方全体攻击力增加y（3回合）

        //普攻

        //队长技能 使我方全体普攻伤害+50%
        //不做

        //普攻时，触发使我方全体普攻伤害增加7.5%（1回合）效果

        //必杀时，触发使目标受到伤害增加10%（3回合）并防御解除效果（不做）

        //每经过3回合，触发：使我方全体攻击力增加20%（1回合）效果

        //6潜  使自身普攻伤害增加10%
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
