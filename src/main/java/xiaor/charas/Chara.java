package xiaor.charas;

import lombok.*;
import xiaor.MessagePack;
import xiaor.skill.Skill;
import xiaor.tools.TriggerEnum;
import xiaor.tools.TriggerManager;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
public abstract class Chara{
    protected int charaId;

    protected String name;

    protected List<Skill> skills;

    protected int attack;

    protected int life;

    protected boolean isMoved;

    protected boolean is6;  //是否6潜

    protected CharaStatus status;   //角色状态

    protected Role role; //角色职业

    protected int shield;   //护盾

    public void defend(Chara acceptor){
        MessagePack pack = MessagePack.builder()
                .acceptor(acceptor)
                .caster(this)
                .build();
        TriggerManager.sendMessage(TriggerEnum.释放防御, pack);
        setStatus(Chara.CharaStatus.INACTIVE);
        TriggerManager.sendMessage(TriggerEnum.角色行动结束, pack);
    }

    public enum CharaStatus {
        DEAD,
        ACTIVE,
        INACTIVE
    }

    @Builder.Default
    protected int star = 3;

    public Chara() {
        isLeader = false;
    }


    protected Element element;

    public int counter(Chara chara) {
        return Element.counter(element, chara.getElement());
    }

    @Builder.Default
    protected int skillLevel = 1;

    @Builder.Default
    protected boolean isLeader = false;

    public Chara(String name, Boolean isLeader) {
        this.name = name;
        this.isLeader = isLeader;
        initSkills();
    }

    public String toString() {
        return name;
    }

    public Chara(String name) {
        this.name = name;
        initSkills();
    }

    public abstract void initSkills();

    public boolean is(Role role) {
        return role == this.role;
    }

    public boolean is(Class<? extends Chara> chara) {
        return chara.isInstance(this);
    }

    public void attack(Chara acceptor){
        MessagePack pack = MessagePack.builder()
                .acceptor(acceptor)
                .caster(this)
                .build();
        TriggerManager.sendMessage(TriggerEnum.释放普攻, pack);
        setStatus(Chara.CharaStatus.INACTIVE);
        TriggerManager.sendMessage(TriggerEnum.角色行动结束, pack);
    }

    public void skill(Chara acceptor){
        MessagePack pack = MessagePack.builder()
                .acceptor(acceptor)
                .caster(this)
                .build();
        TriggerManager.sendMessage(TriggerEnum.释放必杀, pack);
        setStatus(Chara.CharaStatus.INACTIVE);
        TriggerManager.sendMessage(TriggerEnum.角色行动结束, pack);
    }

    public boolean self(MessagePack pack) {
        return pack.caster == this;
    }


    protected static void baseInit(Chara chara, String s) {
        chara.isLeader = false;
        chara.is6 = true;
        chara.star = 5;
        chara.skillLevel = 5;
        String[] split = s.split("\\s+");
        for (String s1 : split) {
            if(s1.startsWith("攻击力")){
                chara.attack = getNumFromString(s1);
            }
            if(s1.startsWith("星")){
                chara.star = getNumFromString(s1);
            }
            if(s1.contains("绊")){
                chara.skillLevel = getNumFromString(s1);
            }
            if(s1.startsWith("潜")){
                chara.is6 = getNumFromString(s1) >= 6;
            }
            if(s1.startsWith("队长")){
                chara.isLeader = true;
            }
            if(s1.startsWith("生命")){
                chara.life = getNumFromString(s1);
            }
            if(s1.startsWith("水属性")){
                chara.element = Element.水属性;
            }
            if(s1.startsWith("风属性")){
                chara.element = Element.风属性;
            }
        }
    }

    public static int getNumFromString(String s) {
        Pattern p = Pattern.compile("[^0-9]");
        Matcher m = p.matcher(s);
        return Integer.parseInt(m.replaceAll("").trim());
    }
}
