package xiaor.charas;


import lombok.experimental.SuperBuilder;
import xiaor.Chara;
import xiaor.Element;
import xiaor.GameBoard;
import xiaor.story.BuffType;
import xiaor.story.SkillBuilder;

import static xiaor.Common.INFI;

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
        SkillBuilder.createBuffSkill(this)
                .to(GameBoard.getCurrentEnemy())
                .multi(0.12)
                .name(this + "必杀附带的易伤")
                .level(1)
                .maxLevel(2)
                .lasted(INFI)
                .buffType(BuffType.受到风属性伤害增加)
                .then()
                .damageMulti(3.3)
                .name(this + "的必杀伤害")
                .to(GameBoard.getCurrentEnemy())
                .build();
        //普通攻击

        //队长技 使自身攻击力+90% 必杀技伤害增加+30%

        //使自身受到伤害减少15% 但是受到火属性伤害增加30%（不做）

        //防御时，触发：使自身当前必杀技CD减少1回合效果（不做）

        //5星被动 必杀时，触发使我方全体风属性角色必杀技伤害增加15%（最多2层）效果

        //6潜 使得自身必杀技伤害增加10%


    }

    @Override
    public String toString() {
        return super.toString();
    }
}
