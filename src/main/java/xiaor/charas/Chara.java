package xiaor.charas;

import lombok.*;
import xiaor.msgpack.BuffCalPack;
import xiaor.msgpack.MessagePack;
import xiaor.tools.Tools;
import xiaor.trigger.TriggerEnum;
import xiaor.trigger.TriggerManager;

import java.util.Collections;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static xiaor.charas.Rare.SSR;

@Getter
@Setter
public abstract class Chara {
    protected int charaId;

    protected Rare rare;
    protected final int uniqueId = Tools.getNewID();

    protected String name;

    protected String nickName;

    protected double baseAttack;

    protected CacheData<Long> attackCache = new CacheData<>(this::_getCurrentAttack);

    protected CacheData<Long> maxLifeCache = new CacheData<>(this::_getCurrentMaxLife);

    protected long life;

    protected long baseLife;

    protected int potential;

    protected CharaStatus status;   //角色状态

    protected Role role; //角色职业

    protected int shield;   //护盾

    protected String hint;  //备注
    private boolean disabled;   //不激活的角色会失去所有技能（包括普攻）

    public void shouldUpdateAtk() {
        attackCache.shouldUpdate();
    }

    public void shouldUpdateLife() {

    }

    protected void setOriginAtk(double attack) {
        this.baseAttack = attack;
    }

    public void setDisabled() {
        this.disabled = true;
    }

    public enum CharaStatus {
        @SuppressWarnings("unused") DEAD,
        ACTIVE,
        INACTIVE
    }

    @Builder.Default
    protected int star = 3;

    public Chara() {
        isLeader = false;
        rare = SSR;//默认ssr
    }

    protected Element element;

    @Builder.Default
    protected int skillLevel = 1;

    @Builder.Default
    protected boolean isLeader;


    public boolean is6() {
        return potential >= 6;
    }

    public boolean is12() {
        return potential >= 12;
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

    public void defend(Chara acceptor) {
        System.out.printf("==============%s的防御==============%n", name);
        MessagePack pack = MessagePack.builder()
                .acceptors(Collections.singletonList(acceptor))
                .caster(this)
                .build();
        if (disabled)
            TriggerManager.sendMessage(TriggerEnum.角色行动结束, pack);
        TriggerManager.sendMessage(TriggerEnum.释放行动, pack);
        TriggerManager.sendMessage(TriggerEnum.释放防御, pack);
        setStatus(Chara.CharaStatus.INACTIVE);
        TriggerManager.sendMessage(TriggerEnum.释放防御后, pack);

        TriggerManager.sendMessage(TriggerEnum.角色行动结束, pack);
    }

    public void attack(Chara acceptor) {
        System.out.printf("==============%s的攻击==============%n", name);
        MessagePack pack = MessagePack.builder()
                .acceptors(Collections.singletonList(acceptor))
                .caster(this)
                .build();
        if (disabled)
            TriggerManager.sendMessage(TriggerEnum.角色行动结束, pack);
        TriggerManager.sendMessage(TriggerEnum.释放行动, pack);
        TriggerManager.sendMessage(TriggerEnum.释放普攻, pack);
        setStatus(Chara.CharaStatus.INACTIVE);
        TriggerManager.sendMessage(TriggerEnum.快速普攻追击, pack);
        TriggerManager.sendMessage(TriggerEnum.释放普攻后, pack);
        TriggerManager.sendMessage(TriggerEnum.攻击后, pack);
        TriggerManager.sendMessage(TriggerEnum.角色行动结束, pack);
    }

    public void skill(Chara acceptor) {
        System.out.printf("==============%s的必杀==============%n", name);
        MessagePack pack = MessagePack.builder()
                .acceptors(Collections.singletonList(acceptor))
                .caster(this)
                .build();
        if (disabled)
            TriggerManager.sendMessage(TriggerEnum.角色行动结束, pack);
        TriggerManager.sendMessage(TriggerEnum.释放行动, pack);
        TriggerManager.sendMessage(TriggerEnum.释放必杀, pack);
        setStatus(Chara.CharaStatus.INACTIVE);
        TriggerManager.sendMessage(TriggerEnum.释放必杀后, pack);
        TriggerManager.sendMessage(TriggerEnum.攻击后, pack);
        TriggerManager.sendMessage(TriggerEnum.角色行动结束, pack);
    }

    protected static void baseInit(Chara chara, String initString) {
        chara.potential = 1;
        chara.star = 1;
        chara.skillLevel = 1;
        String[] split = initString.split("\\s+");

        for (String s : split) {
            if (s.contains("攻击力")) chara.baseAttack = getNumFromString(s);
            if (s.contains("星")) chara.star = getNumFromString(s);
            if (s.contains("绊")) chara.skillLevel = getNumFromString(s);
            if (s.contains("潜")) chara.potential = getNumFromString(s);
            if (s.contains("队长")) chara.isLeader = true;
            if (s.contains("生命")) chara.baseLife = chara.life = getNumFromString(s);
            if (s.contains("水属性") || s.contains("风属性") || s.contains("光属性") ||
                    s.contains("火属性") || s.contains("暗属性")) chara.element = Enum.valueOf(Element.class, s);
            if (s.contains("闇属性")) chara.element = Element.暗属性;
            if (s.startsWith("备注:")) chara.hint = s.substring(3);
        }
    }

    public static int getNumFromString(String s) {
        Pattern p = Pattern.compile("[^0-9]");
        Matcher m = p.matcher(s);
        return Integer.parseInt(m.replaceAll("").trim());
    }

    public long getCurrentAttack() {
        return attackCache.getData();
    }

    public long getCurrentMaxLife() {
        return maxLifeCache.getData();
    }

    public int getBaseAttack() {
        return (int) baseAttack;
    }

    public static class CacheData<T> {

        private T data;

        private boolean shouldUpdate = true;

        public Supplier<T> updater;

        public CacheData(Supplier<T> updater) {
            this.updater = updater;
        }

        public void shouldUpdate() {
            this.shouldUpdate = true;
        }

        public T getData() {
            if (shouldUpdate) {
                shouldUpdate = false;
                data = updater.get();
            }
            return data;
        }
    }

    //计算基本攻击力
    public long _getCurrentAttack() {
        Tools.log("----------------%s的攻击力计算-----------------".formatted(this));
        Tools.log("%s的基础攻击力是%d".formatted(this, this.getBaseAttack()));

        BuffCalPack pack = new BuffCalPack(this, null);
        TriggerManager.sendMessage(TriggerEnum.攻击力计算, pack);
        Tools.log("----------------------攻击力计算结束-----------------------");
        Tools.log("%s当前攻击力是%d".formatted(this, pack.getAtk()));
        return pack.getAtk();
    }

    public long _getCurrentMaxLife() {
        Tools.log("----------------%s的生命值计算-----------------".formatted(this));
        Tools.log("%s的基础生命值是%d".formatted(this, this.getBaseLife()));

        BuffCalPack pack = new BuffCalPack(this, null);
        TriggerManager.sendMessage(TriggerEnum.生命值计算, pack);
        Tools.log("----------------------生命值计算结束-----------------------");
        Tools.log("%s当前生命值是%d".formatted(this, pack.getLife()));
        this.life = pack.getLife(); //变更生命上限时，直接将生命回满
        return pack.getLife();
    }
}
