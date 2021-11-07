package xiaor;

import java.util.ArrayList;
import java.util.List;

public class TriggerManager {
    public static Boolean SKILL_LOG = false;

    public static Boolean BUFF_LOG = true;

    public static Boolean PRIVATE_MSG = false;

    public List<Skill> skills;

    private int IDGen = 0;

    public static int getNewID() {
        return getInstance().getIDGen();
    }

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

    private boolean addBuff(Buff buff) {
        System.out.println("＋新增buff: " + buff);
        skills.add(buff);
        return true;
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
