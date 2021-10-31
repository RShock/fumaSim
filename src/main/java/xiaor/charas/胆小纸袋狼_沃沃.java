package xiaor.charas;


import xiaor.*;

import java.util.function.Function;

import static xiaor.Common.INFI;


public class 胆小纸袋狼_沃沃 extends BaseChara {

    public 胆小纸袋狼_沃沃(String name) {
        super(name);
        initSkills();
    }

    public 胆小纸袋狼_沃沃() {
        super("沃沃");
        initSkills();
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
                        .name(this.toString() + "记住了必杀")
                        .time(INFI)
                        .cast(pack ->
                                getSkill(getSkillLevel()).apply(pack)
                        )
                        .build());

        //普攻技能
        TriggerManager.getInstance().registerNormalAttack(this, this.toString() + "记住了普攻", 1.00);

        //队长技能
        if(isLeader()) {
            TriggerManager.getInstance().registerSelfAttackInc(this, this.toString() + "_队长技能_自身攻击+20%",
                    Trigger.START_OF_GAME,0.2);
            GameBoard.getInstance().getOurChara().forEach(chara -> {
                TriggerManager.getInstance().registerAttackInc(this, chara,
                        this.toString() + "_队长技能_队员攻击+40%", Trigger.START_OF_GAME, 0.2);
            });
        }
    }

    @Override
    public Function<MessagePack, Boolean> getSkill(int level) {
        double[] multi = {0, 0.96, 1.18, 1.41, 1.63, 1.86};
        return messagePack -> {
            TriggerManager.getInstance().registerSkillAttack(this, this.toString() + "的必杀！", 2.00);
            TriggerManager.getInstance().registerSelfAttackInc(this, 
                    this.toString() + "大招_攻击加成_羁绊"+level,
                    Trigger.技能释放结束后, multi[level]);
            return true;
        };
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
