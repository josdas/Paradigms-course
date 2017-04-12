//package queue;

public class ArrayQueue extends AbstractQueue  {
    private Object[] elements = new Object[10];
    private int left = 0;
    private int right = 0;

    private int getCapacity() {
  		return elements.length;
    }

    private int dec(int x) {
        if (x == 0) {
            return getCapacity() - 1;
        } else {
            return x - 1;
        }
    }

    private int inc(int x) {
    	if (x + 1 == getCapacity()) {
    		return 0;
    	} else {
        	return x + 1;
        }
    }

    private void saveAllElements(Object[] temp) {
        assert temp.length >= size;
        if (left <= right) {
            System.arraycopy(elements, left, temp, 0, right - left);
        } else {
            System.arraycopy(elements, left, temp, 0, getCapacity() - left);
            System.arraycopy(elements, 0, temp, getCapacity() - left, right);
        }
    }

    private void ensureCapacity(int sz) {
    	if (getCapacity() <= sz || getCapacity() > sz * 4) {
    		Object[] temp = new Object[sz * 2 + 1];
            saveAllElements(temp);
    		elements = temp;       
    		left = 0;
    		right = size - 1;
    	}	
    }

    protected void enqueueImpl(Object elem) {
    	ensureCapacity(size);
    	elements[right] = elem;
    	right = inc(right);
    }

    protected Object elementImpl() {
        return elements[left];
    }

    protected void popFront() {
    	assert size > 0;
    	elements[left] = null;
    	left = inc(left);
    }

    public void clear() {
        ensureCapacity(10);
        left = right = 0;
        size = 0;
    }

    protected Object[] getAllElements() {
        Object[] temp = new Object[size];
        saveAllElements(temp);
        return temp;
    }

    protected ArrayQueue newQueue() {
        return new ArrayQueue();
    }
}
