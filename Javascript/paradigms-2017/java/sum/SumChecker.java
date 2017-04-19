package sum;

import base.MainChecker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class SumChecker extends MainChecker {
    public SumChecker(final String className) {
        super(className);
    }

    public void testRandom(final long result, final List<String> args) {
        final List<String> spaced = args.stream()
                .map(value -> randomSpace() + value + randomSpace())
                .collect(Collectors.toList());
        final List<String> argsList = new ArrayList<>();
        for (final Iterator<String> i = spaced.listIterator(); i.hasNext(); ) {
            String next = i.next();
            while (i.hasNext() && random.nextBoolean()) {
                next += " " + randomSpace() + i.next();
            }
            argsList.add(next);
        }
        test(result, argsList.stream().toArray(String[]::new));
    }

    private String randomSpace() {
        return random.nextBoolean() ? "" : " \t\n\u000B\u2029\f".charAt(random.nextInt(6)) + randomSpace();
    }

    public void test(final long result, final String... input) {
        checkEquals(Collections.singletonList(Long.toString(result)), run(input));
    }
}
