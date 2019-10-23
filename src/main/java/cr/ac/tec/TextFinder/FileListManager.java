package cr.ac.tec.TextFinder;

import cr.ac.tec.TextFinder.documents.Document;
import cr.ac.tec.util.Collections.List.TecList;

import java.util.Arrays;

public class FileListManager {
    static FileListManager instance;
    ViewController viewController;
    private TecList<Document> documentsList = null;

    private FileListManager(){
        documentsList = new TecList<>();
    }
    public static FileListManager getInstance() {
        if(instance==null) {
            instance = new FileListManager();
        }
        return instance;
    }
    public synchronized void addDocument(Document newDocument){
        System.out.print(newDocument.getTree().toString());
        documentsList.add(newDocument);
    }
    public synchronized void deleteDocument(Document toDelete){
        documentsList.removeValue(toDelete);
    }
    public synchronized TecList<Document> getDocumentsList(){
        final TecList<Document> readOnlyDocuments = documentsList;
        return readOnlyDocuments;
    }

    //Bubble sort
    public void sortListByDate(TecList<Document> list){
        int n = list.size();
        Document temp = null;
        for (int i=0; i<n; i++){
            for(int j=1; j<(n-i); j++){
                if (list.get(j-1).getDate() > list.get(j).getDate()){
                    temp = list.get(j-1);
                    list.reinsert(list.get(j), j-1);
                    list.reinsert(temp, j);
                }
            }
        }
    }

    //Radix sort
    public void sortListBySize(TecList<Document> list){
        int n = list.size();
        int[] array = new int[n];
        for (int i=0; i<list.size(); i++){
            array[i] = list.get(i).getSize();
        }
        int max = getMaximum(array, n);
        for (int exp = 1; max/exp > 0; exp *= 10) {
            sort(array, n, exp);
        }

        TecList<Document> orderedList = new TecList<>();
        for(int i=0; i<array.length; i++){
            for(int j=0; j<list.size(); j++){
                if(array[i] == list.get(j).getSize()){
                    orderedList.add(list.get(j));
                    list.removeValue(list.get(j));
                    break;
                }
            }
        }

        for (int i=0; i<orderedList.size(); i++){
            list.add(orderedList.get(i));
        }
        orderedList = null;
    }
    private int getMaximum(int[] array, int n) {
        int max = array[0];
        for (int i=1; i<n; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }
    private void sort(int[] array, int n, int exp) {
        int output[] = new int[n];
        int i;
        int count[] = new int[10];
        Arrays.fill(count,0);

        for (i = 0; i < n; i++)
            count[ (array[i]/exp)%10 ]++;

        for (i = 1; i < 10; i++)
            count[i] += count[i - 1];

        for (i = n - 1; i >= 0; i--) {
            output[count[ (array[i]/exp)%10 ] - 1] = array[i];
            count[ (array[i]/exp)%10 ]--;
        }

        for (i = 0; i < n; i++)
            array[i] = output[i];
    }

    //Quick sort
    public void sortListByName(TecList<Document> list){
        sortListByName(list, 0, list.size()-1);
    }
    private void sortListByName(TecList<Document> list, int start, int end){
        int i = start;
        int j = end;
        if(end-start >=1){
            String pivot = list.get(start).getName();
            while(j>i){
                while(list.get(i).getName().compareTo(pivot) <= 0 && i<= end && j>i){
                    i++;
                }
                while(list.get(j).getName().compareTo(pivot) > 0 && j>=start && j>=i){
                    j--;
                }
                if(j>i){
                    swap(list, i, j);
                }
            }
            swap(list, start, j);
            sortListByName(list, start, j-1);
            sortListByName(list, j+1, end);
        }
        else{ return; }
    }
    private void swap (TecList<Document> list, int index1, int index2){
        Document temp = list.get(index1);
        list.reinsert(list.get(index2), index1);
        list.reinsert(temp, index2);
    }
}
