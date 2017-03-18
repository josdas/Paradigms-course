import java.util.Random;

/**
 * Created by Stas on 17.03.2017.
 */
public class Main {
    public static void main(final String[] args) {
        LinkedQueue T = new LinkedQueue();
        //int n = Integer.parseInt(args[0]);
        int n = 10000;
        ArrayQueue queue = new ArrayQueue();
        LinkedQueue Lqueue = new LinkedQueue();
        int s = 0;
        for (int i = 0; i < n; i++) {
            Random random = new Random();
            int type = random.nextInt(5);
            if (s == 0) {
                type = 0;
            }
            int f = random.nextInt(10);
            if (type == 0) {
                s++;
                queue.enqueue(f);
                Lqueue.enqueue(f);
                System.out.println(f + " add");
            } else if(type == 1 && s > 0) {
                Object a = queue.dequeue();
                Object b = Lqueue.dequeue();

                System.out.println(a + " " + b);
                if (a != b) {
                    System.out.println("error");
                    assert false;
                } else {
                    //System.out.println("ok");
                }
                s--;
            } else if(type == 2) {
                queue.clear();
                Lqueue.clear();
                System.out.println("clear");
                s = 0;
            } else if(type == 3) {

            } else if(type == 4 && s > 0) {
            }
        }
    }
}
