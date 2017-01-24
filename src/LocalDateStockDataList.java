
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

// -------------------------------------------------------------------------
/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author Karl Nicholas
 *  @version Jan 19, 2017
 */
public class LocalDateStockDataList {
    private List<LocalDateStockData> localDateStockDataList;
    // ----------------------------------------------------------
    /**
     * Create a new LocalDateStockDataList object.
     */
    public LocalDateStockDataList() {
        localDateStockDataList = new ArrayList<LocalDateStockData>();
    }
    // ----------------------------------------------------------
    /**
     * implementation for Collector accumulator requirement
     * @param stockData StockData to add
     */
    public void add(StockData stockData) {
        LocalDate localDate = stockData.getOpenDateTime().toLocalDate();
        int index = Collections.binarySearch(localDateStockDataList, localDate );
        LocalDateStockData localDateStockData;
        if ( index < 0 ) {
            localDateStockData = new LocalDateStockData(stockData);
            localDateStockDataList.add((0-index)-1, localDateStockData);
        } else {
            localDateStockData = localDateStockDataList.get(index);
            localDateStockData.add(stockData);
        }
    }
    // ----------------------------------------------------------
    /**
     * implementation for Collector combine requirement
     * @param otherMap other LocalDateStockDataList to merge
     * @return this
     */
    public LocalDateStockDataList merge(LocalDateStockDataList otherMap) {
        otherMap.localDateStockDataList.stream().forEach(localDateStockData->{
            int index = Collections.binarySearch(localDateStockDataList, localDateStockData.getLocalDate() );
            localDateStockDataList.add((0-index)-1, localDateStockData);
        });
        return this;
    }
    @Override
    public String toString() {
        return localDateStockDataList.toString();
    }
    // ----------------------------------------------------------
    /**
     * Stream LocalDateStockData from internal data.
     * @return Stream of LocalDateStockData
     */
    public Stream<LocalDateStockData> streamLocalDateStockData()
    {
        return localDateStockDataList.stream();
    }
    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * @return true if sorted
     */
    public boolean assertSorted() {
        LocalDate lastDate = null;
        boolean sorted = true;
        for ( LocalDateStockData localDateStockData: localDateStockDataList ) {
            if ( !localDateStockData.assertSorted() ) {
                sorted = false;
                break;
            }
            LocalDate currentDate = localDateStockData.getLocalDate();
            if ( lastDate == null ) {
                lastDate = currentDate;
            } else if ( currentDate.compareTo(lastDate) <= 0 ) {
                sorted = false;
                break;
            }
        }
        return sorted;
    }
}
