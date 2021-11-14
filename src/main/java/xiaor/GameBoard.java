package xiaor;

import lombok.Getter;
import xiaor.skill.BaseSkill;
import xiaor.skill.Skill;
import xiaor.newStory.action.BuffType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static xiaor.Common.INFI;

@Getter
public class GameBoard {
    private static GameBoard gameBoard = new GameBoard();

    public static GameBoard getInstance() {
        return gameBoard;
    }

    private GameBoard() {
        ourChara = new ArrayList<>();
        enemyChara = new ArrayList<>();
//        TriggerManager.getInstance().registerSkill(Trigger.)
    }

    private int turn;

    private List<Chara> enemyChara;

    private List<Chara> ourChara;

    public static List<Chara> getAlly() {
        return getInstance().getOurChara();
    }

    public static Chara selectTarget(int i) {
        return getInstance()._selectTarget(i);
    }

    private Chara _selectTarget(int i) {
        i--;
        if(enemyChara.size() <= i){
            Random random = new Random();
            int n = random.nextInt(enemyChara.size());
            return enemyChara.get(n);
        }
        else return enemyChara.get(i);
    }

    //private List<Skill> boardSkill; //场地魔法

    public void addOurChara(Chara chara) {
        ourChara.add(chara);
    }

    public void addEnemyChara(Chara chara) {
        enemyChara.add(chara);
    }

    public void run(String originS) {
        TriggerManager.getInstance().sendMessage(TriggerEnum.游戏开始时, new MessagePack());
        int our = 1, their = 1;
        char mode = 'a';
        String[] split = originS.split("\\s+");
        for (String s : split) {
            our = s.charAt(0) - '0' - 1;
            their = s.charAt(2) - '0' - 1;
            mode = s.charAt(1);
            if (mode == 'a') {
                ourChara.get(our).attack(enemyChara.get(their));
            } else {
                ourChara.get(our).skill(enemyChara.get(their));
            }
        }
    }

    public void initSkills() {
        ourChara.forEach(Chara::initSkills);
        Skill skill = BaseSkill.builder().name("【系统级规则】属性克制 优势方+50%伤害").trigger(TriggerEnum.普攻伤害计算)
                .time(INFI)
                .check(pack -> {
                    if(pack.damageCal == null)return false;
                    return pack.damageCal.pack.caster.counter(pack.damageCal.pack.acceptor) == 1;
                }).cast(pack -> {
                    pack.getDamageCal().changeDamage(BuffType.属性克制, 0.5);
                    return true;
                }).build();
        TriggerManager.registerSkill(skill);
        skill = BaseSkill.builder().name("【系统级规则】属性克制 优势方+50%伤害").trigger(TriggerEnum.技能伤害计算)
                .time(INFI)
                .check(pack -> {
                    if(pack.damageCal == null)return false;
                    return pack.damageCal.pack.caster.counter(pack.damageCal.pack.acceptor) == 1;
                }).cast(pack -> {
                    pack.getDamageCal().changeDamage(BuffType.属性克制, 0.5);
                    return true;
                }).build();
        TriggerManager.registerSkill(skill);
    }

    public void resetBoard() {
        ourChara = new ArrayList<>();
        enemyChara = new ArrayList<>();
        TriggerManager.getInstance().reset();
    }

    public static Chara getCurrentEnemy() {
        if (getInstance().getEnemyChara().isEmpty()) {
            return null;
        }
        return getInstance().getEnemyChara().get(0);
    }
}
