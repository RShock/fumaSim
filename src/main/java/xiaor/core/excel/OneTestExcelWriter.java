package xiaor.core.excel;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import static xiaor.core.Common.getResourcePath;

/**
 * 把测试结果输出到excel里
 * 读取excel用的是另一个库，写入时因为全是方格操作不能用那个库
 */
public class OneTestExcelWriter extends ExcelWriter{
    public final String excelPath = getResourcePath(this.getClass(),"单次测试输出表样板.xlsx");
    String exportPath = "output\\单测试"+ new SimpleDateFormat("MM月dd日HH点mm分ss秒").format(new Date()) + ".xlsx";
    Workbook book = new XSSFWorkbook(excelPath);


    public OneTestExcelWriter() throws IOException {
        damageSheet = book.getSheet("伤害");
        this.result = new ExcelTagFinder(damageSheet,
                Set.of("{伤害表}", "{角色简表}", "{角色信息表}", "{行动轴}")).getResult();
    }

    public void setName(String name) {
        this.exportPath = name + new SimpleDateFormat("MM月dd日HH点mm分ss秒").format(new Date()) + ".xlsx";
    }

    public void export() throws IOException {
        FileOutputStream fos = new FileOutputStream(exportPath);
        XSSFFormulaEvaluator.evaluateAllFormulaCells(book);
        book.write(fos);
    }

}
