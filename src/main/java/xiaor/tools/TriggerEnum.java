package xiaor.tools;

public enum TriggerEnum {
    释放普攻,
    释放必杀,

    普攻伤害计算, 技能伤害计算, 攻击力计算, 伤害计算,
    释放必杀后             //大招后buff结算
    ,释放普攻后,
    释放追击普攻后,

    游戏开始时,内部事件, 角色行动结束, 释放防御, 回合结束;
}
