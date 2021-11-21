package xiaor.charas;

public class 时之裂缝_复旦 extends Chara {
    Double 必杀伤害加成;

    public static 时之裂缝_复旦 init(String s) {
        时之裂缝_复旦 复旦 = new 时之裂缝_复旦();
        复旦.name = "复旦";
        复旦.element = Element.光属性;
        复旦.role = Role.守护者;
        复旦.life = 73252441;
        复旦.attack =  1441837;
        baseInit(复旦, s);
        return 复旦;
    }

    @Override
    public void initSkills() {
    }
}
