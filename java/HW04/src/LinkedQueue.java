/**
 * Created by Stas on 17.03.2017.
 */
public class LinkedQueue extends AbstractQueue {
    private Node tail, head;

    LinkedQueue() {
        tail = new Node();
        head = new Node();
        tail.next = head;
        head.prev = tail;
    }

    protected void enqueueImpl(Object e) {
        Node temp = new Node(e);
        temp.next = tail.next;
        temp.prev = tail;
        temp.next.prev = temp;
        tail.next = temp;
    }

    protected Object elementImpl() {
        return head.prev.value;
    }

    protected void popFront() {
        Node temp = head.prev;
        head.prev = temp.prev;
        temp.prev.next = head;
        temp.clear();
    }
    private void print() {
        Node cur = tail.next;
        tail.next = head;
        head.prev = tail;
        System.out.println("print: ");
        for(int i = 0; i < size; i++) {
            Node temp = cur.next;       
            System.out.println(cur.value);
            cur = temp;                   
        }
    }
    public void clear() {
        Node cur = tail.next;
        tail.next = head;
        head.prev = tail;
        for(int i = 0; i < size; i++) {
            Node temp = cur.next;
            cur.clear();
            cur = temp;
        }
        size = 0;
       	print();
    }

    protected Queue newQueue() {
        return new LinkedQueue();
    }

    protected Object[] getAllElements() {
        Object temp[] = new Object[size];
        Node cur = tail.next;
        for(int i = 0; i < size; i++) {
            temp[i] = cur.value;
            cur = cur.next;
        }
        return temp;
    }

    private class Node{
        private Node next, prev;
        private Object value;
        Node() { }
        Node(Object obj) {
            value = obj;
        }
        void clear() {
            next = null;
            prev = null;
            value = null;
        }
    }
}
