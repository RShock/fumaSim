package xiaor.skill;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import xiaor.MessagePack;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UniqueBuff extends Buff{

    public String uniqueId;
    public String maxLevel;
    public String currentLevel;


    @Override
    public boolean cast(MessagePack pack) {
        pack.level = currentLevel;
        return cast.apply(pack);
    }
}
