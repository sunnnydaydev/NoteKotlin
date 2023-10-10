package basic_type;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Create by SunnyDay 2023/10/08 21:07:01
 **/
class Person{}
class Father extends Person{}
class Child extends Father{}

public class GenericTest1 {
    public static void main(String[] args) {
        Collection<Person> myList1 = new MyList<>();

        Collection<Father> myList2 = new MyList<>();

       // myList1.addAll(myList2); // 编译异常
    }
}

interface Collection<E>{
    void addAll(Collection<? super E> items);
}

class MyList<E> implements Collection<E>{
    @Override
    public void addAll(Collection<? super E> items) {
    }
    void copyAll(Collection<Object> to, Collection<String> from) {
        from.addAll(to);
    }
}
