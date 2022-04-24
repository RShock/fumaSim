package xiaor.tools.record;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 一回合有5个伤害数据
 */
@Getter
@Setter
public class ExcelDamageRecord {
    private List<String> action;    //example:1a 2a 3a..
    private List<Integer> damage;    //example:114514
}
