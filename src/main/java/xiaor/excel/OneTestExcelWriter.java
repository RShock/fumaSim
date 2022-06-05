package xiaor.excel;

import kotlin.Pair;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import xiaor.charas.Chara;
import xiaor.tools.record.ExcelDamageRecord;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.IntStream;

import static xiaor.Common.getResourcePath;

/**
 * 把测试结果输出到excel里
 * 读取excel用的是另一个库，写入时因为全是方格操作不能用那个库
 */
public class OneTestExcelWriter {
    private final Map<String, Pair<Integer, Integer>> result;
    Sheet damageSheet;
    public final String excelPath = getResourcePath(this.getClass(),"单次测试输出表样板.xlsx");
    String exportPath = "output\\单测试"+ new SimpleDateFormat("MM月dd日HH点mm分ss秒").format(new Date()) + ".xlsx";
    Workbook book = new XSSFWorkbook(excelPath);

    private static int charaStartRow;       //角色数据 第一行
    private static int charaStartCell;      //角色数据 第一列

    private static int damageStartRow;        //伤害数据 第一行

    private static int damageStartCell;       //伤害数据 第一列

    private static int damageCharaStartRow;   //伤害数据页的角色数据 第一行

    private static int damageCharaStartCell;  //伤害数据页的角色数据 第一列

    public OneTestExcelWriter() throws IOException {
        damageSheet = book.getSheet("伤害");
        var result = new ExcelTagFinder(damageSheet,
                Set.of("{伤害表}", "{角色简表}", "{角色信息表}", "{行动轴}")).getResult();
        charaStartRow = result.get("{角色信息表}").getFirst();
        charaStartCell = result.get("{角色信息表}").getSecond();
        damageStartRow = result.get("{伤害表}").getFirst();
        damageStartCell = result.get("{伤害表}").getSecond();
        damageCharaStartRow = result.get("{角色简表}").getFirst();
        damageCharaStartCell = result.get("{角色简表}").getSecond();
        this.result = result;
    }

    public void setName(String name) {
        this.exportPath = name + new SimpleDateFormat("MM月dd日HH点mm分ss秒").format(new Date()) + ".xlsx";
    }

    public void writeCharaData(List<Chara> chara) {
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
