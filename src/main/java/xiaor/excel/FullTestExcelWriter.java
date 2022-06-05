package xiaor.excel;

import kotlin.Pair;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import xiaor.charas.Chara;
import xiaor.tools.record.ExcelDamageRecord;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import static xiaor.Common.getResourcePath;

/**
 * 把测试结果输出到excel里
 * 读取excel用的是另一个库，写入时因为全是方格操作不能用那个库
 */
public class FullTestExcelWriter {
    private final Map<String, Pair<Integer, Integer>> result;
    Sheet damageSheet;
    public final String excelPath = getResourcePath(this.getClass(), "全方位测试输出表样板.xlsx");
    String exportPath = "output\\全测试" + new SimpleDateFormat("MM月dd日HH点mm分ss秒").format(new Date()) + ".xlsx";
    Workbook book = new XSSFWorkbook(excelPath);

    public FullTestExcelWriter() throws IOException {
        damageSheet = book.getSheet("满配伤害");

        this.result = new ExcelTagFinder(damageSheet,
                Set.of("{伤害表}", "{角色简表}", "{角色信息表}", "{羁绊表}", "{五星表}", "{练度表}", "{贡献度表}", "{伤害构成}", "{行动轴}")).getResult();
    }

    public void setName(String name) {
        this.exportPath = name + new SimpleDateFormat("MM月dd日HH点mm分ss秒").format(new Date()) + ".xlsx";
    }

    public void writeCharaData(List<Chara> chara) {
        int damageCharaStartRow = result.get("{角色简表}").getFirst();
        int damageCharaStartCell = result.get("{角色简表}").getSecond();
        int charaStartRow = result.get("{角色信息表}").getFirst();
        IntStream.range(0, 5)
                .forEach(i -> writeCharaRow(charaStartRow + i, chara.get(i)));

        Row nameRow = getRow(damageSheet, damageCharaStartRow);
        Row damageRow = getRow(damageSheet, damageCharaStartRow + 1);
        for (int i = 0; i < 5; i++) {
            nameRow.getCell(damageCharaStartCell + i * 2).setCellValue(chara.get(i).getName());
            damageRow.getCell(damageCharaStartCell + i * 2).setCellValue(chara.get(i).getBaseAttack());
        }
    }

    private void writeCharaRow(int rowNum, Chara chara) {
        int charaStartCell = result.get("{角色信息表}").getSecond();

        Row row = getRow(damageSheet, rowNum);
//         dataSheet.getRow(rowNum);
        Cell nameCell = row.createCell(charaStartCell);
        Cell starCell = row.createCell(charaStartCell + 1);
        Cell skillLevel = row.createCell(charaStartCell + 2);
        Cell is6Cell = row.createCell(charaStartCell + 3);
        Cell atkCell = row.createCell(charaStartCell + 4);
        Cell hpCell = row.createCell(charaStartCell + 5);
        Cell hintCell = row.createCell(charaStartCell + 6);

        nameCell.setCellValue(chara.getName());
        starCell.setCellValue(chara.getStar());
        skillLevel.setCellValue(chara.getSkillLevel());
        is6Cell.setCellValue(chara.is6() ? "是" : "否");
        atkCell.setCellValue(chara.getBaseAttack());
        hpCell.setCellValue(chara.getBaseLife());
        hintCell.setCellValue(chara.getHint());
    }

    public void export() throws IOException {
        FileOutputStream fos = new FileOutputStream(exportPath);
        XSSFFormulaEvaluator.evaluateAllFormulaCells(book);
        book.write(fos);
    }

    public void writeDamageData(ExcelDamageRecord excelDamageRecord) {
        int damageStartRow = result.get("{伤害表}").getFirst();
        int damageStartCell = result.get("{伤害表}").getSecond();
        int size = excelDamageRecord.getDamagePairs().size();
        for (int i = 0; i < size; i++) {
            int rowNum = i / 5 + damageStartRow;
            Row row = getRow(damageSheet, rowNum);
            Cell cell = row.createCell(damageStartCell + (i % 5) * 2);
            cell.setCellValue(excelDamageRecord.getDamagePairs().get(i).action());
            cell = row.createCell(damageStartCell + (i % 5) * 2 + 1);
            cell.setCellValue(excelDamageRecord.getDamagePairs().get(i).damage());
        }
    }

    private static Row getRow(Sheet sheet, int rowNum) {
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            return sheet.createRow(rowNum);
        }
        return row;
    }

    public void writeSkillLevelMatrix(Long[][] skillLevelMatrix) {
        int startRow = result.get("{羁绊表}").getFirst();
        int startCell = result.get("{羁绊表}").getSecond();
        for (int i = 0; i < 6; i++) {
            Row row = getRow(damageSheet, startRow + i);
            for (int j = 0; j < 4; j++) {
                Cell cell = row.createCell(startCell + j);
                cell.setCellValue(skillLevelMatrix[i][j]);
            }
        }
    }

    public void writeProficiencyMatrix(Long[][] 练度Matrix) {
        int startRow = result.get("{练度表}").getFirst();
        int startCell = result.get("{练度表}").getSecond();
        for (int i = 0; i < 6; i++) {
            Row row = getRow(damageSheet, startRow + i);
            for (int j = 0; j < 4; j++) {
                Cell cell = row.createCell(startCell + j);
                cell.setCellValue(练度Matrix[i][j]);
            }
        }
    }

    public void writeStarList(Long[] starList) {
        int startRow = result.get("{五星表}").getFirst();
        int startCell = result.get("{五星表}").getSecond();
        for (int i = 0; i < 6; i++) {
            Row row = getRow(damageSheet, startRow + i);
            Cell cell = row.createCell(startCell);
            cell.setCellValue(starList[i]);
        }
    }

    public void writeContributeList(Long[] starList) {
        int startRow = result.get("{贡献度表}").getFirst();
        int startCell = result.get("{贡献度表}").getSecond();
        Row row = getRow(damageSheet, startRow);
        for (int i = 0; i < 5; i++) {
            Cell cell = row.createCell(startCell + i);
            cell.setCellValue(starList[i]);
        }
    }

    public void writeDamagePart(Long[] damagePart) {
        int startRow = result.get("{伤害构成}").getFirst();
        int startCell = result.get("{伤害构成}").getSecond();
        Row row = getRow(damageSheet, startRow);
        for (int i = 0; i < 2; i++) {
            Cell cell = row.createCell(startCell + i);
            cell.setCellValue(damagePart[i]);
        }
    }

    public void writeAction(List<String> action) {
        int actionCharaStartRow = result.get("{行动轴}").getFirst();
        int actionCharaStartCell = result.get("{行动轴}").getSecond();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < action.size(); i++) {
            if (i % 5 == 4 || i == action.size() - 1) {
                Row row = getRow(damageSheet, actionCharaStartRow + i / 5);
                Cell cell = ExcelTagFinder.getCell(row, actionCharaStartCell);
                cell.setCellValue(sb.append(action.get(i)).toString());
                sb.setLength(0);
            } else sb.append(action.get(i)).append(" ");
        }
    }
}
