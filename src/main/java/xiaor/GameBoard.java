package xiaor;

import xiaor.charas.胆小纸袋狼_沃沃;

import java.util.ArrayList;
import java.util.List;

import static xiaor.Trigger.ATTACK;

public class GameBoard {
    private static GameBoard gameBoard= new GameBoard();

    public static GameBoard getInstance() {
        return gameBoard;
    }

    private GameBoard() {
        ourChara = new ArrayList<>();
        enemyChara = new ArrayList<>();
        TriggerManager.getInstance().registerSkill(
                new BaseSkill(ATTACK, pack->true, pack-> new AttackDamageCal(pack).handle()));
    }

    private int turn;

    private List<Chara> enemyChara;

    private List<Chara> ourChara;

    //private List<Skill> boardSkill; //场地魔法

    public void addOurChara(Chara chara) {
        ourChara.add(chara);
    }

    public void addEnemyChara(Chara chara) {
        enemyChara.add(chara);
    }

    public void run(String s) {
        ourChara.get(0).attack(enemyChara.get(0));
    }
}
