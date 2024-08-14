package xiaor.core.charas;

import lombok.*;
import xiaor.core.logger.LogType;
import xiaor.core.logger.Logger;
import xiaor.core.msgpack.BuffCalPack;
import xiaor.core.msgpack.MessagePack;
import xiaor.core.Tools;
import xiaor.core.skillbuilder.skill.Skill;
import xiaor.core.trigger.TriggerEnum;
import xiaor.core.trigger.TriggerManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static xiaor.core.charas.Rare.SSR;

@Getter
@Setter
public abstract class Chara {
    protected int charaId;

    protected Rare rare;
    protected final int uniqueId = Tools.getNewID();

    protected String name;

    protected String nickName;

    protected double baseAttack;

    //当前攻击力
    protected CacheData<Long> attackCache = new CacheData<>(this::_getCurrentAttack);

    //当前最大生命值
    protected CacheData<Long> maxLifeCache = new CacheData<>(this::_getCurrentMaxLife);

    //当前最大技能CD
    protected CacheData<Short> maxCDCache = new CacheData<>(this::_getCurrentMaxCD);

    protected short CD = 0;     //当前CD
    protected short baseCD;     //最大CD
    protected List<Short> cds;  //记录了5个羁绊分别的CD

    protected long life;

    protected long baseLife;

    protected int potential;    //潜力

    protected CharaStatus status;   //角色状态

    protected Role role; //角色职业

    protected int shield;   //护盾

    protected String hint;  //备注

    private boolean disabled;   //不激活的角色会失去所有技能（包括普攻）

    public void shouldUpdateAtk() {
        attackCache.shouldUpdate();
    }

    public void shouldUpdateLife() {
        maxLifeCache.shouldUpdate();
    }

    protected void setOriginAtk(double attack) {
        this.baseAttack = attack;
    }

    public void setDisabled() {
        this.disabled = true;
    }

    protected void setOriginLife(int life) {
        this.baseLife = life;
    }

    protected List<Skill> skills = new ArrayList<>();      // chara会保存自己的skill以方便查阅，但是真正的skill依然由TriggerManager维护

    public void addSkill(Skill skill) {
        skills.add(skill);
    }

    public enum CharaStatus {
        @SuppressWarnings("unused") DEAD,
        ACTIVE,
        INACTIVE
    }

    protected int star = 3;

    public Chara() {
        isLeader = false;
        rare = SSR;//默认ssr
    }

    protected Element element;

    protected int skillLevel = 1;

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

    public boolean is(Element element) {
         return element == this.element;
    }

    public void defend(Chara acceptor) {
        Logger.INSTANCE.log(LogType.角色行动, "==============%s的防御==============%n".formatted(name));
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
        TriggerManager.sendMessage(TriggerEnum.行动后, pack);

        TriggerManager.sendMessage(TriggerEnum.角色行动结束, pack);
    }

    public void attack(Chara acceptor) {
        Logger.INSTANCE.log(LogType.角色行动, "==============%s的攻击==============%n".formatted(name));
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
        TriggerManager.sendMessage(TriggerEnum.行动后, pack);
        TriggerManager.sendMessage(TriggerEnum.角色行动结束, pack);
    }

    public void skill(Chara acceptor) {
        Logger.INSTANCE.log(LogType.角色行动, "==============%s的必杀==============%n".formatted(name));
        MessagePack pack = MessagePack.builder()
                .acceptors(Collections.singletonList(acceptor))
                .caster(this)
                .build();
        if (CD < getCurrentCD()) {
            Logger.INSTANCE.log(LogType.其他, "%s的CD本应尚未准备完毕:(%s/%s)".formatted(this, CD, maxCDCache.getData()));
        }
        CD = 0;
        if (disabled)
            TriggerManager.sendMessage(TriggerEnum.角色行动结束, pack);
        TriggerManager.sendMessage(TriggerEnum.释放行动, pack);
        TriggerManager.sendMessage(TriggerEnum.释放必杀, pack);
        setStatus(Chara.CharaStatus.INACTIVE);
        TriggerManager.sendMessage(TriggerEnum.快速必杀追击, pack);
        TriggerManager.sendMessage(TriggerEnum.释放必杀后, pack);
        TriggerManager.sendMessage(TriggerEnum.攻击后, pack);
        TriggerManager.sendMessage(TriggerEnum.行动后, pack);
        TriggerManager.sendMessage(TriggerEnum.角色行动结束, pack);
    }

