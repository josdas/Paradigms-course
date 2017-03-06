
import  java.util.Random;
public class Test {    
    public static void main(final String[] args) {
    	int n = Integer.parseInt(args[0]);
    	ArrayQueue queue = new ArrayQueue();
    	int s = 0;             
    	ArrayQueueADT queueADT = new ArrayQueueADT();
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
    			ArrayQueueModule.enqueue(f);
    			ArrayQueueADT.enqueue(queueADT, f);
    		} else if(type == 1 && s > 0) {
    			Object a = queue.dequeue();
    			Object b = ArrayQueueModule.dequeue();
    			Object c = ArrayQueueADT.dequeue(queueADT);
    			if (a != b || a != c) {
    				System.out.println("error");
    			} else {
    				//System.out.println("ok");
    			}
    			s--;
    		} else if(type == 2) {
    			queue.clear();
    			ArrayQueueModule.clear();
    			ArrayQueueADT.clear(queueADT);
    			s = 0;
            } else if(type == 3) {
    			s++;
    			queue.push(f);
    			ArrayQueueModule.push(f);
    			ArrayQueueADT.push(queueADT, f);	
            } else if(type == 4 && s > 0) { 
                Object a = queue.remove();
    			Object b = ArrayQueueModule.remove();
    			Object c = ArrayQueueADT.remove(queueADT);
    			if (a != b || a != c) {
    				System.out.println("error");
    			} else {
    				//System.out.println("ok");
    			}	
    			s--;
    		}
    	}
    }
}