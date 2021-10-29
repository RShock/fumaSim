package xiaor;

import java.util.ArrayList;
import java.util.List;

public class TriggerManager {
    public List<Skill> skills;

    private static TriggerManager triggerManager= new TriggerManager();

    private TriggerManager() {
        skills = new ArrayList<>();
    }
    public static TriggerManager getInstance() {
        return triggerManager;
    }

    public void registerSkill(Skill skill){
        skills.add(skill);
    }

    public void sendMessage(Trigger trigger, MessagePack pack) {
        skills.stream().filter(skill -> skill.getTrigger().equals(trigger))
                .filter(skill -> skill.check(pack))
                .forEach(skill -> skill.cast(pack));
    }
}
