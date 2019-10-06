package cr.ac.tec.util.Collections.List;


import java.io.Serializable;
/**
 * Technological institute of Costa Rica
 * Computer Engineering
 * Course: de Algoritmos y estructuras de datos I
 * Project II: TextFinder
 * JDK 11
 * Description: Node that stores data in the TecLinkedList. Serializable
 * @author Jose Morales Vargas
 * @since September 2019
 */
public class DataNode<T> implements Serializable {
    DataNode<T> next;
    DataNode<T> prev;
    T data;

    /**
     * Constructor of the DataNode
     * @param data data to be stored
     */
    public DataNode(T data){
        this.data = data;
        this.next = this.prev =null;
    }

}
