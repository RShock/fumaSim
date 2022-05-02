package xiaor.msgpack;

import lombok.Data;
import lombok.Getter;
import xiaor.charas.Chara;
import xiaor.skillbuilder.skill.BuffType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

/**
 * 攻击力计算包
 */
@Getter
public class BuffCalPack extends MessagePack implements Packable {
    public final HashMap<BuffType, Double> buffMap = new HashMap<>();   //需要收集的数据

    public BuffCalPack(Chara castor, Chara acceptor) {
        this.caster = castor;
        this.acceptors = Collections.singletonList(acceptor);
    }

    public void addBuff(BuffType type, double percent) {
        if (buffMap.containsKey(type)) {
            buffMap.put(type, buffMap.get(type) + percent);
        } else {
            buffMap.put(type, percent);
        }
    }

    public int getAtk() {
        double 攻击力百分比增加 = Optional.ofNullable(buffMap.get(BuffType.攻击力)).orElse(0.0);
        double 攻击力数值增加 = Optional.ofNullable(buffMap.get(BuffType.攻击力数值)).orElse(0.0);
        return (int) (caster.getBaseAttack() * (1 + 攻击力百分比增加) + 攻击力数值增加);
    }

    public int getLife() {
        double 攻击力百分比增加 = Optional.ofNullable(buffMap.get(BuffType.生命值)).orElse(0.0);
        return (int) (caster.getBaseLife() * (1 + 攻击力百分比增加));
    }
}
