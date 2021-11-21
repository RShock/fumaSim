package xiaor.tools;

import lombok.Getter;
import xiaor.MessagePack;
import xiaor.skill.Buff;
import xiaor.skill.Skill;
import xiaor.skill.UniqueBuff;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class TriggerManager {
    public static Boolean SKILL_LOG = false;

    public static Boolean BUFF_LOG = true;

    public static Boolean PRIVATE_MSG = false;

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

    public static boolean registerBuff(Buff buff) {
        return TriggerManager.getInstance().addBuff(buff);
    }

    private boolean addBuff(Buff newBuff) {
        //可堆叠buff特殊处理
        if(newBuff instanceof UniqueBuff newUniqueBuff) {
            System.out.println("＋新增可堆叠buff: " + newBuff);
            Optional<UniqueBuff> first = skills.stream().filter(skill -> skill instanceof UniqueBuff)
                    .map(skill -> (UniqueBuff)skill)
                    .filter(buff -> buff.uniqueId.equals(newUniqueBuff.uniqueId))
                    .findFirst();
            if(first.isPresent()){
                first.get().add(newUniqueBuff);
                System.out.println("堆叠成功，当前层数" + first.get().currentLevel);
            }else{
                skills.add(newBuff);
            }
            return true;
        }
        System.out.println("＋新增buff: " + newBuff);
        skills.add(newBuff);
        return true;
    }

    public static List<Skill> getSkill() {
        return getInstance().skills;
    }
    public boolean _sendMessage(TriggerEnum trigger, MessagePack pack) {
        boolean isRespond = false;
        //普通for循环防止迭代器问题
        for (int i = 0; i < skills.size(); i++) {
            if (!skills.get(i).getTrigger().equals(trigger)) continue;
            if (!skills.get(i).check(pack)) continue;
            isRespond = true;
            if(trigger != TriggerEnum.内部事件 || PRIVATE_MSG) {
                System.out.println(trigger + "，触发 " + skills.get(i).toString());
            }
            skills.get(i).cast(pack);
        }
        return isRespond;
    }

    public static boolean registerSkill(Skill skill) {
        if (SKILL_LOG) System.out.println("＋新增：" + skill);
        getInstance().addSkill(skill);
        return true;
    }

    public static boolean sendMessage(TriggerEnum trigger, MessagePack pack) {
        return getInstance()._sendMessage(trigger, pack);
    }

    private boolean addSkill(Skill skill) {
        skills.add(skill);
        return true;
    }

}
