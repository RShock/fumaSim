package xiaor.msgpack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import xiaor.charas.Chara;
import xiaor.skillbuilder.skill.BuffType;
import xiaor.tools.record.DamageRecord;

import java.util.Collections;
import java.util.HashMap;

/**
 * 攻击力计算包
 */
@Getter
@Setter
@AllArgsConstructor
public class DamageRecordPack extends MessagePack implements Packable {
    public DamageRecord result; //用于伤害计算完毕后的记录
}
