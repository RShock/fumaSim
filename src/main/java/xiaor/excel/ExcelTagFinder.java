package xiaor.excel;

import kotlin.Pair;
import org.apache.poi.ss.usermodel.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * 这个类一口气把excel的所有tag找出来
 * 稍微考虑了一点性能
 */
public class ExcelTagFinder {


    private final Map<String, Pair<Integer, Integer>> result;
    private final Sheet sheet;

    public ExcelTagFinder(Sheet sheet, Set<String> tags) {
        this.sheet = sheet;
        int lastRowNum = sheet.getLastRowNum();
        Map<String, Pair<Integer, Integer>> result = new HashMap<>();
        AtomicInteger findCount = new AtomicInteger();
        IntStream.range(0, lastRowNum).forEach(x -> {
            Row row = getRow(sheet, x);
            int lastCellNum = row.getLastCellNum();
            IntStream.range(0, lastCellNum).forEach(y -> {
                Cell cell = getCell(row, y);
                if(cell.getCellType() == CellType.STRING) {
                    String val = cell.getStringCellValue();
                    if (tags.contains(val)) {
                        result.put(val, new Pair<>(x, y));
                        findCount.getAndIncrement();
                    }
                }
            });
        });
        if (findCount.get() != tags.size()) {
            throw new RuntimeException("表里面没有找到所有tag: %s %s".formatted(tags, result));
        }
        this.result = result;
    }

    public Map<String, Pair<Integer, Integer>> getResult() {
        return result;
    }

    public void writeCell(String tag, int dx, int dy, String data) {
        var pos = result.get(tag);
        Row row = sheet.getRow(pos.getFirst() + dx);
        Cell cell = row.getCell(pos.getSecond() + dy);
        cell.setCellValue(data);
    }

    private static Row getRow(Sheet sheet, int rowNum) {
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            return sheet.createRow(rowNum);
        }
        return row;
    }

    public static Cell getCell(Row row, int cellNum) {
        Cell cell = row.getCell(cellNum);
        if (cell == null) {
            return row.createCell(cellNum);
        }
        return cell;
    }
}
