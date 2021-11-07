package xiaor;

public interface Skill {
    //Todo skill type
    public TriggerEnum getTrigger();

    boolean check(MessagePack pack);

    boolean cast(MessagePack pack);
}
