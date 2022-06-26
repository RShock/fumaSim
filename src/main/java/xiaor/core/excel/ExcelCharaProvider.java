package xiaor.core.excel;

import xiaor.core.charas.ImportedChara;
import xiaor.core.excel.vo.CharaExcelVo;
import xiaor.core.exception.CharaNotFound;


public class ExcelCharaProvider {
    private ExcelCharaProvider() {
    }

    public static ImportedChara getCharaByName(String charaName) {
        CharaExcelVo targetChara = ExcelReader.getInstance().getCharaVos().stream()
                .filter(chara -> chara.getCharaName().contains(charaName)).findFirst()
                .orElseThrow(() -> new CharaNotFound("找不到指定角色：" + charaName));
        return ImportedChara.convertToChara(targetChara);
    }

    public static int searchIdByCharaName(String name) {
        return ExcelReader.getInstance().getCharaVos().stream().filter(chara -> chara.getCharaName().equals(name))
                .findFirst().orElseThrow(() -> new CharaNotFound("未找到对应名称")).charaId;
    }
}
