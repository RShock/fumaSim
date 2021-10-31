package xiaor;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public abstract class BaseChara implements Chara {
    private int charaId;

    private String name;

    private List<Skill> skills;

    private int attack;

    private int life;

    private List<Buff> buffs;

    private Element element;

    private int skillLevel = 1;

    private boolean isLeader = false;

    public BaseChara(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

}
