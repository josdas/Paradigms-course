public class ArrayQueue extends AbstractQueue{
    // Inv: (n >= 0) && (a[i] != null for i = 1..n - 1)
    private Object[] elements = new Object[10];
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
                         
    private void saveAllElements(Object[] temp) {
        assert temp.length >= size;
        if (left <= right) {
            System.arraycopy(elements, left, temp, 0, right - left);
        } else {
            System.arraycopy(elements, left, temp, 0, getCapacity() - left);
            System.arraycopy(elements, 0, temp, getCapacity() - left, right);
        }
    }
    //Pre: sz >= 0
    private void ensureCapacity(int sz) {
    	if (getCapacity() <= sz || getCapacity() > sz * 4) {
    		Object[] temp = new Object[sz * 2 + 1];
            saveAllElements(temp);
    		elements = temp;       
    		left = 0;
    		right = size - 1;
    	}	
    }
    // Post: sz < |elem'| <= sz * 4

    // Pre: elem != null
    protected void enqueueImpl(Object elem) {
        size++;
    	ensureCapacity(size);
    	elements[right] = elem;
    	right = inc(right);
    }
    // (n' == n + 1) && (a'[i] == a[i] for i = 0..n - 1) && (a'[n] == elem)

    // Pre: n > 0
    protected Object elementImpl() {
        return elements[left];
    }
    // Post: res == a[0]

    // Pre: n > 0
    protected void popFront() {
    	assert size > 0;
    	elements[left] = null;
    	left = inc(left);
    }
    // Post: (n' == n - 1) && (a'[i - 1] == a[i] for i = 1...n - 1)

    // Pre:
    public void clear() {
        ensureCapacity(10);
        left = right = 0;
        size = 0;
    }
    // Post: n == 0

    protected Object[] getAllElements() {
        Object[] temp = new Object[size];
        saveAllElements(temp);
        return temp;
    }

    protected Queue newQueue() {
        return new ArrayQueue();
    }
}
