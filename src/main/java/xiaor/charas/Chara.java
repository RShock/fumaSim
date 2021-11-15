package xiaor.charas;


import xiaor.Element;
import xiaor.MessagePack;
import xiaor.tools.TriggerEnum;
import xiaor.tools.TriggerManager;

//Todo 这个接口没屌用迟早把他做掉
public interface Chara {
    default void attack(Chara acceptor){
        MessagePack pack = MessagePack.builder()
                .acceptor(acceptor)
                .caster(this)
                .build();
        TriggerManager.sendMessage(TriggerEnum.释放普攻, pack);
        setStatus(BaseChara.CharaStatus.INACTIVE);
        TriggerManager.sendMessage(TriggerEnum.角色行动结束, pack);
    }

    void setStatus(BaseChara.CharaStatus status);
    BaseChara.CharaStatus getStatus();

    default void skill(Chara acceptor){
        MessagePack pack = MessagePack.builder()
                .acceptor(acceptor)
                .caster(this)
                .build();
        TriggerManager.sendMessage(TriggerEnum.释放大招, pack);
        setStatus(BaseChara.CharaStatus.INACTIVE);
        TriggerManager.sendMessage(TriggerEnum.角色行动结束, pack);
    }

    default void defend(Chara acceptor) {
        MessagePack pack = MessagePack.builder()
                .acceptor(acceptor)
                .caster(this)
                .build();
        TriggerManager.sendMessage(TriggerEnum.释放防御, pack);
        setStatus(BaseChara.CharaStatus.INACTIVE);
        TriggerManager.sendMessage(TriggerEnum.角色行动结束, pack);
    }

    default boolean self(MessagePack pack) {
        return pack.caster == this;
    }

    void initSkills();

    int getAttack();
    int getLife();

    void setLife(int lifeRemain);

    Element getElement();

    int counter(Chara acceptor);

    int getSkillLevel();

    boolean isLeader();
//    Function<MessagePack, Boolean> getSkill(int level);
}
