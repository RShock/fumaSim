package xiaor.excel.vo;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;

public class CharaExcelVo {
    @ExcelRow
    private int rowIndex;

    @ExcelCell(0)
    private String charaName;

    @ExcelCell(1)
    private String charaId;

    @ExcelCell(2)
    private String charaElement;

    @ExcelCell(3)
    private String charaType;

    @ExcelCell(4)
    private String charaType2;

    @ExcelCell(5)
    private String rare;

    @ExcelCell(6)
    private int attack;

    @ExcelCell(7)
    private int defense;
}
