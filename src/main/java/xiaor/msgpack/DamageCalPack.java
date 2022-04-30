package xiaor.msgpack;

import lombok.Data;
import xiaor.charas.Chara;
import xiaor.skillbuilder.skill.BuffType;

import java.util.Collections;
import java.util.HashMap;

/**
 * 攻击力计算包
 */
@Data
public class DamageCalPack extends MessagePack implements Packable {
    public final HashMap<BuffType, Double> damageBuffMap = new HashMap<>();   //需要收集的数据

    public  DamageCalPack(Chara castor, Chara acceptor) {
        this.caster = castor;
        this.acceptors = Collections.singletonList(acceptor);
    }

    public void addBuff(BuffType type, double percent) {
        if (damageBuffMap.containsKey(type)) {
            damageBuffMap.put(type, damageBuffMap.get(type) + percent);
        } else {
            damageBuffMap.put(type, percent);
        }
    }

    public HashMap<BuffType, Double> getMap() {
        return damageBuffMap;
    }
}
