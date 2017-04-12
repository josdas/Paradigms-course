public class ArrayQueue {
    // Inv: (n >= 0) && (a[i] != null for i = 1..n - 1)
    private Object[] elements = new Object[10];
    private int size = 0;
    private int left = 0;
    private int right = 0;

   	// Pre: 
    private int getCapacity() {
  		return elements.length;
    }
    // Post: res = |elem|

    // Pre: (|elem| != 0) && (0 <= x < |elem|)
    private int dec(int x) {
        if (x == 0) {
            return getCapacity() - 1;
        } else {
            return x - 1;
        }
    }
    // Post: (res == x - 1 && x > 0) || (res == |elem| && x == 0)
    

    // Pre: (|elem| != 0) && (0 <= x < |elem|)
    private int inc(int x) {
    	if (x + 1 == getCapacity()) {
    		return 0;
    	} else {
        	return x + 1;
        }
    }
    // res == (x + 1) % |elem|
                         

    //Pre: sz >= 0
    private void ensureCapacity(int sz) {
    	if ((getCapacity() <= sz) || (getCapacity() > sz * 4)) {
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
    // Post: sz < |elem'| <= sz * 4

    // Pre: elem != null
    public void enqueue(Object elem) {
    	assert elem != null;
    	size++;
    	ensureCapacity(size);
    	elements[right] = elem;
    	right = inc(right);
    }
    // (n' == n + 1) && (a'[i] == a[i] for i = 0..n - 1) && (a'[n] == elem)

    // Pre: elem != null
    public void push(Object elem) {
    	assert elem != null;
    	size++;
    	ensureCapacity(size);
    	left = dec(left);
    	elements[left] = elem;
    }
    // (n' == n + 1) && (a'[i + 1] == a[i] for i = 0..n - 1) && (a'[0] == elem)

    // Pre: n > 0
    public Object element() {
        assert size > 0;
        return elements[left];
    }
    // Post: res == a[0]

    // Pre: n > 0
    public Object peek() {
        assert size > 0;
        return elements[dec(right)];
    }
    // Post: res == a[n - 1]

    // Pre: n > 0
    public Object dequeue() {
    	assert size > 0;
    	Object temp = element();
    	popFront();
    	return temp;
    }
    // Post: (n' == n - 1) && (a'[i - 1] == a[i] for i = 1...n - 1) && (res == a[0])
    
    // Pre: n > 0 
    private void popBack() {
    	assert size > 0;
    	right = dec(right);
    	elements[right] = null;
		size--;	
    }
    // Post: (n' == n - 1) && (a'[i] == a[i] for i = 0...n - 2)

    // Pre: n > 0 
    private void popFront() {
    	assert size > 0;
    	elements[left] = null;
    	left = inc(left);   
		size--;	
    }
    // Post: (n' == n - 1) && (a'[i - 1] == a[i] for i = 1...n - 1)                                                           

    // Pre: n > 0
    public Object remove() {
    	assert size > 0;
    	Object temp = peek();
    	popBack();
    	return temp;
    }
    // Post: (n' == n - 1) && (a'[i] == a[i] for i = 0...n - 2) && (res == a[n - 1])

    // Pre:
    public int size() {
        return size;
    }
    // Post: res == n

    // Pre:
    public boolean isEmpty() {
        return size == 0;
    }
    // Post: res == (n == 0)

    // Pre:
    public void clear() {
        ensureCapacity(10);
        size = left = right = 0;
    }
    // Post: n == 0
}
