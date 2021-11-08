package xiaor.charas;


import lombok.experimental.SuperBuilder;
import xiaor.Chara;
import xiaor.Element;

@SuperBuilder(toBuilder = true)
public class 复生公主_千鹤 extends BaseChara {

    public 复生公主_千鹤() {
        this("千鹤");
    }

    public 复生公主_千鹤(String name) {
        super();
        this.name = name;
        this.element = Element.风属性;
        this.isLeader = false;
    }

    @Override
    public void defense(Chara acceptor) {

    }

    @Override
    public void initSkills() {
        double[] multi = {0, 0.69, 0.73, 0.76, 0.80, 0.80};
        //使目标受到的风属性伤害增加12（2层）  再以攻击力330%对目标造成伤害 CD4

        //普通攻击

        //队长技 使自身攻击力+90% 必杀技伤害增加+30%

        //使自身受到伤害减少15% 但是受到火属性伤害增加30%（不做）

        //防御时，触发：使自身当前必杀技CD减少1回合效果（不做）

        //5星被动 必杀时，触发使我方全体风属性角色必杀技伤害增加15%（最多2层）效果

        //6潜 使得自身必杀技伤害增加10%

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
