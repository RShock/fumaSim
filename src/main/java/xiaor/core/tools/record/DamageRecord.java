package xiaor.core.tools.record;

import lombok.Getter;
import lombok.Setter;
import xiaor.core.charas.Chara;
import xiaor.core.trigger.TriggerEnum;

@Getter
@Setter
public final class DamageRecord {
    private Integer actionId;
    private final TriggerEnum type;
    private final String detail;
    private final Chara caster;
    private final Chara accepter;
    private final int num;

    public DamageRecord(TriggerEnum type, String detail, Chara caster, Chara accepter, int num) {
        this.type = type;
        this.detail = detail;
        this.caster = caster;
        this.accepter = accepter;
        this.num = num;
    }

}
