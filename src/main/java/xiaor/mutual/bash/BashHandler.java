package xiaor.mutual.bash;

import xiaor.core.GameBoard;
import xiaor.core.charas.ImportedChara;
import xiaor.core.exception.CharaNotFound;

public class BashHandler {
    private static final BashHandler instance = new BashHandler();

    private GameBoard gameBoard = GameBoard.getInstance();

    public static BashHandler init() {
        return instance;
    }

    public void setChara(String s) {
        try {
            ImportedChara importedChara = ImportedChara.initChara(s);
            if (s.contains("满配")) {
                BashTools.sendMsg("将以满配来创建角色");
            }
            GameBoard.getAlly().add(importedChara);
            BashTools.sendMsg("角色创建完毕");
        } catch (CharaNotFound e){
            BashTools.sendMsg("没找到对应角色，请检查角色名称与格式");
        }
    }

    public void setEnemy() {

    }

    public void reset() {
        gameBoard.resetBoard();
    }

    public void greedSearch() {}

}
