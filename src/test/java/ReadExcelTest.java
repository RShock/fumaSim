import com.poiji.bind.Poiji;
import com.poiji.option.PoijiOptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xiaor.excel.vo.CharaExcelVo;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ReadExcelTest {
    @Test
    public void should_read_excel() {
        File resourcePath = new File(URLDecoder.decode(getClass().getResource("/").getPath(), StandardCharsets.UTF_8));
        String excelPath = resourcePath.getParent() + "/classes/角色技能资料.xlsx";
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().build();
        List<CharaExcelVo> charas = Poiji.fromExcel(new File(excelPath), CharaExcelVo.class, options);

        Assertions.assertEquals(5, charas.size());
    }
}
