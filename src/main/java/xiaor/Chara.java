package xiaor;


import java.util.function.Function;

public interface Chara {
    default void attack(Chara acceptor){
        MessagePack pack = MessagePack.builder()
                .acceptor(acceptor)
                .caster(this)
                .build();
        TriggerManager.getInstance().sendMessage(Trigger.普通攻击, pack);
    }

    default void skill(Chara acceptor){
        MessagePack pack = MessagePack.builder()
                .acceptor(acceptor)
                .caster(this)
                .build();
        TriggerManager.getInstance().sendMessage(Trigger.SKILL, pack);
    }

    default boolean self(MessagePack pack) {
        return pack.caster == this;
    }

    void defense(Chara acceptor);

    void initSkills();

    int getAttack();
    int getLife();

    void setLife(int lifeRemain);

    Function<MessagePack, Boolean> getSkill(int level);
}
