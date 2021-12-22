package xiaor.charas;

import xiaor.excel.ExcelReader;
import xiaor.excel.vo.CharaExcelVo;
import xiaor.excel.vo.SkillExcelVo;

import java.util.List;
import java.util.stream.Collectors;

public class ImportedChara extends Chara implements Cloneable{
    private List<SkillExcelVo> uninitedSkills;  //skill需要等到角色正式使用时再初始化
    @Override
    public void initSkills() {

    }

    public List<SkillExcelVo> getUninitedSkills() {
        return uninitedSkills;
    }

    public void setUninitedSkills(List<SkillExcelVo> uninitedSkills) {
        this.uninitedSkills = uninitedSkills;
    }

    public static ImportedChara initChara(String initWords) throws CloneNotSupportedException {
        String[] split = initWords.split("\\s+");
        String charaName = split[0];
        ImportedChara chara = ExcelReader.getChara(charaName).clone();
        chara.initSkills();
        return chara;
    }

    public static ImportedChara convertToChara(CharaExcelVo charaVo, List<SkillExcelVo> skillVos) {
        ImportedChara importedChara = new ImportedChara();
        importedChara.setCharaId(charaVo.charaId);
        importedChara.setAttack(charaVo.attack);
        importedChara.setElement(Enum.valueOf(Element.class, charaVo.charaElement));
        importedChara.setName(charaVo.charaName);
        importedChara.setRole(Enum.valueOf(Role.class, charaVo.charaRole));
        importedChara.setUninitedSkills(
                skillVos.stream().filter(vo -> vo.getOwner() == charaVo.charaId).collect(Collectors.toList()));
        return importedChara;
    }

    @Override
    public ImportedChara clone() throws CloneNotSupportedException {
        ImportedChara chara = (ImportedChara) super.clone();
        return chara;
    }
}
