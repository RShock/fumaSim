package xiaor;

import lombok.*;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Setter
@Getter
public class MessagePack {
    public Chara caster;

    public Chara acceptor;

    public DamageCal damageCal; //用于伤害计算

    public int id;          //用于内部事件的id
    public String level;    //用于可堆叠buff计算的层数

    public boolean checkCaster(Chara caster) {
        return caster.equals(this.caster);
    }

    public static MessagePack newIdPack(int id) {
        return MessagePack.builder().id(id).build();
    }

    public static MessagePack damagePack(DamageCal damageCal) {
        return MessagePack.builder()
                .damageCal(damageCal)
                .caster(damageCal.pack.caster)
                .acceptor(damageCal.pack.acceptor)
                .build();
    }
}
