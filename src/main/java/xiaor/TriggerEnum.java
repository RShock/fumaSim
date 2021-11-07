package xiaor;

public enum TriggerEnum {
    普通攻击,
    大招,

    ATTACK_DAMAGE_CAL, SKILL_DAMAGE_CAL, 攻击力计算, BEFORE_ATTACK,
    技能释放结束后             //大招后buff结算
    ,

    游戏开始时,内部事件;
}
