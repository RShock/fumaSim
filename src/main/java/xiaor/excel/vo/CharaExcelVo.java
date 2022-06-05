package xiaor.excel.vo;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;
import lombok.Data;
import xiaor.charas.Chara;
import xiaor.tools.Tools;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

@Data
public class CharaExcelVo {
    @ExcelRow
    public int rowIndex;

    @ExcelCell(0)
    public String charaName;

    @ExcelCell(1)
    private String nickName;

    @ExcelCell(2)
    public int charaId;

    @ExcelCell(3)
    public String charaElement;

    @ExcelCell(4)
    public String charaRole;

    @ExcelCell(5)
    public String charaType;   //限定or常驻

    @ExcelCell(6)
    public String rare;

    @ExcelCell(7)
    public double attack;

    @ExcelCell(8)
    public double life;

    public void setSkillExcelVos(List<SkillExcelVo> skillExcelVos) {
        this.skillExcelVos = skillExcelVos;
    }

    /**
     * description中出现的数据，理应在effect中再出现一次
     * @param description
     * @param effect
     */
    public static void checkMatch(List<SkillExcelVo> vos, String description, String effect) {
        if(effect.contains("没做"))return;

        var allNum = Tools.findAllNum(effect);
        if(effect.contains("自身获得")){
            Matcher matcher = Tools.find(effect, ".*自身获得技能(?<skillId>\\d+).*");
            int skillId = Integer.parseInt(matcher.group("skillId"));
            effect = vos.stream().filter(skillExcelVo -> skillExcelVo.getSkillId() == skillId).findFirst().orElseThrow(
                    () -> new RuntimeException("skillId不存在" + skillId)).getEffect();
        }
        allNum.addAll(Tools.findAllNum(effect));
        allNum.add("50");   //50回合可以省略
        IntStream.range(1,5).mapToObj(Objects::toString).forEach(allNum::add);  //1-5太小了也省略吧
        allNum.add("1");    //第一回合也可以省略
        if(!allNum.containsAll(Tools.findAllNum(description))){
            System.out.println("warning: 描述中出现了效果里没有的数字："+description+ "  "+effect);
        }
    }

    private List<SkillExcelVo> skillExcelVos;
}
