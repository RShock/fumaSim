package xiaor.excel.vo;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import xiaor.charas.Element;
import xiaor.charas.ImportedChara;
import xiaor.charas.Role;

import java.util.List;

@Data
public class CharaExcelVo {
    @ExcelRow
    public int rowIndex;

    @ExcelCell(0)
    public String charaName;

    @ExcelCell(1)
    public int charaId;

    @ExcelCell(2)
    public String charaElement;

    @ExcelCell(3)
    public String charaRole;

    @ExcelCell(4)
    public String charaType;   //限定or常驻

    @ExcelCell(5)
    public String rare;

    @ExcelCell(6)
    public int attack;

    @ExcelCell(7)
    public int defense;

    private List<SkillExcelVo> skillExcelVos;
}
