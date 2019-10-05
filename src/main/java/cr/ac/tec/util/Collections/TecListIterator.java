package cr.ac.tec.util.Collections;

import java.util.Iterator;

public class TecListIterator<T> implements Iterator<T> {
    private DataNode<T> current;

    public TecListIterator(DataNode<T> first) {
        current = first;
    }
    @Override
    public boolean hasNext() {
        return current!=null;
    }
    @Override
    public T next() {
        DataNode<T> temp = current;
        current = current.next;
        return temp.data;
    }
}
