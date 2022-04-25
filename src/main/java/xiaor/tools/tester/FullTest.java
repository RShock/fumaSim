package xiaor.tools.tester;

import xiaor.GameBoard;
import xiaor.charas.Chara;
import xiaor.charas.ImportedChara;
import xiaor.excel.ExcelCharaProvider;
import xiaor.excel.ExcelWriter;
import xiaor.tools.record.DamageRecorder;
import xiaor.tools.record.ExcelDamageRecord;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * 对一个队伍进行全面的检测，输出模板为全方位测试输出表
 */
public class FullTest {
    private final String action;
    private final List<String> charaNames;
    List<Chara> charas;
    GameBoard gameBoard = GameBoard.getInstance();
    DamageRecorder damageRecorder = DamageRecorder.getInstance();

    public FullTest(List<String> charaNames, String action) throws IOException {
        this.action = action;
        this.charaNames = charaNames;
        initMaxChara(charaNames);
        fullTest();
    }

    public void fullTest() throws IOException {
        ExcelWriter excelWriter = new ExcelWriter();
        gameBoard.run(action);
        excelWriter.writeCharaData(charas);
        ExcelDamageRecord excelDamageRecord = new ExcelDamageRecord(
                Arrays.stream(action.split("\\s+")).filter(s -> !s.isEmpty()).toList(),
                damageRecorder.exportDamagePerAction()
        );
        excelWriter.writeDamageData(excelDamageRecord);
        Long[][] skillLevelMatrix = skillLevelTest();
        excelWriter.writeSkillLevelMatrix(skillLevelMatrix);
        Long[][] proficiencyMatrix = proficiencyTest();
        excelWriter.writeProficiencyMatrix(proficiencyMatrix);
        excelWriter.export();
    }

    private void initMaxChara(List<String> charaNames) {
        charas = charaNames.stream().map(ExcelCharaProvider::getCharaByName)
                .peek(ImportedChara::maxData)
                .peek(gameBoard::addOurChara)
                .collect(toList());
        charas.get(0).setLeader(true);
        Chara 测试角色 = ImportedChara.initChara("测试 角色 生命0");
        gameBoard.addEnemyChara(测试角色);
    }

    Long[][] skillLevelTest() {
        Long[][] damageMatrix = new Long[6][4];
        for (int charaIndex = 0; charaIndex < 5; charaIndex++) {
            for (int skillLevel = 1; skillLevel <= 4; skillLevel++) {
                gameBoard.resetBoard();
                initMaxChara(charaNames);
                charas.get(charaIndex).setSkillLevel(skillLevel);
                gameBoard.run(action);
                damageMatrix[charaIndex][skillLevel - 1] = DamageRecorder.getInstance().calAllDamage();
            }
        }
        for (int skillLevel = 1; skillLevel <= 4; skillLevel++) {
            gameBoard.resetBoard();
            initMaxChara(charaNames);
            int finalSkillLevel = skillLevel;
            charas.forEach(c -> c.setSkillLevel(finalSkillLevel));
            gameBoard.run(action);
            damageMatrix[5][skillLevel - 1] = DamageRecorder.getInstance().calAllDamage();
        }
        return damageMatrix;
    }

    Long[][] proficiencyTest() {
        Long[][] damageMatrix = new Long[6][4];
        for (int charaIndex = 0; charaIndex < 5; charaIndex++) {
            for (int proficiency = 1; proficiency <= 4; proficiency++) {
                gameBoard.resetBoard();
                initMaxChara(charaNames);
                Chara chara = charas.get(charaIndex);
                chara.setBaseAttack((int) (chara.getBaseAtk() * proficiency * 0.2));
                gameBoard.run(action);
                damageMatrix[charaIndex][proficiency - 1] = DamageRecorder.getInstance().calAllDamage();
            }
        }
        for (int i = 1; i <= 4; i++) {
            gameBoard.resetBoard();
            initMaxChara(charaNames);
            double proficiency = i * 0.2;
            charas.forEach(c -> c.setBaseAttack((int) (proficiency * c.getBaseAtk())));
            gameBoard.run(action);
            damageMatrix[5][i - 1] = DamageRecorder.getInstance().calAllDamage();
        }
        return damageMatrix;
    }
}
