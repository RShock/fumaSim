package xiaor.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import static xiaor.Common.getResourcePath;

/**
 * 把测试结果输出到excel里
 * 读取excel用的是另一个库，写入时因为全是方格操作不能用那个库
 */
public class FullTestExcelWriter extends ExcelWriter{


    public final String excelPath = getResourcePath(this.getClass(), "全方位测试输出表样板.xlsx");
    String exportPath = "output\\全测试" + new SimpleDateFormat("MM月dd日HH点mm分ss秒").format(new Date()) + ".xlsx";
    Workbook book = new XSSFWorkbook(excelPath);

    public FullTestExcelWriter() throws IOException {
        damageSheet = book.getSheet("满配伤害");

        result = new ExcelTagFinder(damageSheet,
                Set.of("{伤害表}", "{角色简表}", "{角色信息表}", "{羁绊表}", "{五星表}", "{练度表}", "{贡献度表}", "{伤害构成}", "{行动轴}")).getResult();
    }

    public void setName(String name) {
        this.exportPath = name + new SimpleDateFormat("MM月dd日HH点mm分ss秒").format(new Date()) + ".xlsx";
    }

    public void export() throws IOException {
        FileOutputStream fos = new FileOutputStream(exportPath);
        XSSFFormulaEvaluator.evaluateAllFormulaCells(book);
        book.write(fos);
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

    public void writeProficiencyMatrix(Long[][] 练度Matrix) {
        int startRow = result.get("{练度表}").getFirst();
        int startCell = result.get("{练度表}").getSecond();
        setCellBlock(练度Matrix, startRow, startCell);
    }

    private void setCellBlock(Long[][] 练度Matrix, int startRow, int startCell) {
        for (int i = 0; i < 6; i++) {
            Row row = getRow(damageSheet, startRow + i);
            for (int j = 0; j < 4; j++) {
                Cell cell = row.createCell(startCell + j);
                cell.setCellValue(练度Matrix[i][j]);
            }
        }
    }

    public void writeSkillLevelMatrix(Long[][] skillLevelMatrix) {
        int startRow = result.get("{羁绊表}").getFirst();
        int startCell = result.get("{羁绊表}").getSecond();
        setCellBlock(skillLevelMatrix, startRow, startCell);
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

}
