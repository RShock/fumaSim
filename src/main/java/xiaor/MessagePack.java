package xiaor;

import lombok.*;
import xiaor.charas.Chara;
import xiaor.tools.record.DamageRecorder;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Setter
@Getter
public class MessagePack {
    public Chara caster;

    public List<Chara> acceptors;

    public DamageCal damageCal; //用于伤害计算
    public DamageRecorder.DamageRecord result; //用于伤害计算完毕后的记录

    public int id;          //用于内部事件的id
    public int level;    //用于可堆叠buff计算的层数

    public boolean checkCaster(Chara caster) {
        return caster.equals(this.caster);
    }

    public static MessagePack newIdPack(int id) {
        return MessagePack.builder().id(id).build();
    }

    public static MessagePack damagePack(DamageCal damageCal, Chara acceptor) {
        return MessagePack.builder()
                .damageCal(damageCal)
                .caster(damageCal.pack.caster)
                .acceptors(Collections.singletonList(acceptor))
                .build();
    }

    public Boolean checkAccepter(Chara acceptor) {
        return this.acceptors.contains(acceptor);
    }
}
