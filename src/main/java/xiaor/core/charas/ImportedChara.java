package xiaor.core.charas;

import xiaor.core.excel.ExcelCharaProvider;
import xiaor.core.excel.vo.CharaExcelVo;
import xiaor.core.excel.vo.SkillExcelVo;
import xiaor.core.logger.LogType;
import xiaor.core.logger.Logger;
import xiaor.core.skillbuilder.SkillType;
import xiaor.core.Tools;

import java.util.List;
import java.util.regex.Matcher;

import static xiaor.core.excel.vo.CharaExcelVo.checkMatch;

public class ImportedChara extends Chara {
    private List<SkillExcelVo> uninitedSkills;  //skill需要等到角色正式使用时再初始化

    @Override
    public void initSkills() {
        if (isDisabled()) return;
        uninitedSkills.forEach(vo -> checkMatch(uninitedSkills, vo.getDescription(), vo.getEffect()));
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
        if (initWords.contains("满配")) {
            chara.maxData();
        }
        baseInit(chara, initWords);
        return chara;
    }

    public static ImportedChara convertToChara(CharaExcelVo charaVo) {
        ImportedChara importedChara = new ImportedChara();
        importedChara.setCharaId(charaVo.charaId);
        importedChara.setOriginAtk(charaVo.attack);
        importedChara.setOriginLife((int) charaVo.life);
        importedChara.setElement(Enum.valueOf(Element.class, charaVo.charaElement));
        importedChara.setName(charaVo.charaName);
        importedChara.setRole(Enum.valueOf(Role.class, charaVo.charaRole));
        importedChara.setUninitiatedSkills(charaVo.getSkillExcelVos());    //初始化技能需要等到所有角色设置好
        importedChara.setNickName(charaVo.getNickName());
        importedChara.setRare(Enum.valueOf(Rare.class, charaVo.rare));
        //尝试从必杀技栏找到CD
        List<Short> cds = charaVo.getSkillExcelVos().stream().filter(SkillExcelVo::is必杀).map(SkillExcelVo::getEffect)
                .map(ImportedChara::parseTurn).toList();
        importedChara.setCds(cds);
        return importedChara;
    }

    private static short parseTurn(String effect) {
        try {
            Matcher matcher = Tools.find(effect, ".*(CD(?<CD>\\d+)).*");
            String cd = matcher.group("CD");
            return Short.parseShort(cd);
        } catch (RuntimeException e) {
            Logger.INSTANCE.log(LogType.其他, "警告，该技能的CD未编写于excel内：%s".formatted(effect));
            return 0;
        }
    }

    /**
     * 这个方法只能在刚导入后调用（以防止攻击力不正确），将数据设为满配
     */
    public void maxData() {
        baseAttack = magicConvert(baseAttack, rare);
        baseLife = magicConvert(baseLife, rare);
        star = 5;
        potential = 12;
        skillLevel = 5;
    }

    public void to4星1绊Data() {
        baseAttack = magicConvert(baseAttack, rare);
        baseLife = magicConvert(baseLife, rare);
        star = 4;
        potential = 12;
        skillLevel = 1;
    }

    //把图鉴上的攻击/生命转化为满配的实际数值
    private int magicConvert(double data, Rare rare) {
        double pow = Math.pow(1.1, 59);
        double starMulti = switch (rare) {
            case SSR -> 10.0 / 8;
            case SR -> 10.0 / 7;
            case R -> 10.0 / 6;
            case N -> 10.0 / 5;
        };
        return (int) (data * pow * 2.6 * starMulti);
    }
}
