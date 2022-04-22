package xiaor.tools.tester;

import xiaor.GameBoard;
import xiaor.charas.Chara;
import xiaor.charas.ImportedChara;
import xiaor.excel.ExcelCharaProvider;
import xiaor.excel.ExcelWriter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 对一个队伍进行全面的检测，输出模板为全方位测试输出表
 */
public class FullTest {
    List<Chara> charas;
    GameBoard gameBoard = GameBoard.getInstance();

    public FullTest(List<String> charaNames, String action) throws IOException {
        ExcelWriter excelWriter = new ExcelWriter();
        initMaxChara(charaNames);
        gameBoard.run(action);
        excelWriter.writeCharaData(charas);
//        excelWriter.writeDamageData();
    }

    private void initMaxChara(List<String> charaNames) {
        charas = charaNames.stream().map(ExcelCharaProvider::getCharaByName)
                .peek(ImportedChara::maxData)
                .peek(gameBoard::addOurChara)
                .collect(Collectors.toList());
        charas.get(0).setLeader(true);
        Chara 测试角色 = ImportedChara.initChara("测试 角色 生命0");
        gameBoard.addEnemyChara(测试角色);
        gameBoard.initSkills();
    }
}
