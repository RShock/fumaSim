package xiaor.msgpack;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import xiaor.charas.Chara;
import xiaor.skillbuilder.skill.BuffType;
import xiaor.skillbuilder.skill.buff.Buff;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

/**
 * 攻击力计算包
 */
@Data
public class AtkCalPack extends MessagePack implements Packable {
    public final HashMap<BuffType, Double> atkBuffMap = new HashMap<>();   //需要收集的数据

    public AtkCalPack (Chara caster) {
        this.caster = caster;
    }

    public void setBuff(BuffType type, double percent) {
        if (type != BuffType.攻击力 && type != BuffType.攻击力数值) {
            throw new RuntimeException("不支持的攻击力buff类型");
        }
        if (atkBuffMap.containsKey(type)) {
            atkBuffMap.put(type, atkBuffMap.get(type) + percent);
        } else {
            atkBuffMap.put(type, percent);
        }
    }

    public int getAtk() {
        double 攻击力百分比增加 = Optional.ofNullable(atkBuffMap.get(BuffType.攻击力)).orElse(0.0);
        double 攻击力数值增加 = Optional.ofNullable(atkBuffMap.get(BuffType.攻击力数值)).orElse(0.0);
        return (int) (caster.getBaseAttack() * (1 + 攻击力百分比增加) + 攻击力数值增加);
    }
}
