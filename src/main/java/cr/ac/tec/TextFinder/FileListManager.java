package cr.ac.tec.TextFinder;

import cr.ac.tec.TextFinder.documents.Document;
import cr.ac.tec.TextFinder.documents.SearchResult;
import cr.ac.tec.TextFinder.documents.SortBy;
import cr.ac.tec.util.Collections.List.TecList;
import javafx.util.Pair;

import java.util.Arrays;

public class FileListManager {
    static FileListManager instance;
    ViewController viewController;
    private TecList<Document> docList = null;
    private TecList<SearchResult> srchResults = null;
    private FileListManager(){
        docList = new TecList<>();
        srchResults = new TecList<>();
    }
    public static FileListManager getInstance() {
        if(instance==null) {
            instance = new FileListManager();
        }
        return instance;
    }
    public synchronized void addDocument(Document newDocument){
        docList.add(newDocument);
        addSearchResult(new SearchResult(newDocument, "LMAO", new Pair<>(2,3)));
    }
    public synchronized void deleteDocument(Document toDelete){
        docList.removeValue(toDelete);
    }
    public synchronized TecList<Document> getDocList(){
        final TecList<Document> readOnlyDocuments = docList;
        return readOnlyDocuments;
    }

    public void addSearchResult(SearchResult result){
        srchResults.add(result);
        viewController.resultContainer.getChildren().add(result);
    }
    public void sortResults(SortBy args) {
        for (SearchResult srchRes: srchResults) {
            viewController.resultContainer.getChildren().remove(srchRes);
        }
        if(args == SortBy.NAME)
            sortListByName();
        else if(args == SortBy.SIZE){
            System.out.println("uwu");
            sortListBySize();}
        else if(args == SortBy.DATE){
            System.out.println("date ots");
            sortListByDate();}
        else{
            System.out.println("ssssssssssssssssssssssssssssss");
        }
        for (SearchResult srchRes: srchResults) {
            viewController.resultContainer.getChildren().add(srchRes);
        }
    }
    //Bubble sort
    public void sortListByDate(){
        int n = srchResults.size();
        SearchResult temp = null;
        boolean stop = false;
        while(!stop) {
            int c = 0;
            for (int i = 0; i < n; i++) {
                for (int j = i; j < n; j++) {
                    if (srchResults.get(i).getDoc().getDate() > srchResults.get(j).getDoc().getDate()) {
                        temp = srchResults.get(i);
                        srchResults.reinsert(srchResults.get(j), i);
                        srchResults.reinsert(temp, j);
                        c++;
                    }
                }
            }
            if (c == 0) {
                stop = true;
            }else{
            }
        }
    }
    //Radix sort
    public void sortListBySize(){
        int n = srchResults.size();
        int[] array = new int[n];
        for (int i=0; i<srchResults.size(); i++){
            array[i] = srchResults.get(i).getDoc().getSize();
        }
        int max = getMaximum(array, n);
        for (int exp = 1; max/exp > 0; exp *= 10) {
            sort(array, n, exp);
        }

        TecList<SearchResult> orderedList = new TecList<>();
        for(int i=0; i<array.length; i++){
            for(int j=0; j<srchResults.size(); j++){
                if(array[i] == srchResults.get(j).getDoc().getSize()){
                    orderedList.add(srchResults.get(j));
                    srchResults.removeValue(srchResults.get(j));
                    break;
                }
            }
        }

        for (int i=0; i<orderedList.size(); i++){
            srchResults.add(orderedList.get(i));
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
    public void sortListByName(){
        sortListByName(srchResults, 0, srchResults.size()-1);
    }
    private void sortListByName(TecList<SearchResult> list, int start, int end){
        int i = start;
        int j = end;
        if(end-start >=1){
            String pivot = list.get(start).getDoc().getName();
            while(j>i){
                while(list.get(i).getDoc().getName().compareToIgnoreCase(pivot) <= 0 && i<= end && j>i){
                    i++;
                }
                while(list.get(j).getDoc().getName().compareToIgnoreCase(pivot) > 0 && j>=start && j>=i){
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
    private void swap (TecList<SearchResult> list, int index1, int index2){
        SearchResult temp = list.get(index1);
        list.reinsert(list.get(index2), index1);
        list.reinsert(temp, index2);
    }
    public synchronized TecList<SearchResult> getSrchResults() {
        return srchResults;
    }
    public synchronized void setSrchResults(TecList<SearchResult> srchResults) {
        this.srchResults = srchResults;
    }
}
