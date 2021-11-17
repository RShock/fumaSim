package xiaor.charas;



public class 法斯公主_露露 extends Chara {

    public static 法斯公主_露露 init(String s) {
        法斯公主_露露 露露 = new 法斯公主_露露();
        露露.name = "露露";
        露露.element = Element.风属性;
        露露.role = Role.治疗者;
        baseInit(露露, s);
        return 露露;
    }
    @Override
    public void initSkills() {
        double[] multi = {0, 0.69, 0.73, 0.76, 0.80, 0.80};
        //以攻击力200%对我方全体进行治疗，并获得”每回合以攻击力x%进行治疗（5回合）"效果 CD5->4

        //以攻击力50%对我方全体进行治疗

        //队长技 使我方全体必杀技伤害增加25%

        //普攻时，触发“以攻击力25%对我方HP最低者进行治疗" hp最低应该是百分比（吃必杀技加成）

        //攻击时，触发”以自身攻击力20%使我方全体攻击力增加(1回合）“（右侧效应）
        // 这里要做CD消退
        // 必杀时，触发使我方全体必杀技伤害增加25%（1回合）

        //防御减伤

        //免疫沉默


    }

    @Override
    public String toString() {
        return super.toString();
    }
}
