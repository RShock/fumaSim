package xiaor.excel;


import com.poiji.bind.Poiji;
import com.poiji.option.PoijiOptions;
import xiaor.excel.vo.CharaExcelVo;
import xiaor.excel.vo.SkillExcelVo;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ExcelReader {
    private static final ExcelReader excelReader = new ExcelReader();

    public static ExcelReader getInstance() {
        return excelReader;
    }

    public List<CharaExcelVo> getCharaVos() {
        return charaVos;
    }

    private List<CharaExcelVo> charaVos;

    private ExcelReader() {
        initChara();
    }


    private void initChara() {
        File resourcePath = new File(URLDecoder.decode(Objects.requireNonNull(getClass().getResource("/")).getPath(), StandardCharsets.UTF_8));
        String excelPath = resourcePath.getParent() + "/classes/角色技能资料.xlsx";
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().build();
        charaVos = Poiji.fromExcel(new File(excelPath), CharaExcelVo.class, options);
        options = PoijiOptions.PoijiOptionsBuilder.settings().sheetIndex(1).build();
        List<SkillExcelVo> skillVos = Poiji.fromExcel(new File(excelPath), SkillExcelVo.class, options);

        charaVos.forEach(
                charaExcelVo -> {
                    List<SkillExcelVo> charaSkills = skillVos.stream().filter(skillExcelVo -> skillExcelVo.getSkillId() / 1000 == charaExcelVo.charaId)
                            .collect(Collectors.toList());
                    charaExcelVo.setSkillExcelVos(charaSkills);
                }
        );
    }

}
