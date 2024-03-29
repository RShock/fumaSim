package xiaor.core;

import lombok.Getter;
import xiaor.core.charas.Chara;
import xiaor.core.logger.LogType;
import xiaor.core.logger.Logger;
import xiaor.core.tools.GlobalDataManager;
import xiaor.core.tools.record.DamageRecorder;
import xiaor.core.trigger.TriggerEnum;
import xiaor.core.trigger.TriggerManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        int our, their;
        char mode;
        String[] split = action.split("\\s+");
        for (String s : split) {
            if (s.equals("")) continue;
            if (s.equals("|")) {
                TriggerManager.sendMessage(TriggerEnum.回合结束, null);
                continue;
            }
            our = s.charAt(0) - '0' - 1;
            if (s.length() == 2)
                their = 0;
            else
                their = s.charAt(2) - '0' - 1;
            mode = s.charAt(1);
            if (mode == 'a' || mode == 'A') {
                ourChara.get(our).attack(enemyChara.get(their));
            } else if (mode == 'd' || mode == 'D') {
                ourChara.get(our).defend(enemyChara.get(their));
            } else {
                ourChara.get(our).skill(enemyChara.get(their));
            }
        }
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
