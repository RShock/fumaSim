package xiaor.core.excel;


import com.poiji.bind.Poiji;
import com.poiji.option.PoijiOptions;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.xssf.usermodel.*;
import xiaor.core.excel.vo.CharaExcelVo;
import xiaor.core.excel.vo.SkillExcelVo;

import java.io.Console;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static xiaor.core.Common.getResourcePath;

public class ExcelReader {
    private static final ExcelReader excelReader = new ExcelReader();

    public static ExcelReader getInstance() {
        return excelReader;
    }

    public List<CharaExcelVo> getCharaVos() {
        return charaVos;
    }

    private List<CharaExcelVo> charaVos;

    private ExcelReader() {
        initChara();
    }

    public final String excelPath = getResourcePath(this.getClass(),"角色技能资料.xlsx");

    private void initChara() {

        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().build();
        charaVos = Poiji.fromExcel(new File(excelPath), CharaExcelVo.class, options);
        options = PoijiOptions.PoijiOptionsBuilder.settings().sheetIndex(1).build();
        var skillVos = Poiji.fromExcel(new File(excelPath), SkillExcelVo.class, options);
        checkSkillVos(skillVos);

        charaVos.forEach(
                charaExcelVo -> {
                    var charaSkills = skillVos.stream().filter(skillExcelVo -> skillExcelVo.getSkillId() / 1000 == charaExcelVo.charaId)
                            .collect(Collectors.toList());
                    charaExcelVo.setSkillExcelVos(charaSkills);
                }
        );
    }

     private static void checkSkillVos(List<SkillExcelVo> skillVos) {
            // 使用HashMap来收集skillId及其出现的次数
            Map<Integer, Long> skillIdCounts = skillVos.stream()
                    .filter(skillExcelVo -> skillExcelVo.getSkillId() != 0)
                    .collect(Collectors.groupingBy(SkillExcelVo::getSkillId, Collectors.counting()));

            // 找出重复的skillId
            List<Integer> duplicateSkillIds = skillIdCounts.entrySet().stream()
                    .filter(entry -> entry.getValue() > 1)
                    .map(Map.Entry::getKey)
                    .toList();

            // 如果存在重复的skillId，抛出异常并包含这些重复的skillId
            if (!duplicateSkillIds.isEmpty()) {
                throw new RuntimeException("存在相同的skillId: " + duplicateSkillIds);
            }
        }

    /**
     * 获取图片和位置 (xlsx)
     *
     * @param sheet /
     * @return /
     */
    public static Map<Integer, PictureData> getXlsxPictures(XSSFSheet sheet) {
        Map<Integer, PictureData> map = new HashMap<>(4);
        List<POIXMLDocumentPart> list = sheet.getRelations();
        for (POIXMLDocumentPart part : list) {
            if (part instanceof XSSFDrawing) {
                XSSFDrawing drawing = (XSSFDrawing) part;
                List<XSSFShape> shapes = drawing.getShapes();
                for (XSSFShape shape : shapes) {
                    XSSFPicture picture = (XSSFPicture) shape;
                    XSSFClientAnchor anchor = picture.getPreferredSize();
                    // 行号-列号
                    var marker = anchor.getFrom();
                    Integer key = marker.getRow();
                    map.put(key, picture.getPictureData());
                }
            }
        }
        return map;
    }

}
