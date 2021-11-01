package xiaor.charas;

import lombok.*;
import lombok.experimental.SuperBuilder;
import xiaor.Chara;
import xiaor.Element;
import xiaor.Skill;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class BaseChara implements Chara {
    private int charaId;

    private String name;

    private List<Skill> skills;

    private int attack;

    private int life;

    private boolean isMoved;

    private Element element;

    @Builder.Default
    private int skillLevel = 1;

    @Builder.Default
    private boolean isLeader = false;

    public BaseChara(String name, Boolean isLeader) {
        this.name = name;
        this.isLeader = isLeader;
        initSkills();
    }

    public String toString() {
        return name;
    }

    public BaseChara(String name) {
        this.name = name;
        initSkills();
    }

    public abstract void initSkills();

    public boolean setMoved() {
        this.isMoved = true;
        return true;
    }

    public boolean setUnMoved() {
        this.isMoved = false;
        return true;
    }

}
