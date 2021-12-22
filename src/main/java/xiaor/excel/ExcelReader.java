package xiaor.excel;


import com.poiji.bind.Poiji;
import com.poiji.option.PoijiOptions;
import xiaor.charas.ImportedChara;
import xiaor.excel.vo.CharaExcelVo;
import xiaor.excel.vo.SkillExcelVo;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import static xiaor.charas.ImportedChara.convertToChara;

public class ExcelReader {
    private static final ExcelReader excelReader = new ExcelReader();

    public static ExcelReader getInstance() {
        return excelReader;
    }

    private List<ImportedChara> charas;

    private ExcelReader() {
        charas = initCharas();
    }

    public static ImportedChara getChara(String charaName) {
        return getCharas().stream().filter(chara -> chara.getName().equals(charaName)).findFirst().
                orElseThrow(()->new RuntimeException("找不到指定角色："+ charaName));
    }

    private List<ImportedChara> initCharas() {
        File resourcePath = new File(URLDecoder.decode(getClass().getResource("/").getPath(), StandardCharsets.UTF_8));
        String excelPath = resourcePath.getParent() + "/classes/角色技能资料.xlsx";
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().build();
        List<CharaExcelVo> charaVos = Poiji.fromExcel(new File(excelPath), CharaExcelVo.class, options);
        options = PoijiOptions.PoijiOptionsBuilder.settings().sheetIndex(1).build();
        List<SkillExcelVo> skillVos = Poiji.fromExcel(new File(excelPath), SkillExcelVo.class, options);

        return parseChara(charaVos, skillVos);
    }

    private List<ImportedChara> parseChara(List<CharaExcelVo> charaVos, List<SkillExcelVo> skillVos) {
        return charaVos.stream().map(vo -> convertToChara(vo, skillVos)).collect(Collectors.toList());
    }

    public static List<ImportedChara> getCharas() {
        return getInstance().charas;
    }
}
