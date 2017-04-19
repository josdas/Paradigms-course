package sum;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class SumLongTest {
    private static final SumChecker CHECKER = new SumChecker("SumLong");

    public static void main(final String[] args) {
        CHECKER.test(1, "1");
        CHECKER.test(6, "1", "2", "3");
        CHECKER.test(1, " 1");
        CHECKER.test(1, "1 ");
        CHECKER.test(1, "\t1\t");
        CHECKER.test(12345, "\t12345\t");
        CHECKER.test(1368, " 123 456 789 ");
        CHECKER.test(60, "010", "020", "030");
        CHECKER.test(-1, "-1");
        CHECKER.test(-6, "-1", "-2", "-3");
        CHECKER.test(-12345, "\t-12345\t");
        CHECKER.test(-1368, " -123 -456 -789 ");
        CHECKER.test(1, "+1");
        CHECKER.test(6, "+1", "+2", "+3");
        CHECKER.test(12345, "\t+12345\t");
        CHECKER.test(1368, " +123 +456 +789 ");
        CHECKER.test(0);
        CHECKER.test(0, " ");
        randomTest(10, 100);
        randomTest(10, Long.MAX_VALUE);
        randomTest(100, Long.MAX_VALUE);
        CHECKER.printStatus();
    }

    private static void randomTest(final int numbers, final long max) {
        long sum = 0;
        final List<String> args = new ArrayList<>();
        for (int i = 0; i < numbers; i++) {
            final long v = CHECKER.random.nextLong() % max;
            args.add(String.valueOf(v));
            sum += v;
        }
        CHECKER.testRandom(sum, args);
    }
}
