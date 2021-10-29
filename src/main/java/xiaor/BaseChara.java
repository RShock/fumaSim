package xiaor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class BaseChara implements Chara {
    private int charaId;

    private String name;

    private List<Skill> skills;

    private int attack;

    private int life;

    private List<Buff> buffs;

    private Element element;

    private int skillLevel;

}
