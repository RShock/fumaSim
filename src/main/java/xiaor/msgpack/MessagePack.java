package xiaor.msgpack;

import lombok.*;
import xiaor.DamageCal;
import xiaor.charas.Chara;
import xiaor.skillbuilder.skill.buff.Buff;
import xiaor.tools.record.DamageRecord;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Setter
@Getter
//TODO 用泛型替代
public class MessagePack implements Packable{
    public Chara caster;
    public Buff buff;
    public List<Chara> acceptors;

    public boolean checkCaster(Chara caster) {
        return caster.equals(this.caster);
    }


    public Boolean checkAccepter(Chara acceptor) {
        return this.acceptors.contains(acceptor);
    }

    public Boolean checkCastor(Chara caster) {
        return this.caster.getCharaId() == caster.getCharaId();
    }
}
