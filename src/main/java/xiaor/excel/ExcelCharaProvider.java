package xiaor.excel;

import xiaor.charas.ImportedChara;
import xiaor.excel.vo.CharaExcelVo;


public class ExcelCharaProvider {
    private static final ExcelCharaProvider charaProvider = new ExcelCharaProvider();

    public static ExcelCharaProvider getInstance() {
        return charaProvider;
    }

    private ExcelCharaProvider() {
    }

    public static ImportedChara getCharaByName(String charaName) {
        CharaExcelVo targetChara = ExcelReader.getInstance().getCharaVos().stream().filter(chara -> chara.getCharaName().equals(charaName)).findFirst().
                orElseThrow(() -> new RuntimeException("找不到指定角色：" + charaName));
        return ImportedChara.convertToChara(targetChara);
    }

    public static ImportedChara getCharaById(int id) {
        CharaExcelVo targetChara = ExcelReader.getInstance().getCharaVos().stream().filter(chara -> chara.getCharaId() == id).findFirst().
                orElseThrow(() -> new RuntimeException("找不到指定角色：" + id));
        return ImportedChara.convertToChara(targetChara);
    }
}
