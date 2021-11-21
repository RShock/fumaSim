package xiaor.charas;

public class 五十四层boss_诺诺可 extends Chara {
    Double 必杀伤害加成;

    public static 五十四层boss_诺诺可 init(String s) {
        五十四层boss_诺诺可 诺诺可 = new 五十四层boss_诺诺可();
        诺诺可.name = "诺诺可";
        诺诺可.element = Element.水属性;
        诺诺可.role = Role.攻击者;
        诺诺可.life = 73252441;
        诺诺可.attack =  1441837;
        诺诺可.必杀伤害加成 = 0.1;
        baseInit(诺诺可, s);
        return 诺诺可;
    }

    @Override
    public void initSkills() {

    }
}
