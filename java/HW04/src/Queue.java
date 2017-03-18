/**
 * Created by Stas on 17.03.2017.
 */

import java.util.function.Predicate;
import java.util.function.Function;

public interface Queue {
    int size();
    void enqueue(Object e);
    Object element();
    Object dequeue();
    boolean isEmpty();
    void clear();
    Queue filter(Predicate<Object> p);
    Queue map(Function<Object, Object> f);
}
