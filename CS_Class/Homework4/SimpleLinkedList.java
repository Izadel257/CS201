import java.util.*;

public class SimpleLinkedList<E> {
    private Node<E> head;
    private int numItems;

    private class Node<E> {
        private Node<E> next;
        private E data;

        public Node(E item) {
            data = item;
            next = null;
        }


    }
//--------------------------------------------------------------------
    public SimpleLinkedList() {
        head = null;
        numItems = 0;
    }

	public void add(E item) {
		if (head != null)  {
			Node<E> temp = head;
			while (temp.next != null) {
				temp = temp.next;
			}
			temp.next = new Node<E>(item);
			numItems++;
		} else {
			head = new Node<E>(item);
			numItems++;
		}
	}











    public String toString() {
        StringJoiner sj = new StringJoiner(" -> ");
        Node<E> temp = head;
        while (temp != null) {
            sj.add(temp.data.toString());
            temp = temp.next;
        }
        return sj.toString();
    }

    public static void main(String[] args) {
        SimpleLinkedList<String> lst = new SimpleLinkedList<String>();
        System.out.println(lst);
    }
}
