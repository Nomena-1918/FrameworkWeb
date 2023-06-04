package dataStructure;

public class Pair<T, P> {
    private T item1;
    private P item2;

    public Pair(T item1, P item2) {
        setItem1(item1);
        setItem2(item2);
    }

    public T getItem1() {
        return item1;
    }

    public void setItem1(T item1) {
        this.item1 = item1;
    }

    public P getItem2() {
        return item2;
    }


    public void setItem2(P item2) {
        this.item2 = item2;
    }


}

