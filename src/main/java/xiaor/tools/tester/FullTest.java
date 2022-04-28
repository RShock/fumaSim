package xiaor.tools.tester;

import xiaor.GameBoard;
import xiaor.charas.Chara;
import xiaor.charas.ImportedChara;
import xiaor.excel.ExcelWriter;
import xiaor.tools.Tools;
import xiaor.tools.record.DamageRecorder;
import xiaor.tools.record.ExcelDamageRecord;

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
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                this.action[i][j] = action;
            }
        }
        this.charaNames = charaNames;
        init(charaNames);
    }

    private void init(List<String> charaNames) {
        this.charas = Tools.initMaxChara(charaNames);
        Chara 测试角色 = ImportedChara.initChara("测试 角色 生命0");
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
        ExcelWriter excelWriter = new ExcelWriter();
        gameBoard.run(action[4][4]);
        excelWriter.writeCharaData(GameBoard.getAlly());
        ExcelDamageRecord excelDamageRecord = new ExcelDamageRecord(
                Arrays.stream(action[4][4].split("\\s+")).filter(s -> !s.isEmpty()).toList(),
                damageRecorder.exportDamagePerAction()
        );
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
            gameBoard.run(action[4][4]);
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
            gameBoard.run(action[4][4]);
            starMatrix[charaIndex] = DamageRecorder.getInstance().calAllDamage();
        }
        gameBoard.resetBoard();
        init(charaNames);
        charas.forEach(chara -> chara.setStar(4));
        gameBoard.run(action[4][4]);
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
                gameBoard.run(action[charaIndex][skillLevel - 1]);
                damageMatrix[charaIndex][skillLevel - 1] = DamageRecorder.getInstance().calAllDamage();
            }
        }
        for (int skillLevel = 1; skillLevel <= 4; skillLevel++) {
            gameBoard.resetBoard();
            init(charaNames);
            int finalSkillLevel = skillLevel;
            charas.forEach(c -> c.setSkillLevel(finalSkillLevel));
            gameBoard.run(action[5][skillLevel - 1]);
            damageMatrix[5][skillLevel - 1] = DamageRecorder.getInstance().calAllDamage();
        }
        return damageMatrix;
    }

    Long[][] proficiencyTest() {
        Long[][] damageMatrix = new Long[6][4];
        for (int charaIndex = 0; charaIndex < 5; charaIndex++) {
            for (int proficiency = 1; proficiency <= 4; proficiency++) {
                gameBoard.resetBoard();
                init(charaNames);
                Chara chara = charas.get(charaIndex);
                chara.setBaseAttack((int) (chara.getBaseAttack() * proficiency * 0.2));
                gameBoard.run(action[4][4]);
                damageMatrix[charaIndex][proficiency - 1] = DamageRecorder.getInstance().calAllDamage();
            }
        }
        for (int i = 1; i <= 4; i++) {
            gameBoard.resetBoard();
            init(charaNames);
            double proficiency = i * 0.2;
            charas.forEach(c -> c.setBaseAttack((int) (proficiency * c.getBaseAttack())));
            gameBoard.run(action[4][4]);
            damageMatrix[5][i - 1] = DamageRecorder.getInstance().calAllDamage();
        }
        return damageMatrix;
    }
}
