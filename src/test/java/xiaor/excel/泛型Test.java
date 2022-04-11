package xiaor.excel;

public class 泛型Test {
    private class A {
        public int a;
    }

    private class B<T> extends A{
        public int b;
        private T t;

        public void setT(T t) {
            this.t = t;
        }

        public T getT(T t) {
            return t;
        }
    }

//
//    private doSth(T<T extends A>) {
//
//    }
}
