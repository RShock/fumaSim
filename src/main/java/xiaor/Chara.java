package xiaor;


public interface Chara {
    default void attack(Chara acceptor){
        MessagePack pack = MessagePack.builder()
                .acceptor(acceptor)
                .caster(this)
                .build();
        TriggerManager.sendMessage(TriggerEnum.普通攻击, pack);
    }

    default void skill(Chara acceptor){
        MessagePack pack = MessagePack.builder()
                .acceptor(acceptor)
                .caster(this)
                .build();
        TriggerManager.sendMessage(TriggerEnum.大招, pack);
    }

    default boolean self(MessagePack pack) {
        return pack.caster == this;
    }

    void defense(Chara acceptor);

    void initSkills();

    int getAttack();
    int getLife();

    void setLife(int lifeRemain);

    Element getElement();

    int counter(Chara acceptor);

//    Function<MessagePack, Boolean> getSkill(int level);
}
