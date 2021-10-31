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

    public boolean checkCaster(Chara caster) {
        return caster.equals(this.caster);
    }
}
