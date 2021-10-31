package xiaor;

public enum Trigger {
    普通攻击,
    SKILL,

    ATTACK_DAMAGE_CAL, SKILL_DAMAGE_CAL, 攻击力计算, BEFORE_ATTACK,
    技能释放结束后             //大招后buff结算
    ,

    游戏开始时;
}
