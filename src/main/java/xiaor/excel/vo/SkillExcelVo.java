package xiaor.excel.vo;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;
import lombok.Getter;
import xiaor.skillbuilder.SkillType;
import xiaor.trigger.TriggerEnum;

import java.awt.*;

@Getter
public class SkillExcelVo {
    @ExcelRow
    private int rowIndex;

    @ExcelCell(0)
    private String owner;   //技能所属者

    @ExcelCell(1)
    private int skillId;

    @ExcelCell(2)
    private String skillType;

    @ExcelCell(3)
    private String skillType2;

    @ExcelCell(4)
    private String description;

    @ExcelCell(5)
    private String trigger;

    @ExcelCell(6)
    private String effect;

    public SkillType getSkillType() {
        return switch (skillType) {
            case "必杀技" -> Enum.valueOf(SkillType.class, skillType + skillType2);
            case "被动" -> Enum.valueOf(SkillType.class, skillType2 + skillType);
            default -> Enum.valueOf(SkillType.class, skillType);
        };
    }

    public TriggerEnum getTrigger() {
        return Enum.valueOf(TriggerEnum.class, trigger);
    }

    public String toString() {
        return "[%s %s %s]".formatted(owner, skillType, skillType2 == null? "/" : skillType2);
    }
}
