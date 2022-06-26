import com.poiji.bind.Poiji;
import com.poiji.option.PoijiOptions;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xiaor.core.excel.vo.CharaExcelVo;
import xiaor.core.excel.vo.SkillExcelVo;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static xiaor.core.excel.ExcelReader.getInstance;
import static xiaor.core.excel.ExcelReader.getXlsxPictures;

public class ReadExcelTest {
    @Test
    public void should_read_chara_excel() {
        String excelPath = getInstance().excelPath;

        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().build();
        List<CharaExcelVo> charas = Poiji.fromExcel(new File(excelPath), CharaExcelVo.class, options);
    }

    @Test
    public void should_read_skill_excel() {
        String excelPath = getInstance().excelPath;
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().sheetIndex(1).build();
        List<SkillExcelVo> charas = Poiji.fromExcel(new File(excelPath), SkillExcelVo.class, options);
    }

    @Test
    public void should_read_skill_img() {
        String excelPath = getInstance().excelPath;

        try {
            Workbook book = new XSSFWorkbook(excelPath);
            Sheet userDataSheet = book.getSheet("Sheet1");
            var picMap = getXlsxPictures((XSSFSheet) userDataSheet);
            Assertions.assertNotNull(picMap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
