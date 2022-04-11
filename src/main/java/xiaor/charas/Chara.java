package xiaor.charas;

import lombok.*;
import xiaor.MessagePack;
import xiaor.tools.Tools;
import xiaor.trigger.TriggerEnum;
import xiaor.trigger.TriggerManager;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
public abstract class Chara{
    protected int charaId;

    protected final int uniqueId = Tools.getNewID();

    protected String name;

    protected int attack;

    protected int life;

    protected boolean isMoved;

    protected int potential;

    protected CharaStatus status;   //角色状态

    protected Role role; //角色职业

    protected int shield;   //护盾

    public enum CharaStatus {
        DEAD,
        ACTIVE,
        INACTIVE;
    }
    @Builder.Default
    protected int star = 3;

    public Chara() {
        isLeader = false;
    }

    protected Element element;

    @Builder.Default
    protected int skillLevel = 1;

    @Builder.Default
    protected boolean isLeader = false;

    public void defend(Chara acceptor){
        MessagePack pack = MessagePack.builder()
                .acceptors(Collections.singletonList(acceptor))
                .caster(this)
                .build();
        TriggerManager.sendMessage(TriggerEnum.释放防御, pack);
        setStatus(Chara.CharaStatus.INACTIVE);
        TriggerManager.sendMessage(TriggerEnum.释放防御后, pack);

        TriggerManager.sendMessage(TriggerEnum.角色行动结束, pack);
    }

    public boolean is6() {
        return potential>=6;
    }

    public boolean is12() {
        return potential>=12;
    }
    public int counter(Chara chara) {
        return Element.counter(element, chara.getElement());
    }

    public String toString() {
        return name;
    }

    public int uniqueId() {
        return uniqueId;
    }

    public abstract void initSkills();

    public boolean is(Role role) {
        return role == this.role;
    }

    public void attack(Chara acceptor){
        MessagePack pack = MessagePack.builder()
                .acceptors(Collections.singletonList(acceptor))
                .caster(this)
                .build();
        TriggerManager.sendMessage(TriggerEnum.释放普攻, pack);
        setStatus(Chara.CharaStatus.INACTIVE);
        TriggerManager.sendMessage(TriggerEnum.释放普攻后, pack);
        TriggerManager.sendMessage(TriggerEnum.攻击后, pack);
        TriggerManager.sendMessage(TriggerEnum.角色行动结束, pack);
    }

    public void skill(Chara acceptor){
        MessagePack pack = MessagePack.builder()
                .acceptors(Collections.singletonList(acceptor))
                .caster(this)
                .build();
        TriggerManager.sendMessage(TriggerEnum.释放必杀, pack);
        setStatus(Chara.CharaStatus.INACTIVE);
        TriggerManager.sendMessage(TriggerEnum.释放必杀后, pack);
        TriggerManager.sendMessage(TriggerEnum.攻击后, pack);
        TriggerManager.sendMessage(TriggerEnum.角色行动结束, pack);
    }

    protected static void baseInit(Chara chara, String s) {
        chara.isLeader = false;
        chara.potential = 1;
        chara.star = 1;
        chara.skillLevel = 1;
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
                chara.potential = getNumFromString(s1);
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
            if(s1.startsWith("光属性")){
                chara.element = Element.光属性;
            }
        }
    }

    public static int getNumFromString(String s) {
        Pattern p = Pattern.compile("[^0-9]");
        Matcher m = p.matcher(s);
        return Integer.parseInt(m.replaceAll("").trim());
    }
}
