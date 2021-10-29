package xiaor;

public interface Chara {
    default void attack(GameBoard board, Chara acceptor){
        MessagePack pack = MessagePack.builder()
                .acceptor(acceptor)
                .caster(this)
                .build();
        TriggerManager.getInstance().sendMessage(Trigger.ATTACK, pack);
    }

    default void skill(Chara acceptor){
        MessagePack pack = MessagePack.builder()
                .acceptor(acceptor)
                .caster(this)
                .build();
        TriggerManager.getInstance().sendMessage(Trigger.SKILL, pack);
    }

    void defense(Chara acceptor);

    void initSkills(GameBoard board);

    int getAttack();
}
