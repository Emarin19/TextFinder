package cr.ac.tec.util.Collections;

import java.io.Serializable;
import java.util.Iterator;
/**
 * Technological Institute of Costa Rica
 * Computer Engineering
 * Course: de Algoritmos y estructuras de datos I
 * Project II: TextFinder
 * JDK 11
 * Description: Custom Linked List object. Can perform many standard list operations plus some more
 * specific operations.
 * @author Jose Morales Vargas
 * @since October 2019
 */
public class TecList<T> implements Iterable<T>, Serializable {
    private DataNode<T> first;
    private DataNode<T> last;

    /**
     * Default constructor for a TecList which is an implementation of doubly Linked List
     * The way data is stored is using a DataNode object
     * @see cr.ac.tec.util.Collections.DataNode
     */
    public TecList(){
        first = last = null;
    }

    /**
     * Creates a new DataNode object which stores the value specified. Adds the node to the TecList in the last position
     * @param value value to storage
     */
    public void add(T value){
        if (first==null) {
            first = new DataNode<T>(value);
            first.prev = null;
            last = first;
        }else{
            DataNode<T> elemento = new DataNode(value);
            elemento.prev =last;
            last.next = elemento;
            last=elemento;
        }
        return;
    }

    /**
     * Creates a new DataNode object per value specified. Adds the nodes to the TecList
     * @param items Collection of items to be added to the TecList
     */
    public void addAll(T ...items){
        for(T i : items){
            add(i);
        }
        return;
    }

    /**
     * Creates a new DataNode object which stores the value specified. Adds the node to the TecList in the index
     * specified
     * @param value value to storage
     * @param index index in which the value will be positioned
     */
    public void insert(T value, int index){
        if(index == 0){
            if(first!=null){
                first.prev = new DataNode<>(value);
                first.prev.next = first;
                first = first.prev;
            }else{
                first = last = new DataNode<>(value);
            }
        }else if(index == size()-1){
            System.out.println(2);
            DataNode<T> newNode = new DataNode<>(value);
            last.prev.next = newNode;
            newNode.prev = last.prev;
            newNode.next = last;
            last.prev = newNode;
        }else if(index<size()-1){

            DataNode current = first;
            for(int i=0; i<index; i++){
                current = current.next;
            }
            DataNode<T> newNode = new DataNode<>(value);
            current.prev.next = newNode;
            newNode.prev = current.prev;
            current.prev = newNode;
            newNode.next = current;
        }
        else if(index==size()){

            add(value);
        }
        return;
    }

    /**
     * Returns the data stored in the DataNode at the position specified
     * @param index position of the list of the value to be checked
     * @return value of DataNode at index
     */
    public T get(int index){
        DataNode<T> current = first;
        for(int i=0; i<index; i++){
            current = current.next;
        }
        return current.data;
    }

    /**
     * Deletes the reference to a DataNode at an specified position, returns its value
     * @param index position of DataNode in the TecList
     * @return value of the DataNode at the position specified
     */
    public T pop(int index){
        if(first != null && last!=null) {
            int listSize = size();
            T data;
            if (index == listSize - 1) {
                data = last.data;
                removeLast();
            } else if (index == 0) {
                data = first.data;
                removeFirst();
            } else {
                DataNode<T> current = first;
                for (int i = 0; i < index; i++) {
                    current = current.next;
                }
                current.prev.next = current.next;
                current.next.prev = current.prev;
                data=current.data;
            }
            return data;
        }
        return null;
    }

    /**
     * deletes the reference to a DataNode
     * @param dataNode DataNode object to be removed
     */
    public void remove(DataNode<T> dataNode){
        if(dataNode.equals(first)){
            removeFirst();
        }else if(dataNode.equals(last)){
            removeLast();
        }else{
            dataNode.prev.next = dataNode.next;
            dataNode.next.prev=dataNode.prev;
        }
        return;
    }
    public void removeValue(T value){
        DataNode<T> current = first;
        while(!current.data.equals(value) && current.next!=null){
            current = current.next;
        }
        if(current.data.equals(value)) {
            remove(current);
        }
        return;
    }

    /**
     * removes first element of list
     */
    public void removeFirst(){
        if (first!= null) {
            if(first.next!=null) {
                first = first.next;
                first.prev= null;
            }else{
                first=last=null;
            }
        }
        return;
    }

    /**
     * removes last element of list
     */
    public void removeLast(){
        if (last!= null) {
            if (last.prev!=null) {
                last = last.prev;
                last.next =null;
            }else{
                last=first=null;
            }
        }
        return;
    }

    /**
     * counts the elements in the list
     * @return count of elements in the list
     */
    public int size(){
        int cnt = 0;
        for (T nodeData : this){
            cnt++;
        }
        return cnt;
    }

    /**
     * returns first DataNode reference
     * @return first DataNode
     */
    public DataNode<T> getFirst() {
        return first;
    }

    /**
     * returns last DataNode reference
     * @return last node
     */
    public DataNode<T> getLast() {
        return last;
    }

    /**
     *  returns value of first DataNode
     * @return value of first node
     */
    public T getFirstValue() {
        return first.data;
    }

    /**
     * checks the value stored on the last DataNode
     * @return value of last DataNode
     */
    public T getLastValue() {
        return last.data;
    }

    /**
     * checks if the list has more than 0 elements
     * @return if the list is empty, true
     */
    public boolean isEmpty(){
        return first == null;
    }

    /**
     * Checks if a value is stored in any node of the list
     * @param value value to be looked for
     * @return whether the value is or not contained in the list
     */
    public boolean contains(T value){
        boolean contains=false;
        for (T nodeData : this){
            if (value.equals(nodeData)) {
                contains = true;
            }
        }
        return contains;
    }

    /**
     * counts how many Nodes hold the value specified
     * @param value value to be looked for
     * @return times the value is stored
     */
    public int nodesWithValue(T value){
        int cnt = 0;
        for (T nodeData: this){
            if (value.equals(nodeData)) {
                cnt++;
            }
        }
        return cnt;
    }

    /**
     * override of standard toString method
     * @return String representation of self
     */
    @Override
    public String toString() {
        String toString = "[|-|";
        for (T value:this) {
            toString += value.toString()+ "|-|";
        }
        toString+="]";
        return toString;
    }

    /**
     * Defines the iterator for the list to use in for each loop
     * @return iterator object for the TecList
     */
    @Override
    public Iterator<T> iterator() {
        return new TecListIterator(first);
    }
}
