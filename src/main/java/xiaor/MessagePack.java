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

    public DamageCal damageCal;

    public int id;

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
