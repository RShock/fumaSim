package xiaor.excel;

import xiaor.charas.ImportedChara;
import xiaor.excel.vo.CharaExcelVo;


public class ExcelCharaProvider {
    private ExcelCharaProvider() {
    }

    public static ImportedChara getCharaByName(String charaName) {
        CharaExcelVo targetChara = ExcelReader.getInstance().getCharaVos().stream().filter(chara -> chara.getCharaName().equals(charaName)).findFirst().
                orElseThrow(() -> new RuntimeException("找不到指定角色：" + charaName));
        return ImportedChara.convertToChara(targetChara);
    }

    public static int searchIdByCharaName(String name) {
        return ExcelReader.getInstance().getCharaVos().stream().filter(chara -> chara.getCharaName().equals(name))
                .findFirst().orElseThrow(() -> new RuntimeException("未找到对应名称")).charaId;
    }
}
