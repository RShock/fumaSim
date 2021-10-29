package xiaor;

import xiaor.charas.胆小纸袋狼_沃沃;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static xiaor.Trigger.ATTACK;

public class GameBoard {
    private static GameBoard gameBoard= new GameBoard();

    public static GameBoard getInstance() {
        return gameBoard;
    }

    private GameBoard() {
        ourChara = new ArrayList<>();
        enemyChara = new ArrayList<>();
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

    public void run(String originS) {
        int our = 1,their = 1;
        char mode = 'a';
        String[] split = originS.split("\\s+");
        for (String s : split) {
            our = s.charAt(0) - '0' -1;
            their = s.charAt(2) - '0' -1;
            mode = s.charAt(1);
            if(mode == 'a') {
                ourChara.get(our).attack(enemyChara.get(their));
            }else{
                ourChara.get(our).skill(enemyChara.get(their));
            }
        }
    }
}
