package xiaor.core;

import xiaor.core.charas.ImportedChara;
import xiaor.core.excel.ExcelCharaProvider;
import xiaor.core.trigger.TriggerManager;

import java.io.*;
import java.util.*;
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

    public static List<ImportedChara> init4星1绊Chara(List<String> charaNames) {
        var charas = charaNames.stream().map(ExcelCharaProvider::getCharaByName)
                .peek(ImportedChara::to4星1绊Data)
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

    /**
     * 检查action内同一回合有没有一个角色行动了2次
     * 模式1： 5a 1a 2a 3a 4a 代表 第五个人先动 然后第一个...
     * 模式2： 上面代表第一个人第五个动，第二个人第一个动...
     * 对于模式2 该函数可以将其转换为模式1返回
     * 目前只支持5人轴使用
     * @param action
     * @param isType2
     * @return
     */
    public static String handleAction(String action, boolean isType2) {
        String[] split = action.split("\n");
        for (String s : split) {
            Pattern pattern = Pattern.compile(".*(\\d).*\\1.*");
            Matcher matcher = pattern.matcher(s);
            if(matcher.find()) {
                throw new RuntimeException("同一个回合一个角色只能行动一次！" + s);
            }
        }
        if(isType2) {
            StringBuilder newS=  new StringBuilder();
            for (String s : split) {
                var s2 = Arrays.stream(s.split("\\s+")).filter(_s -> !_s.isBlank()).toList();
                String[] tmp = new String[5];
                for (int i = 0; i < s2.size(); i++) {
                    int pos = Integer.parseInt(s2.get(i).substring(0, 1));
                    tmp[pos-1] = (i+1) + s2.get(i).substring(1);
                }
                for (int i = 0; i < s2.size(); i++) {
                    newS.append(tmp[i]);
                    newS.append(" ");
                }
                newS.append("\n");
            }
            return newS.toString();
        }
        return action;
    }
}
