package xiaor.excel;

import kotlin.Pair;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import xiaor.charas.Chara;
import xiaor.tools.record.ExcelDamageRecord;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public abstract class ExcelWriter {
    protected Map<String, Pair<Integer, Integer>> result;

    Sheet damageSheet;

    protected void writeCharaRow(int rowNum, Chara chara) {
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

    protected static Row getRow(Sheet sheet, int rowNum) {
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            return sheet.createRow(rowNum);
        }
        return row;
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

}
