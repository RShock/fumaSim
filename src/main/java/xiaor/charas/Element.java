package xiaor.charas;

public enum Element {
    水属性,
    风属性,
    光属性,
    暗属性,
    火属性;

    public static int counter(Element a, Element b) {
        if(a == 水属性 && b == 风属性)
            return -1;
        if(a == 火属性 && b == 水属性)
            return -1;
        if(a == 风属性 && b == 火属性)
            return -1;
        if(a == 风属性 && b == 水属性)
            return 1;
        if(a == 水属性 && b == 火属性)
            return 1;
        if(a == 火属性 && b == 风属性)
            return 1;
        if(a == 光属性 && b == 暗属性)
            return 1;
        if(a == 暗属性 && b == 光属性)
            return 1;
        return 0;
    }
}
