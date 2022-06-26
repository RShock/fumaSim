package xiaor.mutual.tester;

import xiaor.core.GameBoard;
import xiaor.core.charas.Chara;
import xiaor.core.charas.ImportedChara;
import xiaor.core.excel.FullTestExcelWriter;
import xiaor.core.logger.Logger;
import xiaor.core.Tools;
import xiaor.core.tools.record.DamageRecorder;
import xiaor.core.tools.record.ExcelDamageRecord;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 对一个队伍进行全面的检测，输出模板为全方位测试输出表
 */
public class FullTest {
    /**
     * 有的时候 不同羁绊CD不同 action也不同，所以准备了一个数组（对于星不同CD不同的，暂时不做）
     * 第6列用于全队羁绊控制
     */
    private final String[][] action = new String[6][5];
    private final List<String> charaNames;
    List<ImportedChara> charas;
    GameBoard gameBoard = GameBoard.getInstance();
    DamageRecorder damageRecorder = DamageRecorder.getInstance();

    public FullTest(List<String> charaNames, String action) {
        gameBoard.resetBoard();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                this.action[i][j] = action;
            }
        }
        this.charaNames = charaNames;
        init(charaNames);
        Logger.INSTANCE.setLogLevel(Logger.LogLevel.NONE);
    }

    private void init(List<String> charaNames) {
        this.charas = Tools.initMaxChara(charaNames);
        Chara 测试角色 = ImportedChara.initChara("测试角色 生命0");
        gameBoard.addEnemyChara(测试角色);
    }

    /**
     * 为指定羁绊无法达成满配CD的角色设置新的轴，低于这个羁绊的全部会使用这个轴
     *
     * @param charaIndex 角色位置，为真正的下标+1 如果为6则为全队羁绊均下降
     * @param flower     花数
     * @param newAction  行动轴
     */
    public void setAction(int charaIndex, int flower, String newAction) {
        for (int i = 0; i < flower - 1; i++) {
            action[charaIndex - 1][i] = newAction;
        }
    }

    public void fullTest() throws IOException {
        FullTestExcelWriter excelWriter = new FullTestExcelWriter();
        gameBoard.silentRun(action[4][4]);
        excelWriter.writeCharaData(GameBoard.getAlly());
        var actionList = Arrays.stream(action[4][4].split("\\s+"))
                .filter(s -> !s.isEmpty())
                .map(s -> s.length() == 2? s+="1": s)
                .toList();
        excelWriter.writeAction(actionList);
        ExcelDamageRecord excelDamageRecord = new ExcelDamageRecord(actionList, damageRecorder.exportDamagePerAction());
        excelWriter.writeDamageData(excelDamageRecord);
        Long[][] skillLevelMatrix = skillLevelTest();
        excelWriter.writeSkillLevelMatrix(skillLevelMatrix);
        Long[][] proficiencyMatrix = proficiencyTest();
        excelWriter.writeProficiencyMatrix(proficiencyMatrix);
        Long[] starList = starTest();
        excelWriter.writeStarList(starList);
        Long[] contributeList = contributeTest();
        excelWriter.writeContributeList(contributeList);
        excelWriter.writeDamagePart(excelDamageRecord.getDamagePart());
        excelWriter.export();
    }

    private Long[] contributeTest() {
        Long[] contributeList = new Long[5];
        for (int charaIndex = 0; charaIndex < 5; charaIndex++) {
            gameBoard.resetBoard();
            init(charaNames);
            charas.get(charaIndex).setDisabled();
            gameBoard.silentRun(action[4][4]);
            contributeList[charaIndex] = DamageRecorder.getInstance().calAllDamage();
        }
        return contributeList;
    }

    private Long[] starTest() {
        Long[] starMatrix = new Long[6];
        for (int charaIndex = 0; charaIndex < 5; charaIndex++) {
            gameBoard.resetBoard();
            init(charaNames);
            charas.get(charaIndex).setStar(4);
            gameBoard.silentRun(action[4][4]);
            starMatrix[charaIndex] = DamageRecorder.getInstance().calAllDamage();
        }
        gameBoard.resetBoard();
        init(charaNames);
        charas.forEach(chara -> chara.setStar(4));
        gameBoard.silentRun(action[4][4]);
        starMatrix[5] = DamageRecorder.getInstance().calAllDamage();
        return starMatrix;
    }

    Long[][] skillLevelTest() {
        Long[][] damageMatrix = new Long[6][4];
        for (int charaIndex = 0; charaIndex < 5; charaIndex++) {
            for (int skillLevel = 1; skillLevel <= 4; skillLevel++) {
                gameBoard.resetBoard();
                init(charaNames);
                charas.get(charaIndex).setSkillLevel(skillLevel);
                gameBoard.silentRun(action[charaIndex][skillLevel - 1]);
                damageMatrix[charaIndex][skillLevel - 1] = DamageRecorder.getInstance().calAllDamage();
            }
        }
        for (int skillLevel = 1; skillLevel <= 4; skillLevel++) {
            gameBoard.resetBoard();
            init(charaNames);
            int finalSkillLevel = skillLevel;
            charas.forEach(c -> c.setSkillLevel(finalSkillLevel));
            gameBoard.silentRun(action[5][skillLevel - 1]);
            damageMatrix[5][skillLevel - 1] = DamageRecorder.getInstance().calAllDamage();
        }
        return damageMatrix;
    }

    Long[][] proficiencyTest() {
        double proficiencyTable[] = {0, 0, 0.43, 0.61, 0.82, 1};
        Long[][] damageMatrix = new Long[6][4];
        for (int charaIndex = 0; charaIndex < 5; charaIndex++) {
            for (int proficiency = 1; proficiency <= 4; proficiency++) {
                gameBoard.resetBoard();
                init(charaNames);
                Chara chara = charas.get(charaIndex);
                chara.setBaseAttack((int) (chara.getBaseAttack() * proficiencyTable[proficiency]));
                chara.setBaseLife((int) (chara.getBaseLife() * proficiencyTable[proficiency]));
                gameBoard.silentRun(action[4][4]);
                damageMatrix[charaIndex][proficiency - 1] = DamageRecorder.getInstance().calAllDamage();
            }
        }
        for (int i = 1; i <= 4; i++) {
            gameBoard.resetBoard();
            init(charaNames);
            double proficiency = proficiencyTable[i];
            charas.forEach(c -> c.setBaseAttack((int) (proficiency * c.getBaseAttack())));
            charas.forEach(c -> c.setBaseLife((int) (proficiency * c.getBaseLife())));
            gameBoard.silentRun(action[4][4]);
            damageMatrix[5][i - 1] = DamageRecorder.getInstance().calAllDamage();
        }
        return damageMatrix;
    }
}
