package xiaor.core.msgpack;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import xiaor.core.tools.record.DamageRecord;

/**
 * 攻击力计算包
 */
@Getter
@Setter
@AllArgsConstructor
public class DamageRecordPack extends MessagePack implements Packable {
    public DamageRecord result; //用于伤害计算完毕后的记录
}
