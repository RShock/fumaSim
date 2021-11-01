package xiaor.charas;


import lombok.experimental.SuperBuilder;
import xiaor.*;

import java.util.function.Function;

import static xiaor.Common.INFI;

@SuperBuilder(toBuilder = true)
public class 胆小纸袋狼_沃沃 extends BaseChara {

    public 胆小纸袋狼_沃沃() {
        super("沃沃", false);
    }

    public 胆小纸袋狼_沃沃(String name) {
        super(name);
    }

    public 胆小纸袋狼_沃沃(String name, boolean isLeader) {
        super(name, isLeader);
    }

    @Override
    public void defense(Chara acceptor) {

    }

    @Override
    public void initSkills() {
        //必杀
        TriggerManager.getInstance().registerSkill(
                BaseSkill.builder()
                        .trigger(Trigger.SKILL)
                        .check(this::self)
                        .name(this + "记住了必杀")
                        .time(INFI)
                        .cast(pack ->
                                getSkill(getSkillLevel()).apply(pack)
                        )
                        .build());

        //普攻技能
        TriggerManager.getInstance().registerNormalAttack(this, this + "记住了普攻", 1.00);

        //队长技能
        if(isLeader()) {
            TriggerManager.getInstance().registerSelfAttackInc(this, this + "_队长技能_自身攻击+20%",
                    Trigger.游戏开始时,0.2);
            GameBoard.getInstance().getOurChara().forEach(chara -> {
                TriggerManager.getInstance().registerAttackInc(this, chara,
                        this + "_队长技能_队员攻击+40%", Trigger.游戏开始时, 0.4);
            });
        }

        //3星技能 3连爪击

    }

    @Override
    public Function<MessagePack, Boolean> getSkill(int level) {
        double[] multi = {0, 0.96, 1.18, 1.41, 1.63, 1.86};
        return messagePack -> {
            TriggerManager.getInstance().registerSkillAttack(this, this + "的必杀！", 2.00,
                    pack -> {
                        TriggerManager.getInstance().addAtkIncImi(this, this,
                                this.toString() + "大招_攻击加成_羁绊"+level, multi[level], 6,
                                pack2 ->this.setMoved());
                        return true;
                    });

            return true;
        };
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
