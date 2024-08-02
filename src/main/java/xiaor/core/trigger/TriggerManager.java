package xiaor.core.trigger;

import lombok.Getter;
import xiaor.core.charas.Chara;
import xiaor.core.logger.LogType;
import xiaor.core.logger.Logger;
import xiaor.core.msgpack.BuffCalPack;

import xiaor.core.msgpack.Packable;
import xiaor.core.skillbuilder.skill.BuffType;
import xiaor.core.skillbuilder.skill.buff.Buff;
import xiaor.core.skillbuilder.skill.Skill;
import xiaor.core.skillbuilder.skill.buff.UniqueBuff;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Getter
public class TriggerManager {

    public static Boolean BUFF_LOG = true;

    private final List<Skill> skills;

    private int IDGen = 10000;

    public int getIDGen() {
        return IDGen++;
    }

    private static TriggerManager triggerManager = new TriggerManager();

    private TriggerManager() {
        skills = new ArrayList<>();
    }

    public static TriggerManager getInstance() {
        return triggerManager;
    }

    public void reset() {
        triggerManager = new TriggerManager();
    }

    public static void registerBuff(Buff buff) {
        TriggerManager.getInstance().addBuff(buff);
    }

    public static Optional<? extends Buff<?>> queryBuff(BuffType type, Chara owner){
        return TriggerManager.getInstance().skills.stream().filter(skill -> skill instanceof Buff<?>)
                .map(skill -> (Buff<?>) skill)
                .filter(buff -> buff.getBuffType() == type)
                .filter(buff -> buff.getAcceptor() == owner)
                .findFirst();
    }

    private void addBuff(Buff<BuffCalPack> newBuff) {
        //可堆叠buff特殊处理
        if(newBuff instanceof UniqueBuff newUniqueBuff) {
            Optional<UniqueBuff<BuffCalPack>> first = skills.stream().filter(skill -> skill instanceof UniqueBuff)
                    .map(skill -> (UniqueBuff<BuffCalPack>)skill)
                    .filter(buff -> buff.uniqueId.equals(newUniqueBuff.uniqueId))
                    .findFirst();
            if(first.isPresent()){
                first.get().add(newUniqueBuff);
                Logger.INSTANCE.log(LogType.其他, "＋新增可堆叠buff: %s %s".formatted(first.get(), ((UniqueBuff<BuffCalPack>) newBuff).uniqueId) );

            }else{
                skills.add(newBuff);
                Logger.INSTANCE.log(LogType.其他, "＋创建可堆叠buff: %s %s".formatted(newBuff, ((UniqueBuff<BuffCalPack>) newBuff).uniqueId) );
            }
            return;
        }
        Logger.INSTANCE.log(LogType.其他,"＋新增buff: " + newBuff);
        skills.add(newBuff);
    }

    public static List<Skill> getSkill() {
        return getInstance().skills;
    }
    @SuppressWarnings("ForLoopReplaceableByForEach")
    public void _sendMessage(TriggerEnum trigger, Packable pack) {
        //普通for循环防止迭代器问题
        int size = skills.size();
        for (int i = 0; i < size; i++) {
            if (!skills.get(i).getTrigger().equals(trigger) || !skills.get(i).check(pack)) continue;
            String s= "触发" + trigger + skills.get(i).toString();    //用于debug
            if(!skills.get(i).toString().contains("系统规则")) {
                Logger.INSTANCE.log(LogType.触发BUFF, s);
            }
            skills.get(i).cast(pack);
        }
    }

    public static void registerSkill(Skill skill) {
        getInstance().skills.add(skill);
    }

    public static void sendMessage(TriggerEnum trigger, Packable pack) {
        getInstance()._sendMessage(trigger, pack);
    }

    public static void sendMessage(TriggerEnum trigger) {
        getInstance()._sendMessage(trigger, null);
    }
}
