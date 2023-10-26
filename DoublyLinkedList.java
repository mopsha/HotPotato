import java.util.Iterator;
import java.util.NoSuchElementException;
public class DoublyLinkedList<E> implements ListAbstractType<E> {
    private static class Node<E> {
        Node<E> next;
        Node<E> previous;
        E data;
        public Node(E data){
            this.data = data;
        }
        public Node(){
            this.next = null;
            this.previous = null;
        }


    }


    Node<E> first;
    Node<E> last;


    public TwoWayListIterator<E> iterator(){
        return new DoublyLinkedListIterator();
    }
    //Implementing Iterator
    private class DoublyLinkedListIterator implements TwoWayListIterator<E> {
        private Node<E> current;
        private boolean nextState;
        private boolean prevState;
        private Node<E> lastReturned;

        public DoublyLinkedListIterator(){
            current = new Node<>();
            current.next = first;
        }

        public boolean hasPrevious() {
            return current != null && current.previous != null;
        }

        public E previous() {
            prevState = true;
            nextState = false;
            if(hasPrevious()){
                current = current.previous;
                lastReturned = current;
                return current.data;
            }
            throw new NoSuchElementException();
        }

        public void add(E element) {
            prevState = false;
            nextState = false;
            Node<E> addNew = new Node<>(element);
            if (hasPrevious() && hasNext()) {
                addNew.next = current.next;
                addNew.previous = current.previous;
                current.previous.next = addNew;
                current.next.previous = addNew;
            } else if (hasPrevious()) {
                last.previous.next = addNew;
                addNew.previous = last.previous;
                last = addNew;
            } else if (hasNext()) {
                addNew.next = first.next;
                current.next.previous = addNew;
                first = addNew;
            } else {
                throw new UnsupportedOperationException();
            }
            current = addNew;
        }

        public boolean hasNext() {
            return current != null && current.next != null;
        }

        public E next() {
            nextState = true;
            prevState = false;
            if (hasNext()) {
                current = current.next;
                lastReturned = current;
                return current.data;
            }
            throw new NoSuchElementException();
        }

        public void remove(){
            if(current == null){
                throw new UnsupportedOperationException();
            }
            if(current.previous.next == current.next || !nextState){
                throw new IllegalStateException();
            }
            current.previous = current.next;
            current.next.previous = current.previous;
            nextState = false;
            prevState = false;
        }

        public void set(E element){
            if(!nextState && !prevState){
                throw new IllegalStateException();
            }
            Node<E> newNode = new Node<>(element);
            if(lastReturned.next != null && lastReturned.previous != null){
                newNode.next = lastReturned.next;
                newNode.previous = lastReturned.previous;
                lastReturned.next.previous = newNode;
                lastReturned.previous.next = newNode;
            }else if(lastReturned.previous != null){
                newNode.previous = lastReturned.previous;
                last = newNode;
                lastReturned.previous.next = newNode;
            }else if(lastReturned.next != null){
                newNode.next = lastReturned.next;
                first = newNode;
                lastReturned.next.previous = newNode;
            }
            throw new UnsupportedOperationException();
        }
    }
    DoublyLinkedListIterator iterator = new DoublyLinkedListIterator();
    //Implementation of CollectionType:
    public boolean add(E element){
        Node<E> addNew = new Node<>(element);
        if (first == null) {
            first = addNew;
        }else{
            last.next = addNew;
            addNew.previous = last;
        }
        last = addNew;
        return true;
    }

    public boolean remove(E element){
        if(this.contains(element)){
            iterator.current = first;
            while(iterator.current.data != element){
                iterator.current = iterator.current.next;
            }
            if(iterator.current.previous != null) {
                iterator.current.previous.next = iterator.current.next;
            }
            return true;
        }
        return false;
    }

    public int size(){
        iterator.current = first;
        int counter = 0;
        if(first == null){
            return 0;
        }else{
            while(iterator.current != null){
                counter++;
                iterator.current = iterator.current.next;
            }
            return counter;
        }
    }

    public boolean isEmpty(){
        return size() == 0;
    }

    public boolean contains(E element){
        iterator.current = first;
        if(first == null){
            return false;
        }else{
            while(element != iterator.current.data){
                iterator.current = iterator.current.next;
            }
            return true;
        }
    }

    //Implementing the ListAbstractType interface
    public E get(int index){
        if(size() > index && index >= 0){
            iterator.current = first;
            for(int i = 0; i < index; i++) iterator.current = iterator.current.next;
            if(iterator.current.data != null){
                return iterator.current.data;
            }
        }else if(size() == index){
            return last.data;
        }
        return first.data;
    }

    public E set(int index, E element){
        Node<E> temp;
        if(size() >= index && index >= 0){
            iterator.current = first;
            for(int i = 0; i < index; i++) iterator.current = iterator.current.next;
            Node<E> setter = new Node<>(element);
            if(iterator.current.next != null) setter.next = iterator.current.next;
            setter.previous = iterator.current.previous;
            temp = iterator.current;
            iterator.current = iterator.current.previous;
            iterator.current.next = setter;
            return temp.data;
        }
        return first.data;
    }

    public void add(int index, E element){
        Node<E> addNew = new Node<>(element);
        iterator.current = first;
        if(index == 0){
            addNew.next = first;
            iterator.current.previous = addNew;
            first = addNew;
        }else if(index > 0 && index < size()){
            for(int i = 0; i < index; i++){
                iterator.current = iterator.current.next;
            }
            addNew.next = iterator.current;
            addNew.previous = iterator.current.previous;
            iterator.current = iterator.current.previous;
            iterator.current.next = addNew;
        }else if(index == size()){
            last.next = addNew;
            addNew.previous = last;
            last = addNew;
        }
    }

    public void remove(int index) {
        if(index < 0 || index >= size()){
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        iterator.current = first;
        int cIndex = 0;
        while(index > cIndex){
            iterator.current = iterator.current.next;
            cIndex++;
        }

        if(iterator.current.previous != null){
            iterator.current.previous.next = iterator.current.next;
        }else{
            first = iterator.current.next;
        }

        if(iterator.current.next != null){
            iterator.current.next.previous = iterator.current.previous;
        }else{
            last = iterator.current.previous;
        }
    }

    @Override
    public String toString() {
        Iterator<E> it = this.iterator();
        if (!it.hasNext()) {
            return "[]";
        } else{
            StringBuilder builder = new StringBuilder("[");
            while (it.hasNext()) {
                E e = it.next();
                builder.append(e.toString());
                if (!it.hasNext())
                    return builder.append("]").toString();
                builder.append(", ");
            }
            //code execution should never reach this line
        }
        return null;
    }
}
