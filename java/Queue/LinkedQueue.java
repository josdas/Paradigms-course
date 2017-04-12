//package queue;

/**
 * Created by Stas on 17.03.2017.
 */
public class LinkedQueue extends AbstractQueue {
    private ListNode tail, head;

    public LinkedQueue() {
        tail = new ListNode();
        head = new ListNode();
        tail.next = head;
        head.prev = tail;
    }

    protected void enqueueImpl(Object e) {
        ListNode temp = new ListNode(e);
        temp.next = tail.next;
        temp.prev = tail;
        temp.next.prev = temp;
        tail.next = temp;
    }

    protected Object elementImpl() {
        return head.prev.value;
    }

    protected void popFront() {
        ListNode temp = head.prev;
        head.prev = temp.prev;
        temp.prev.next = head;
        temp.clear();
    }

    private void print() {
        ListNode cur = tail.next;
        tail.next = head;
        head.prev = tail;
        System.out.println("print: ");
        for(int i = 0; i < size; i++) {
            ListNode temp = cur.next;
            System.out.println(cur.value);
            cur = temp;
        }
    }

    public void clear() {
        ListNode cur = tail.next;
        tail.next = head;
        head.prev = tail;
        for(int i = 0; i < size; i++) {
            ListNode temp = cur.next;
            cur.clear();
            cur = temp;
        }
        size = 0;
    }

    protected LinkedQueue newQueue() {
        return new LinkedQueue();
    }

    protected Object[] getAllElements() {
        Object temp[] = new Object[size];
        ListNode cur = head.prev;
        for(int i = 0; i < size; i++) {
            temp[i] = cur.value;
            cur = cur.prev;
        }
        return temp;
    }

    private class ListNode{
        private ListNode next, prev;
        private Object value;
        ListNode() { }
        ListNode(Object obj) {
            value = obj;
        }
        void clear() {
            next = null;
            prev = null;
            value = null;
        }
    }
}
