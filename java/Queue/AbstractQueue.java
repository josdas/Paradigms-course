//package queue;
//
/**
 * Created by Stas on 17.03.2017.
 */
import java.util.function.Function;
import java.util.function.Predicate;


abstract public class AbstractQueue implements Queue {
    protected int size;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    abstract protected void enqueueImpl(Object e);

    public void enqueue(Object elem) {
        assert elem != null;
        size++;
        enqueueImpl(elem);
    }

    abstract protected Object elementImpl();

    public Object element() {
        assert size > 0;
        return elementImpl();
    }

    abstract protected void popFront();

    public Object dequeue() {
        assert size > 0;
        Object temp = element();
        popFront();
        size--;
        return temp;
    }

    abstract public void clear();

    abstract protected AbstractQueue newQueue();

    abstract protected Object[] getAllElements();

    public AbstractQueue filter(Predicate<Object> predicate) {
        Object temp[] = getAllElements();
        AbstractQueue queue = newQueue();
        for(Object element : temp) {
            if(predicate.test(element)) {
                queue.enqueue(element);
            }
        }
        return queue;
    }

    public AbstractQueue map(Function<Object, Object> function) {
        Object temp[] = getAllElements();
        AbstractQueue queue = newQueue();
        for(Object element : temp) {
            queue.enqueue(function.apply(element));
        }
        return queue;
    }
}
