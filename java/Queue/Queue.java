//package queue;
/**
 * Created by Stas on 17.03.2017.
 */

import java.util.function.Predicate;
import java.util.function.Function;

public interface Queue {
    //R - result
    //n - size

    //prec: true
    //post: R = n
    int size();

    // prec: elem != null
    // post: (n' == n + 1) && (a'[i] == a[i] for i = 0..n - 1) && (a'[n] == elem)
    void enqueue(Object e);

    // prec: n > 0
    // post: R = a[0]
    Object element();

    // prec: n > 0
    // post: (n' == n - 1) && (a'[i - 1] == a[i] for i = 1...n - 1) && (res == a[0])
    Object dequeue();

    //prec: true
    //post: R = (n == 0)
    boolean isEmpty();

    //prec: true
    //post: n` == 0
    void clear();

    //prec: p != null
    //post: size` <= size && (a` in a) && (p(a`) == true for i = 1..size`) && (size` is the maximum as possible) && R = a` && (a`[i] != null for i = 1..size`)
    // if (A in B), then exist a strictly sorted array I of size |A| && (1 <= I[i] <= |B| && A[i] == B[I[i]] for i = 1..|A|)
    Queue filter(Predicate<Object> p);

    //prec: f != null && (f(a[i]) != null for i = 1..size)
    //post: (a`[i] = f(a[i]) for i = 1..n) && size` == size && R = a`
    Queue map(Function<Object, Object> f);
}
