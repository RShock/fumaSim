package xiaor.excel;

import xiaor.charas.ImportedChara;


public class ExcelCharaProvider {
    private static final ExcelCharaProvider charaProvider = new ExcelCharaProvider();

    public static ExcelCharaProvider getInstance() {
        return charaProvider;
    }

    private ExcelCharaProvider() {
    }

    public static ImportedChara getChara(int charaId) {
        return ExcelReader.getCharas().stream().filter(importedChara -> charaId == importedChara.getCharaId()).findFirst()
                .orElseThrow(() -> new RuntimeException("没找到指定ID的角色：" + charaId));
    }
}
