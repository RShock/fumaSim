package xiaor.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import xiaor.charas.Chara;
import xiaor.tools.record.ExcelDamageRecord;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * 把测试结果输出到excel里
 * 读取excel用的是另一个库，写入时因为全是方格操作不能用那个库
 */
public class ExcelWriter {
    Sheet dataSheet;
    Sheet damageSheet;
    File resourcePath = new File(URLDecoder.decode(Objects.requireNonNull(getClass().getResource("/")).getPath(), StandardCharsets.UTF_8));
    String excelPath = resourcePath.getParent() + "/classes/全方位测试输出表样板.xlsx";
    String exportPath = "output" + new SimpleDateFormat("MM_dd_HH_mm_ss").format(new Date()) + ".xlsx";
    Workbook book = new XSSFWorkbook(excelPath);

    private static final int charaStartRow = 1;       //角色数据 第一行
    private static final int charaStartCell = 1;      //角色数据 第一列

    private static final int damageStartRow = 5;        //伤害数据 第一行

    private static final int damageStartCell = 1;       //伤害数据 第一列

    private static final int damageCharaStartRow = 2;   //伤害数据页的角色数据 第一行

    private static final int damageCharaStartCell = 1;  //伤害数据页的角色数据 第一列

    public ExcelWriter() throws IOException {
        dataSheet = book.getSheet("角色信息");
        damageSheet = book.getSheet("满配伤害");
    }

    public void setName(String name) {
        this.exportPath = name + new SimpleDateFormat("MM_dd_HH_mm_ss").format(new Date()) + ".xlsx";
    }
    public void writeCharaData(List<Chara> chara) {
        IntStream.range(0, 5)
                .forEach(i -> writeCharaRow(charaStartRow + i, chara.get(i)));

        Row nameRow = getRow(damageSheet, damageCharaStartRow);
        Row damageRow = getRow(damageSheet, damageCharaStartRow+1);
        for(int i=0; i<5; i++) {
            nameRow.getCell(damageCharaStartCell+i*2).setCellValue(chara.get(i).getName());
            damageRow.getCell(damageCharaStartCell+i*2).setCellValue(chara.get(i).getBaseAttack());
        }
    }

    private void writeCharaRow(int rowNum, Chara chara) {
        Row row = getRow(dataSheet, rowNum);
//         dataSheet.getRow(rowNum);
        Cell nameCell = row.createCell(charaStartCell);
        Cell starCell = row.createCell(charaStartCell+1);
        Cell skillLevel = row.createCell(charaStartCell+2);
        Cell is6Cell = row.createCell(charaStartCell+3);
        Cell atkCell = row.createCell(charaStartCell+4);
        Cell hpCell = row.createCell(charaStartCell+5);
        Cell hintCell = row.createCell(charaStartCell+6);

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
        int size = excelDamageRecord.getDamagePairs().size();
        for(int i=0; i< size; i++){
            int rowNum = i/5 + damageStartRow;
            Row row = getRow(damageSheet, rowNum);
            Cell cell = row.createCell(damageStartCell+(i%5)*2);
            cell.setCellValue(excelDamageRecord.getDamagePairs().get(i).action());
            cell = row.createCell(damageStartCell+(i%5)*2+1);
            cell.setCellValue(excelDamageRecord.getDamagePairs().get(i).damage());
        }
    }

    private static Row getRow(Sheet sheet, int rowNum) {
        Row row = sheet.getRow(rowNum);
        if(row == null){
            return sheet.createRow(rowNum);
        }
        return row;
    }

    public void writeSkillLevelMatrix(Long[][] skillLevelMatrix) {
        final int startCell = 15, startRow = 54;
        for(int i=0;i<6;i++) {
            Row row = getRow(damageSheet, startRow+i);
            for(int j=0;j<4;j++) {
                Cell cell = row.createCell(startCell+j);
                cell.setCellValue(skillLevelMatrix[i][j]);
            }
        }
    }

    public void writeProficiencyMatrix(Long[][] skillLevelMatrix) {
        final int startCell = 15, startRow = 63;
        for(int i=0;i<6;i++) {
            Row row = getRow(damageSheet, startRow+i);
            for(int j=0;j<4;j++) {
                Cell cell = row.createCell(startCell+j);
                cell.setCellValue(skillLevelMatrix[i][j]);
            }
        }
    }

    public void writeStarList(Long[] starList) {
        final int startCell = 22, startRow = 54;
        for (int i = 0; i < 6; i++) {
            Row row = getRow(damageSheet, startRow + i);
            Cell cell = row.createCell(startCell);
            cell.setCellValue(starList[i]);
        }
    }

    public void writeContributeList(Long[] starList) {
        final int startCell = 22, startRow = 63;
        Row row = getRow(damageSheet, startRow);
        for (int i = 0; i < 5; i++) {
            Cell cell = row.createCell(startCell+i);
            cell.setCellValue(starList[i]);
        }
    }

    public void writeDamagePart(Long[] damagePart) {
        final int startCell = 15, startRow = 71;
        Row row = getRow(damageSheet, startRow);
        for (int i = 0; i < 2; i++) {
            Cell cell = row.createCell(startCell+i);
            cell.setCellValue(damagePart[i]);
        }
    }
}
