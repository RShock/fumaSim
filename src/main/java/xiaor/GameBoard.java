package xiaor;

import lombok.Getter;
import xiaor.charas.Chara;
import xiaor.skill.BaseSkill;
import xiaor.skill.Skill;
import xiaor.skill.BuffType;
import xiaor.tools.*;
import xiaor.tools.record.DamageRecorder;
import xiaor.trigger.TriggerEnum;
import xiaor.trigger.TriggerManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static xiaor.Common.INFI;
import static xiaor.tools.GlobalDataManager.getIntData;

@Getter
public class GameBoard {
    private static final GameBoard gameBoard = new GameBoard();

    public static GameBoard getInstance() {
        return gameBoard;
    }

    private GameBoard() {
        ourChara = new ArrayList<>();
        enemyChara = new ArrayList<>();
    }

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
        if (enemyChara.size() <= i) {
            Random random = new Random();
            int n = random.nextInt(enemyChara.size());
            return enemyChara.get(n);
        } else return enemyChara.get(i);
    }

    public void addOurChara(Chara chara) {
        ourChara.add(chara);
    }

    public void addEnemyChara(Chara chara) {
        enemyChara.add(chara);
    }

    public void run(String originS) {
        TriggerManager.sendMessage(TriggerEnum.被动光环, new MessagePack());
        TriggerManager.sendMessage(TriggerEnum.游戏开始时, new MessagePack());
        int our, their;
        char mode;
        String[] split = originS.split("\\s+");
        for (String s : split) {
            if(s.equals(""))continue;
            if(s.equals("|")){
                TriggerManager.sendMessage(TriggerEnum.回合结束, null);
                continue;
            }
            our = s.charAt(0) - '0' - 1;
            if(s.length() == 2)
                their = 0;
            else
                their = s.charAt(2) - '0' - 1;
            mode = s.charAt(1);
            if (mode == 'a' || mode == 'A') {
                ourChara.get(our).attack(enemyChara.get(their));
            } else if (mode == 'd'|| mode == 'D') {
                ourChara.get(our).defend(enemyChara.get(their));
            } else {
                ourChara.get(our).skill(enemyChara.get(their));
            }
        }
    }

    public void initSkills() {
        Skill skill = BaseSkill.builder().name("【系统规则】属性克制 优势方+50%伤害").trigger(TriggerEnum.伤害计算)
                .time(INFI)
                .check(pack -> {
                    if (pack.damageCal == null) return false;
                    return pack.damageCal.pack.caster.counter(pack.damageCal.pack.acceptors.get(0)) == 1;
                }).cast(pack -> {
                    pack.getDamageCal().changeDamage(BuffType.属性克制, 0.5);
                    return true;
                }).build();
        TriggerManager.registerSkill(skill);
        skill = BaseSkill.builder().name("【系统规则】属性克制 劣势方-25%伤害").trigger(TriggerEnum.伤害计算)
                .time(INFI)
                .check(pack -> {
                    if (pack.damageCal == null) return false;
                    return pack.damageCal.pack.caster.counter(pack.damageCal.pack.acceptors.get(0)) == -1;
                }).cast(pack -> {
                    pack.getDamageCal().changeDamage(BuffType.属性克制, -0.25);
                    return true;
                }).build();
        TriggerManager.registerSkill(skill);
        //游戏开始前其实还有第0回合，不过暂时先把游戏开始当作整个系统启动的第一事件吧
        skill = BaseSkill.builder().name("【系统规则】第一回合").trigger(TriggerEnum.游戏开始时)
                .time(INFI)
                .check(pack -> true)
                .cast(pack -> {
                    GlobalDataManager.putIntData(KeyEnum.GAMETURN, 1);
                    Tools.log(Tools.LogColor.RED, "第1回合开始");
                    TriggerManager.sendMessage(TriggerEnum.回合开始, null);
                    GameBoard.getAlly().forEach(chara -> chara.setStatus(Chara.CharaStatus.ACTIVE));
                    return true;
                }).build();
        TriggerManager.registerSkill(skill);
        //目前设计 回合结束后默认没敌人的回合 继续是我方回合
        skill = BaseSkill.builder().name("【系统规则】角色行动完进入下一回合").trigger(TriggerEnum.角色行动结束)
                .time(INFI)
                .check(pack -> GameBoard.getAlly().stream().noneMatch(chara -> chara.getStatus().equals(Chara.CharaStatus.ACTIVE)))
                .cast(pack -> {
                    TriggerManager.sendMessage(TriggerEnum.回合结束, null);
                    return true;
                }).build();
        TriggerManager.registerSkill(skill);
        //buff消退：回合结束时所有非永久buff都会消退1层
        skill = BaseSkill.builder().name("【系统规则】回合结束时所有非永久buff都会消退1层").trigger(TriggerEnum.回合结束)
                .time(INFI)
                .check(pack -> true)
                .cast(pack -> {
                    TriggerManager.getInstance().getSkills().forEach(Skill::decrease);
                    GlobalDataManager.putIntData(KeyEnum.GAMETURN, getIntData(KeyEnum.GAMETURN) + 1);
                    Tools.log(Tools.LogColor.RED, "第" + (getIntData(KeyEnum.GAMETURN)) + "回合开始");
                    TriggerManager.sendMessage(TriggerEnum.回合开始, null);
                    return true;
                }).build();
        TriggerManager.registerSkill(skill);
        skill = BaseSkill.builder().name("【系统规则】回合开始时所有角色恢复可用状态").trigger(TriggerEnum.回合开始)
                .time(INFI)
                .check(pack -> true)
                .cast(pack -> {
                    GameBoard.getAlly().forEach(chara -> chara.setStatus(Chara.CharaStatus.ACTIVE));
                    return true;
                }).build();
        TriggerManager.registerSkill(skill);

        ourChara.forEach(Chara::initSkills);
        enemyChara.forEach(Chara::initSkills);
        DamageRecorder.init();
    }

    public void resetBoard() {
        ourChara = new ArrayList<>();
        enemyChara = new ArrayList<>();
        TriggerManager.getInstance().reset();
        DamageRecorder.getInstance().clear();
    }

    public static Chara getCurrentEnemy() {
        if (getInstance().getEnemyChara().isEmpty()) {
            return null;
        }
        return getInstance().getEnemyChara().get(0);
    }

    public static List<Chara> getEnemy() {
        return getInstance().getEnemyChara();
    }
}
