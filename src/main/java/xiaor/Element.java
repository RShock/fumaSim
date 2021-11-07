package xiaor;

public enum Element {
    水属性,
    风属性;

    public static int counter(Element a, Element b) {
        if(a == 水属性 && b == 风属性)
            return -1;
        if(a == 风属性 && b == 水属性)
            return 1;
        return 0;
    }
}
