package basic_type;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by SunnyDay 2023/10/08 21:07:01
 **/
public class GenericTest1 {
    public static void main(String[] args) {

    }
}

interface Collection<E>{
    void addAll(Collection<? extends E> items);

}

class MyList<E> implements Collection<E>{

    void copyAll(Collection<Object> to, Collection<String> from) {
        to.addAll(from);
    }

    @Override
    public void addAll(Collection<? extends E> items) {

    }
}
