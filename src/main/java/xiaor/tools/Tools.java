package xiaor.tools;

import xiaor.GameBoard;
import xiaor.charas.ImportedChara;
import xiaor.excel.ExcelCharaProvider;
import xiaor.trigger.TriggerManager;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

public class Tools {



    public static int getNewID() {
        return TriggerManager.getInstance().getIDGen();
    }

    public static final String RESET = "\033[0m";

    private static final boolean SHOULD_LOG = !"false".equals(System.getProperty("showLog"));

    public static List<ImportedChara> initMaxChara(List<String> charaNames) {
       var charas = charaNames.stream().map(ExcelCharaProvider::getCharaByName)
                .peek(ImportedChara::maxData)
                .peek(chara -> GameBoard.getInstance().addOurChara(chara))
                .collect(toList());
        charas.get(0).setLeader(true);
        return charas;
    }


    public enum LogColor {
        RED, GREEN, YELLOW, BLUE, PURPLE, CYAN, WHITE, GREY
    }

    public static void log(LogColor color, String str) {
        if (!SHOULD_LOG) return;
        String ansiColor = "";
        switch (color) {
            case WHITE -> ansiColor = "\033[0;30m";   // WHITE
            case RED -> ansiColor = "\033[0;31m";     // RED
            case GREEN -> ansiColor = "\033[0;32m";   // GREEN
            case YELLOW -> ansiColor = "\033[0;33m";  // YELLOW
            case BLUE -> ansiColor = "\033[0;34m";    // BLUE
            case PURPLE -> ansiColor = "\033[0;35m";  // PURPLE
            case CYAN -> ansiColor = "\033[0;36m";    // CYAN
            case GREY -> ansiColor = "\033[0;37m";   // GREY
        }   // Regular Colors

        System.out.println(ansiColor + str + RESET);
    }

    public static void log(String str) {
        if (!SHOULD_LOG) return;
        System.out.println(str);
    }

    public static Matcher find(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        if(!matcher.find()){
            throw new RuntimeException("mismatch");
        }
        return matcher;
    }
}