    public void cdChange(int incCD) {
        //todo 检测是否有免疫变动

        CD = (short) Math.min(CD + incCD, getCurrentCD());
    }

    protected static void baseInit(Chara chara, String initString) {
        chara.potential = 1;
        chara.star = 1;
        chara.skillLevel = 1;
        String[] split = initString.split("\\s+");

        for (String s : split) {
            if (s.contains("攻击力")) chara.baseAttack = getNumFromString(s);
            if (s.contains("星")) chara.star = getNumFromString(s);
            if (s.contains("绊")) {
                chara.skillLevel = getNumFromString(s);
                chara.baseCD = chara.getCds().get(chara.skillLevel - 1);
            }
            if (s.contains("潜")) chara.potential = getNumFromString(s);
            if (s.contains("队长")) chara.isLeader = true;
            if (s.contains("生命")) chara.baseLife = chara.life = getNumLFromString(s);
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

    public static long getNumLFromString(String s) {
        Pattern p = Pattern.compile("[^0-9]");
        Matcher m = p.matcher(s);
        return Long.parseLong(m.replaceAll("").trim());
    }

    public long getCurrentAttack() {
        return attackCache.getData();
    }

    public long getCurrentMaxLife() {
        return maxLifeCache.getData();
    }

    public short getCurrentCD() {
        return maxCDCache.getData();
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
    private long _getCurrentAttack() {
        Logger.INSTANCE.log(LogType.触发BUFF, "============%s的攻击力计算============-".formatted(this));
        Logger.INSTANCE.log(LogType.触发BUFF, "%s的基础攻击力是%d".formatted(this, this.getBaseAttack()));

        BuffCalPack pack = new BuffCalPack(this, null);
        TriggerManager.sendMessage(TriggerEnum.攻击力计算, pack);
        Logger.INSTANCE.log(LogType.触发BUFF, "=================攻击力计算结束=================");
        Logger.INSTANCE.log(LogType.触发BUFF, "%s当前攻击力是%d".formatted(this, pack.getAtk()));
        return pack.getAtk();
    }

    private long _getCurrentMaxLife() {
        Logger.INSTANCE.log(LogType.触发BUFF, "============%s的生命值计算============".formatted(this));
        Logger.INSTANCE.log(LogType.触发BUFF, "%s的基础生命值是%d".formatted(this, this.getBaseLife()));

        BuffCalPack pack = new BuffCalPack(this, null);
        TriggerManager.sendMessage(TriggerEnum.生命值计算, pack);
        Logger.INSTANCE.log(LogType.触发BUFF, "=================生命值计算结束=================");
        Logger.INSTANCE.log(LogType.触发BUFF, "%s当前生命值是%d".formatted(this, pack.getLife()));
        this.life = pack.getLife(); //变更生命上限时，直接将生命回满
        return pack.getLife();
    }

    private short _getCurrentMaxCD() {
        Logger.INSTANCE.log(LogType.触发BUFF, "============%s的最大CD计算============".formatted(this));
        Logger.INSTANCE.log(LogType.触发BUFF, "%s的基础CD是%d".formatted(this, this.getBaseCD()));

        BuffCalPack pack = new BuffCalPack(this, null);
        TriggerManager.sendMessage(TriggerEnum.最大CD计算, pack);
        Logger.INSTANCE.log(LogType.触发BUFF, "=================最大CD计算结束=================");
        Logger.INSTANCE.log(LogType.触发BUFF, "%s的当前CD是%d".formatted(this, pack.getCD()));
        return pack.getCD();
    }
}
