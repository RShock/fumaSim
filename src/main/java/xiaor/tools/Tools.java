package xiaor.tools;

public class Tools {
    public static String toPercent(double multi) {
        return "" + multi * 100 + '%';
    }

    public static int getNewID() {
        return TriggerManager.getInstance().getIDGen();
    }

    public static final String RESET = "\033[0m";


    public enum LogColor {
        RESET, RED, GREEN, YELLOW, BLUE, PURPLE, CYAN, WHITE, GREY
    }
    public static void log(LogColor color,String str) {
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
}