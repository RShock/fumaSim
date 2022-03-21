package xiaor.charas;

public class 超级机器人木桩 extends Chara {
    Double 必杀伤害加成;

    public static 超级机器人木桩 init(String s) {
        超级机器人木桩 木桩 = new 超级机器人木桩();
        木桩.name = "木桩";
        木桩.role = Role.攻击者;
        木桩.life = 2147483192;
        baseInit(木桩, s);
        return 木桩;
    }

    @Override
    public void initSkills() {

    }
}
