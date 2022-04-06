package xiaor.charas;

import xiaor.excel.ExcelCharaProvider;
import xiaor.excel.ExcelReader;
import xiaor.excel.vo.CharaExcelVo;
import xiaor.excel.vo.SkillExcelVo;

import java.util.List;

public class ImportedChara extends Chara{
    private List<SkillExcelVo> uninitedSkills;  //skill需要等到角色正式使用时再初始化
    @Override
    public void initSkills() {
        uninitedSkills.forEach(
                skill -> SkillParser.addSkill(this, skill)
        );
    }

    public List<SkillExcelVo> getUninitedSkills() {
        return uninitedSkills;
    }

    public void setUninitedSkills(List<SkillExcelVo> uninitedSkills) {
        this.uninitedSkills = uninitedSkills;
    }

    public static ImportedChara initChara(String initWords) {
        String[] split = initWords.split("\\s+");
        String charaName = split[0] + " " + split[1];
        ImportedChara chara = ExcelCharaProvider.getCharaByName(charaName);
        baseInit(chara, initWords);
        return chara;
    }

    public static ImportedChara convertToChara(CharaExcelVo charaVo) {
        ImportedChara importedChara = new ImportedChara();
        importedChara.setCharaId(charaVo.charaId);
        importedChara.setAttack(charaVo.attack);
        importedChara.setElement(Enum.valueOf(Element.class, charaVo.charaElement));
        importedChara.setName(charaVo.charaName);
        importedChara.setRole(Enum.valueOf(Role.class, charaVo.charaRole));
        importedChara.setUninitedSkills(charaVo.getSkillExcelVos());    //初始化技能需要等到所有角色设置好
        return importedChara;
    }
}