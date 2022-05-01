package xiaor.charas;

import xiaor.excel.ExcelCharaProvider;
import xiaor.excel.vo.CharaExcelVo;
import xiaor.excel.vo.SkillExcelVo;
import xiaor.skillbuilder.SkillType;

import java.util.List;

public class ImportedChara extends Chara {
    private List<SkillExcelVo> uninitedSkills;  //skill需要等到角色正式使用时再初始化

    @Override
    public void initSkills() {
        if (isDisabled()) return;
        uninitedSkills.stream()
                //动态技能在运行中由其他技能注册，自身不注册
                .filter(skillExcelVo -> skillExcelVo.getSkillType() != SkillType.动态技能)
                .forEach(
                        skill -> SkillParser.addSkill(this, uninitedSkills, skill.getSkillId())
                );
    }

    public void setUninitiatedSkills(List<SkillExcelVo> uninitedSkills) {
        this.uninitedSkills = uninitedSkills;
    }

    public static ImportedChara initChara(String initWords) {
        String[] split = initWords.split("\\s+");
        String charaName = split[0].replace("_", " ");
        ImportedChara chara = ExcelCharaProvider.getCharaByName(charaName);
        baseInit(chara, initWords);
        return chara;
    }

    public static ImportedChara convertToChara(CharaExcelVo charaVo) {
        ImportedChara importedChara = new ImportedChara();
        importedChara.setCharaId(charaVo.charaId);
        importedChara.setOriginAtk(charaVo.attack);
        importedChara.setLife((int) charaVo.life);
        importedChara.setElement(Enum.valueOf(Element.class, charaVo.charaElement));
        importedChara.setName(charaVo.charaName);
        importedChara.setRole(Enum.valueOf(Role.class, charaVo.charaRole));
        importedChara.setUninitiatedSkills(charaVo.getSkillExcelVos());    //初始化技能需要等到所有角色设置好
        importedChara.setNickName(charaVo.getNickName());
        importedChara.setRare(Enum.valueOf(Rare.class, charaVo.rare));
        return importedChara;
    }

    /**
     * 这个方法只能在刚导入后调用（以防止攻击力不正确），将数据设为满配
     */
    public void maxData() {
        baseAttack = magicConvert(baseAttack, rare);
        life = magicConvert(baseLife, rare);
        star = 5;
        potential = 12;
        skillLevel = 5;
    }

    //把图鉴上的攻击/生命转化为满配的实际数值
    private int magicConvert(double data, Rare rare) {
        double pow = Math.pow(1.1, 59);
        double starMulti = switch (rare) {
            case SSR -> 10.0/8;
            case SR -> 10.0/7;
            case R -> 10.0/6;
            case N -> 10.0/5;
        };
        return (int) (data * pow * 2.6 * starMulti);
    }
}
