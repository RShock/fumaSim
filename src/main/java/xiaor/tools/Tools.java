package xiaor.tools;

import xiaor.GameBoard;
import xiaor.charas.ImportedChara;
import xiaor.excel.ExcelCharaProvider;
import xiaor.trigger.TriggerManager;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

public class Tools {

    public static int getNewID() {
        return TriggerManager.getInstance().getIDGen();
    }

    public static List<ImportedChara> initMaxChara(List<String> charaNames) {
        var charas = charaNames.stream().map(ExcelCharaProvider::getCharaByName)
                .peek(ImportedChara::maxData)
                .peek(chara -> GameBoard.getInstance().addOurChara(chara))
                .collect(toList());
        charas.get(0).setLeader(true);
        return charas;
    }

    public static Matcher find(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        if (!matcher.find()) {
            throw new RuntimeException("mismatch text: %s %s".formatted(text, regex));
        }
        return matcher;
    }

    public static String readFileAsString(String filePath) {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(filePath + " 不存在", e);
        }
        String line;
        StringBuilder buffer = new StringBuilder();
        while (true) {
            try {
                if ((line = br.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            buffer.append(line);
        }
        return buffer.toString();
    }

    public static void writeToFile(String filepath, String content) {
        if (!new File("output").exists()) {
            new File("output").mkdir();
        }
        File f = new File(filepath);//新建一个文件对象，如果不存在则创建一个该文件
        FileWriter fw;
        try {
            fw = new FileWriter(f);
            fw.write(content);
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static final Pattern pattern = Pattern.compile("([1-9]\\d*\\.?\\d*)|(0\\.\\d*[1-9])");

    public static Set<String> findAllNum(String s) {
        Set<String> nums = new HashSet<>();
        if (s == null || s.isEmpty()) return nums;
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) nums.add(matcher.group(0));
        return nums;
    }
}
