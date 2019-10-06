package cr.ac.tec.util.Collections.List;

import java.util.Iterator;

public class TecListNodeIterator implements Iterator<DataNode> {
    private DataNode current;

    public TecListNodeIterator(DataNode first) {
        current = first;
    }
    @Override
    public boolean hasNext() {
        return current!=null;
    }
    @Override
    public DataNode next() {
        DataNode temp = current;
        current = current.next;
        return temp;
    }
}
