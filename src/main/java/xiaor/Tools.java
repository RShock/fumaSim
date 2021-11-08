package xiaor;

public class Tools {
    public static String toPercent(double multi) {
        return "" + multi * 100 + '%';
    }

    public static int getNewID() {
        return TriggerManager.getInstance().getIDGen();
    }

}
