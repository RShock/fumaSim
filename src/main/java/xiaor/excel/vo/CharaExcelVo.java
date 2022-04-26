package xiaor.excel.vo;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;
import lombok.Data;

import java.util.List;

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

    private List<SkillExcelVo> skillExcelVos;
}
