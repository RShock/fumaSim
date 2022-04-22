package xiaor.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import xiaor.charas.Chara;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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
    Sheet indicatorSheet;
    File resourcePath = new File(URLDecoder.decode(Objects.requireNonNull(getClass().getResource("/")).getPath(), StandardCharsets.UTF_8));
    String excelPath = resourcePath.getParent() + "/classes/全方位测试输出表样板.xlsx";
    String exportPath = "output.xlsx";
    Workbook book = new XSSFWorkbook(excelPath);

    private static int charaStartRow = 2;       //角色数据 第一行
    private static int charaStartCell = 1;      //角色数据 第一列

    public ExcelWriter() throws IOException {
        dataSheet = book.getSheet("角色信息");
        damageSheet = book.getSheet("满配伤害");
        indicatorSheet = book.getSheet("指标");
    }

    public void setName(String name) {
        this.exportPath = name + ".xlsx";
    }
    public void writeCharaData(List<Chara> chara) {
        IntStream.range(0, 4)
                .forEach(i -> writeCharaRow(charaStartRow + i, chara.get(i)));
    }

    private void writeCharaRow(int rowNum, Chara chara) {
        Row row = dataSheet.getRow(rowNum);
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
        atkCell.setCellValue(chara.getBaseAtk());
        hpCell.setCellValue(chara.getBaseLife());
        hintCell.setCellValue(chara.getHint());
    }

    public void export() throws IOException {
        FileOutputStream fos = new FileOutputStream(exportPath);
        book.write(fos);
        book.close();
    }
}
