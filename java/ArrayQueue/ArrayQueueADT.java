public class ArrayQueueADT {
	// Pre для любой функции: deq - дек
    // Inv: (n >= 0) && (a[i] != null for i = 1..n - 1)
    private Object[] elements = new Object[10];
    private int size = 0;
    private int left = 0;
    private int right = 0;

   	// Pre: 
    private static int getCapacity(ArrayQueueADT deq) {
  		return deq.elements.length;
    }
    // Post: res = |elem|

    // Pre: (|elem| != 0) && (0 <= x < |elem|)
    private static int dec(ArrayQueueADT deq, int x) {
        if (x == 0) {
            return getCapacity(deq) - 1;
        } else {
            return x - 1;
        }
    }
    // Post: (res == x - 1 && x > 0) || (res == |elem| && x == 0)
    

    // Pre: (|elem| != 0) && (0 <= x < |elem|)
    private static int inc(ArrayQueueADT deq, int x) {
    	if(x + 1 == getCapacity(deq)){
    		return 0;
    	} else {
        	return x + 1;
        }
    }
    // res == (x + 1) % |elem|
                         

    //Pre: sz >= 0
    private static void ensureCapacity(ArrayQueueADT deq, int sz) {
    	if((getCapacity(deq) <= sz) || (getCapacity(deq) > sz * 4)){
    		Object[] temp = new Object[sz * 2 + 1];
    		int last;
    		if (deq.left <= deq.right) {
    			last = deq.right - deq.left;
    			System.arraycopy(deq.elements, deq.left, temp, 0, deq.right - deq.left);
    		} else {
    		    last = getCapacity(deq) - deq.left + deq.right;
    			System.arraycopy(deq.elements, deq.left, temp, 0, getCapacity(deq) - deq.left);
    			System.arraycopy(deq.elements, 0, temp, getCapacity(deq) - deq.left, deq.right);
    		}
    		deq.elements = temp;
    		deq.left = 0;
    		deq.right = last;	
    	}	
    }
    // Post: sz < |elem'| <= sz * 4

    // Pre: elem != null
    public static void enqueue(ArrayQueueADT deq, Object elem) {
    	assert elem != null;
    	deq.size++;
    	ensureCapacity(deq, deq.size);
    	deq.elements[deq.right] = elem;
    	deq.right = inc(deq, deq.right);
    }
    // (n' == n + 1) && (a'[i] == a[i] for i = 0..n - 1) && (a'[n] == elem)

    // Pre: elem != null
    public static void push(ArrayQueueADT deq, Object elem) {
    	assert elem != null;
    	deq.size++;
    	ensureCapacity(deq, deq.size);
    	deq.left = dec(deq, deq.left);
    	deq.elements[deq.left] = elem;
    }
    // (n' == n + 1) && (a'[i + 1] == a[i] for i = 0..n - 1) && (a'[0] == elem)

    // Pre: n > 0
    public static Object element(ArrayQueueADT deq) {
        assert deq.size > 0;
        return deq.elements[deq.left];
    }
    // Post: res == a[0]

    // Pre: n > 0
    public static Object peek(ArrayQueueADT deq) {
        assert deq.size > 0;
        return deq.elements[dec(deq, deq.right)];
    }
    // Post: res == a[n - 1]

    // Pre: n > 0
    public static Object dequeue(ArrayQueueADT deq) {
    	assert deq.size > 0;
    	Object temp = element(deq);
    	popFront(deq);
    	return temp;
    }
    // Post: (n' == n - 1) && (a'[i - 1] == a[i] for i = 1...n - 1) && (res == a[0])
    
    // Pre: n > 0 
    private static void popBack(ArrayQueueADT deq) {
    	assert deq.size > 0;
    	deq.right = dec(deq, deq.right);
    	deq.elements[deq.right] = null;
		deq.size--;	
    }
    // Post: (n' == n - 1) && (a'[i] == a[i] for i = 0...n - 2)

    // Pre: n > 0 
    private static void popFront(ArrayQueueADT deq) {
    	assert deq.size > 0;
    	deq.elements[deq.left] = null;
    	deq.left = inc(deq, deq.left);   
		deq.size--;	
    }
    // Post: (n' == n - 1) && (a'[i - 1] == a[i] for i = 1...n - 1)                                                           

    // Pre: n > 0
    public static Object remove(ArrayQueueADT deq) {
    	assert deq.size > 0;
    	Object temp = peek(deq);
    	popBack(deq);
    	return temp;
    }
    // Post: (n' == n - 1) && (a'[i] == a[i] for i = 0...n - 2) && (res == a[n - 1])

    // Pre:
    public static int size(ArrayQueueADT deq) {
        return deq.size;
    }
    // Post: res == n

    // Pre:
    public static boolean isEmpty(ArrayQueueADT deq) {
        return size(deq) == 0;
    }
    // Post: res == (n == 0)

    // Pre:
    public static void clear(ArrayQueueADT deq) {
        ensureCapacity(deq, 10);
        deq.size = 0;
        deq.left = 0;
        deq.right = 0;
    }
    // Post: n == 0
}


