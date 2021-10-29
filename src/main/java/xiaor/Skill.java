package xiaor;

public interface Skill {
    //Todo skill type
    public Trigger getTrigger();

    boolean check(MessagePack pack);

    boolean cast(MessagePack pack);
}
