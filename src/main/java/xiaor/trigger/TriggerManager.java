package xiaor.trigger;

import lombok.Getter;
import xiaor.msgpack.BuffCalPack;
import xiaor.msgpack.MessagePack;
import xiaor.msgpack.Packable;
import xiaor.skillbuilder.skill.buff.Buff;
import xiaor.skillbuilder.skill.Skill;
import xiaor.skillbuilder.skill.buff.UniqueBuff;
import xiaor.tools.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class TriggerManager {
    public static Boolean SKILL_LOG = false;

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

    private void addBuff(Buff<BuffCalPack> newBuff) {
        //可堆叠buff特殊处理
        if(newBuff instanceof UniqueBuff newUniqueBuff) {
            Tools.log("＋新增可堆叠buff: " + newBuff);
            Optional<UniqueBuff<BuffCalPack>> first = skills.stream().filter(skill -> skill instanceof UniqueBuff)
                    .map(skill -> (UniqueBuff<BuffCalPack>)skill)
                    .filter(buff -> buff.uniqueId.equals(newUniqueBuff.uniqueId))
                    .findFirst();
            if(first.isPresent()){
                first.get().add(newUniqueBuff);
                Tools.log("堆叠成功，当前层数" + first.get().currentLevel);
            }else{
                skills.add(newBuff);
            }
            return;
        }
        Tools.log("＋新增buff: " + newBuff);
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
            if(!skills.get(i).toString().contains("系统规则")) {
                Tools.log("触发: " + trigger + skills.get(i).toString());
            }
            skills.get(i).cast(pack);
        }
    }

    public static void registerSkill(Skill skill) {
        if (SKILL_LOG) Tools.log("＋新增：" + skill);
        getInstance().skills.add(skill);
    }

    public static void sendMessage(TriggerEnum trigger, Packable pack) {
        getInstance()._sendMessage(trigger, pack);
    }

    public static void sendMessage(TriggerEnum trigger) {
        getInstance()._sendMessage(trigger, null);
    }
}
