import com.poiji.bind.Poiji;
import com.poiji.option.PoijiOptions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import xiaor.charas.Chara;
import xiaor.charas.ImportedChara;
import xiaor.excel.ExcelWriter;
import xiaor.excel.vo.CharaExcelVo;
import xiaor.excel.vo.SkillExcelVo;
import xiaor.tools.record.DamageRecord;
import xiaor.tools.record.DamageRecorder;
import xiaor.tools.record.ExcelDamageRecord;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class WriteExcelTest {
    @Test
    @Disabled
    public void should_write_chara_excel() throws IOException {
        ExcelWriter excelWriter = new ExcelWriter();
        Chara 小精灵王 = ImportedChara.initChara("机灵古怪_塞露西亚 攻击力505739 羁绊2 星3 潜力6 队长 备注:满配");
        Chara 露露 = ImportedChara.initChara("法斯公主_露露 攻击力675452 星5 绊5 潜6");
        Chara 精灵王 = ImportedChara.initChara("精灵王_塞露西亚 攻击力479798 星4 绊5 潜6");
        Chara 千鹤 = ImportedChara.initChara("复生公主_千鹤 攻击力361418 星3 绊1 潜6");
        Chara 沃沃 = ImportedChara.initChara("胆小纸袋狼_沃沃 攻击力204650 星3 绊2 潜5");
        excelWriter.writeCharaData(Arrays.asList(小精灵王, 露露, 精灵王, 千鹤, 沃沃));
//        excelWriter.writeBattleData(DamageRecorder.exportRecords());
        excelWriter.setName("小精灵王, 露露, 精灵王, 千鹤, 沃沃");
        excelWriter.export();
    }

    @Test
    @Disabled
    public void should_write_damage_excel() throws IOException {
        ExcelWriter excelWriter = new ExcelWriter();
        ExcelDamageRecord excelDamageRecord = new ExcelDamageRecord(Arrays.asList("1a", "2a", "3a", "4a", "5a", "1a", "2a"),
                IntStream.range(1000, 1007).boxed().toList());
        excelWriter.writeDamageData(excelDamageRecord);
        excelWriter.setName("小精灵王, 露露, 精灵王, 千鹤, 沃沃");
        excelWriter.export();
    }
}
