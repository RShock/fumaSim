package xiaor.tools.tester;

import xiaor.GameBoard;
import xiaor.charas.Chara;
import xiaor.charas.ImportedChara;
import xiaor.excel.OneTestExcelWriter;
import xiaor.logger.Logger;
import xiaor.tools.Tools;
import xiaor.tools.record.DamageRecorder;
import xiaor.tools.record.ExcelDamageRecord;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 对一个队伍进行全面的检测，输出模板为全方位测试输出表
 */
public class OneTest {
    /**
     * 有的时候 不同羁绊CD不同 action也不同，所以准备了一个数组（对于星不同CD不同的，暂时不做）
     * 第6列用于全队羁绊控制
     */
    private final String action;
    List<ImportedChara> charas;
    GameBoard gameBoard = GameBoard.getInstance();
    DamageRecorder damageRecorder = DamageRecorder.getInstance();

    public OneTest(List<String> charaNames, String action) {
        this.action = action;
        init(charaNames);
        Logger.INSTANCE.setLogLevel(Logger.LogLevel.NONE);
    }

    public OneTest(String action) {
        if(GameBoard.getAlly().size() == 0){
            throw new RuntimeException("需要先初始化角色");
        }
        Chara 测试角色 = ImportedChara.initChara("测试角色 生命0");
        Chara 测试角色2 = ImportedChara.initChara("测试角色 生命0");
        Chara 测试角色3 = ImportedChara.initChara("测试角色 生命0");
        Chara 测试角色4 = ImportedChara.initChara("测试角色 生命0");
        Chara 测试角色5 = ImportedChara.initChara("测试角色 生命0");
        gameBoard.addEnemyChara(测试角色);
        gameBoard.addEnemyChara(测试角色2);
        gameBoard.addEnemyChara(测试角色3);
        gameBoard.addEnemyChara(测试角色4);
        gameBoard.addEnemyChara(测试角色5);
        this.action = action;
        Logger.INSTANCE.setLogLevel(Logger.LogLevel.NONE);
    }

    private void init(List<String> charaNames) {
        this.charas = Tools.initMaxChara(charaNames);
        Chara 测试角色 = ImportedChara.initChara("测试角色 生命0");
        gameBoard.addEnemyChara(测试角色);
    }

    public void test() throws IOException {
        OneTestExcelWriter excelWriter = new OneTestExcelWriter();
        gameBoard.run(action);
        var actionList = Arrays.stream(action.split("\\s+")).filter(s -> !s.isEmpty()).toList();
        excelWriter.writeAction(actionList);
        excelWriter.writeCharaData(GameBoard.getAlly());
        ExcelDamageRecord excelDamageRecord = new ExcelDamageRecord(
                actionList,
                damageRecorder.exportDamagePerAction()
        );
        excelWriter.writeDamageData(excelDamageRecord);

        excelWriter.export();
    }
}
