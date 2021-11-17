package xiaor.charas;

import lombok.*;
import lombok.experimental.SuperBuilder;
import xiaor.skill.Skill;

import java.util.List;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
public abstract class BaseChara implements Chara {
    protected int charaId;

    protected String name;

    protected List<Skill> skills;

    protected int attack;

    protected int life;

    protected boolean isMoved;

    protected boolean is6;  //是否6潜

    protected CharaStatus status;   //角色状态

    protected Role role; //角色职业

    public enum CharaStatus {
        DEAD,
        ACTIVE,
        INACTIVE
    }

    @Builder.Default
    protected int star = 3;

    public BaseChara() {
        isLeader = false;
    }

    public Element getElement() {
        return element;
    }

    protected Element element;

    public int counter(Chara chara) {
        return Element.counter(element, chara.getElement());
    }

    @Builder.Default
    private int skillLevel = 1;

    @Builder.Default
    protected boolean isLeader = false;

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

    public boolean is(Role role) {
        return role == this.role;
    }

    public boolean is(Class<? extends BaseChara> chara) {
        return chara.isInstance(this);
    }
}
