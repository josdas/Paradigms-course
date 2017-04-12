public class ArrayQueueModule {
    // Inv: (n >= 0) && (a[i] != null for i = 1..n - 1)
    private static Object[] elements = new Object[10];
    private static int size = 0;
    private static int left = 0;
    private static int right = 0;

   	// Pre: 
    private static int getCapacity(){
  		return elements.length;
    }
    // Post: res = |elem|

    // Pre: (|elem| != 0) && (0 <= x < |elem|)
    private static int dec(int x) {
        if (x == 0) {
            return getCapacity() - 1;
        } else {
            return x - 1;
        }
    }
    // Post: (res == x - 1 && x > 0) || (res == |elem| && x == 0)
    

    // Pre: (|elem| != 0) && (0 <= x < |elem|)
    private static int inc(int x) {
    	if(x + 1 == getCapacity()){
    		return 0;
    	} else {
        	return x + 1;
        }
    }
    // res == (x + 1) % |elem|
                         

    //Pre: sz >= 0
    private static void ensureCapacity(int sz) {
    	if((getCapacity() <= sz) || (getCapacity() > sz * 4)){
    		Object[] temp = new Object[sz * 2 + 1];
    		int last;
    		if (left <= right) {
    			last = right - left;
    			System.arraycopy(elements, left, temp, 0, right - left);
    		} else {
    		    last = getCapacity() - left + right;
    			System.arraycopy(elements, left, temp, 0, getCapacity() - left);
    			System.arraycopy(elements, 0, temp, getCapacity() - left, right);
    		}
    		elements = temp;
    		left = 0;
    		right = last;	
    	}	
    }
    // Post: sz < |elem'| <= sz * 4 && a` = a

    // Pre: elem != null
    public static void enqueue(Object elem) {
    	assert elem != null;
    	size++;
    	ensureCapacity(size);
    	elements[right] = elem;
    	right = inc(right);
    }
    // (n' == n + 1) && (a'[i] == a[i] for i = 0..n - 1) && (a'[n] == elem)

    // Pre: elem != null
    public static void push(Object elem) {
    	assert elem != null;
    	size++;
    	ensureCapacity(size);
    	left = dec(left);
    	elements[left] = elem;
    }
    // (n' == n + 1) && (a'[i + 1] == a[i] for i = 0..n - 1) && (a'[0] == elem)

    // Pre: n > 0
    public static Object element() {
        assert size > 0;
        return elements[left];
    }
    // Post: res == a[0]

    // Pre: n > 0
    public static Object peek() {
        assert size > 0;
        return elements[dec(right)];
    }
    // Post: res == a[n - 1]

    // Pre: n > 0
    public static Object dequeue() {
    	assert size > 0;
    	Object temp = element();
    	popFront();
    	return temp;
    }
    // Post: (n' == n - 1) && (a'[i - 1] == a[i] for i = 1...n - 1) && (res == a[0])
    
    // Pre: n > 0 
    private static void popBack() {
    	assert size > 0;
    	right = dec(right);
    	elements[right] = null;
		size--;	
    }
    // Post: (n' == n - 1) && (a'[i] == a[i] for i = 0...n - 2)

    // Pre: n > 0 
    private static void popFront() {
    	assert size > 0;
    	elements[left] = null;
    	left = inc(left);   
		size--;	
    }
    // Post: (n' == n - 1) && (a'[i - 1] == a[i] for i = 1...n - 1)                                                           

    // Pre: n > 0
    public static Object remove() {
    	assert size > 0;
    	Object temp = peek();
    	popBack();
    	return temp;
    }
    // Post: (n' == n - 1) && (a'[i] == a[i] for i = 0...n - 2) && (res == a[n - 1])

    // Pre:
    public static int size() {
        return size;
    }
    // Post: res == n

    // Pre:
    public static boolean isEmpty() {
        return size == 0;
    }
    // Post: res == (n == 0)

    // Pre:
    public static void clear() {
        ensureCapacity(10);
        size = left = right = 0;
    }
    // Post: n == 0
}
