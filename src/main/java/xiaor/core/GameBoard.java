package xiaor.core;

import lombok.Getter;
import xiaor.core.charas.Chara;
import xiaor.core.logger.LogType;
import xiaor.core.logger.Logger;
import xiaor.core.tools.GlobalDataManager;
import xiaor.core.tools.KeyEnum;
import xiaor.core.tools.record.DamageRecorder;
import xiaor.core.trigger.TriggerEnum;
import xiaor.core.trigger.TriggerManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static xiaor.core.tools.GlobalDataManager.getIntData;
import static xiaor.core.tools.GlobalDataManager.incIntData;

@Getter
public class GameBoard {
    private static final GameBoard gameBoard = new GameBoard();
    private boolean inited;

    public boolean isSilent() {
        return isSilent;
    }

    private boolean isSilent;

    public static GameBoard getInstance() {
        return gameBoard;
    }

    private GameBoard() {
        ourChara = new ArrayList<>();
        enemyChara = new ArrayList<>();
    }

    private List<Chara> enemyChara;

    private List<Chara> ourChara;

    private String currentTurn = "我方";
    public String mode = "敌方不行动";
    private int turnActCount = 0;    //记录当前回合有几个人行动过了

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

    public static Chara selectAlly(int i) {
        return getInstance()._selectAlly(i);
    }

    private Chara _selectAlly(int i) {
        i--;
        if (ourChara.size() <= i) {
            Random random = new Random();
            int n = random.nextInt(ourChara.size());
            return ourChara.get(n);
        } else return ourChara.get(i);
    }

    public void addOurChara(Chara chara) {
        ourChara.add(chara);
    }

    public void addEnemyChara(Chara chara) {
        enemyChara.add(chara);
    }

    public void run(String action) {
        if (!inited) initSkills();
        if (!ourChara.get(0).isLeader()) {
            Logger.INSTANCE.log(LogType.其他, "警告:队长位没有设置队长标识");
        }
        TriggerManager.sendMessage(TriggerEnum.被动光环);
        TriggerManager.sendMessage(TriggerEnum.游戏开始时);
        TriggerManager.sendMessage(TriggerEnum.游戏开始时2);
        TriggerManager.sendMessage(TriggerEnum.回合开始时);
        continueRun(action);
        if (!isSilent) Logger.INSTANCE.exportHtmlLog();
    }

    public void undo() {

    }

    /**
     * 运行 但是不给任何日志
     *
     * @param action 行动顺序
     */
    public void silentRun(String action) {
        this.isSilent = true;
        run(action);
    }

    public void continueRun(String action) {
        int firstNum, secondNum;
        String[] split = action.split("\\s+");

        for (String s : split) {
            if (s.equals("")) continue;
            if (s.equals("|")) {
                endTurn();
                continue;
            }
            firstNum = s.charAt(0) - '0' - 1;
            if (s.length() == 2)
                secondNum = 0;
            else
                secondNum = s.charAt(2) - '0' - 1;

            Chara a = currentTurn.equals("敌方")?enemyChara.get(firstNum):ourChara.get(firstNum);
            Chara b = currentTurn.equals("敌方")?ourChara.get(secondNum):enemyChara.get(secondNum);
            switch (s.charAt(1)) {
                case 'a', 'A':
                    a.attack(b);
                    break;
                case 'd', 'D':
                    a.defend(b);
                    break;
                default:
                    a.skill(b);
            }
        }
    }

    public void endTurn() {
        if (mode.equals("敌方不行动")) {
            TriggerManager.sendMessage(TriggerEnum.回合结束, null);
            Logger.INSTANCE.log(LogType.回合开始, "第" + (incIntData(KeyEnum.GAME_TURN)) + "回合开始");
            TriggerManager.sendMessage(TriggerEnum.回合开始时, null);
            return;
        }
        switch (currentTurn) {
            case "我方":
                currentTurn = "敌方";
                TriggerManager.sendMessage(TriggerEnum.敌方回合开始, null);
                Logger.INSTANCE.log(LogType.敌方回合开始, "第" + getIntData(KeyEnum.GAME_TURN) + "敌方回合开始");
                break;
            case "敌方":
                currentTurn = "我方";
                TriggerManager.sendMessage(TriggerEnum.回合结束, null);
                Logger.INSTANCE.log(LogType.回合开始, "第" + (incIntData(KeyEnum.GAME_TURN)) + "回合开始");
                TriggerManager.sendMessage(TriggerEnum.回合开始时, null);
        }
    }

    public int getCurrentT(){
        return getIntData(KeyEnum.GAME_TURN);
    }
    public void initSkills() {
        inited = true;
        GlobalSkillRegister.registerSkill();
        ourChara.forEach(Chara::initSkills);
        enemyChara.forEach(Chara::initSkills);
        DamageRecorder.init();
    }

    public void resetBoard() {
        ourChara = new ArrayList<>();
        enemyChara = new ArrayList<>();
        TriggerManager.getInstance().reset();
        DamageRecorder.getInstance().clear();
        GlobalDataManager.reset();
        Logger.INSTANCE.reset();
        isSilent = false;
        inited = false;
        currentTurn = "我方";
        mode = "敌方不行动";
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
