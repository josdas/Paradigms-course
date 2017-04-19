package sum;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class SumLongHexTest {
    private static final SumChecker CHECKER = new SumChecker("SumLongHex");

    public static void main(final String[] args) {
        CHECKER.test(1, "1");
        CHECKER.test(6, "1", "2", "3");
        CHECKER.test(1, " 1");
        CHECKER.test(1, "1 ");
        CHECKER.test(1, "\t1\t");
        CHECKER.test(12345, "\t12345\t");
        CHECKER.test(1368, " 123 456 789 ");
        CHECKER.test(60, "010", "020", "030");

        CHECKER.test(1, "0x1");
        CHECKER.test(0x1a, "0x1a");
        CHECKER.test(0xA2, "0xA2");
        CHECKER.test(62, " 0X0 0X1 0XF 0XF 0x0 0x1 0xF 0xf");
        CHECKER.test(0x1234567890abcdefL, "0x1234567890abcdef");
        CHECKER.test(0xCafeBabeDeadBeefL, "0xCafeBabeDeadBeef");

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
            args.add(CHECKER.random.nextBoolean() ? String.valueOf(v) : "0X" + Long.toHexString(v));
            sum += v;
        }
        CHECKER.testRandom(sum, args);
    }
}
