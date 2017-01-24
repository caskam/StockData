
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
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
public class LocalDateStockData implements Comparable<LocalDate> {
    private LocalDate localDate;
    private List<StockData> stockData;
    private StockDataStats stockDataStats;
    private StockData localDateStockData;
    // ----------------------------------------------------------
    /**
     * Create a new LocalDateStockData object.
     * @param stockData first StockData datea point to add
     */
    public LocalDateStockData(StockData stockData) {
        localDate = stockData.getOpenDateTime().toLocalDate();
        this.stockData = new ArrayList<StockData>();
        stockDataStats = new StockDataStats();
        this.add(stockData);
    }
    // ----------------------------------------------------------
    /**
     * Return localDate of this set of StockData
     * @return localDate of this set of StockData
     */
    public LocalDate getLocalDate() {
        return localDate;
    }
    /**
     * Compare localDate of this instance with another localDate
     * @param other localDate to compare to
     */
    @Override
    public int compareTo(LocalDate other) {
        return localDate.compareTo(other);
    }
    @Override
    public String toString() {
        return localDate.toString() + '('+stockData.size()+')';
    }
    // ----------------------------------------------------------
    /**
     * Add a StockData data point. Assumed to be next in sequence for this instances LocalDate
     * @param newStockData StockData data point
     */
    public void add(StockData newStockData) {
        stockData.add(newStockData);
        stockDataStats.add(newStockData);
    }
    // ----------------------------------------------------------
    /**
     * Get the current value of stockDataStats.
     * @return The value of stockDataStats for this object.
     */
    public StockDataStats getStockDataStats()
    {
        return stockDataStats;
    }
    // ----------------------------------------------------------
    /**
     * Test if stockData for this period is in ascending order.
     * @return true if stockData for this period is in ascending order
     */
    public boolean assertSorted() {
        ZonedDateTime lastDate = null;
        boolean sorted = true;
        for ( StockData sd: stockData ) {
            ZonedDateTime currentDate = sd.getOpenDateTime();
            if ( lastDate == null ) {
                lastDate = currentDate;
            } else if ( currentDate.compareTo(lastDate) <= 0 ) {
                sorted = false;
                break;
            }
        }
        return sorted;
    }
    // ----------------------------------------------------------
    /**
     * Return a Stream<StockData>;
     * @return Stream<StockData> streamStockData
     */
    public Stream<StockData> streamStockData() {
        return stockData.stream();
    }
    // ----------------------------------------------------------
    /**
     * Get the current value of localDateStockData.
     * @return The value of localDateStockData for this object.
     */
    public StockData getLocalDateStockData()
    {
        if ( localDateStockData == null )
            reduce();
        return localDateStockData;
    }
    // ----------------------------------------------------------
    /**
     * Reduce the days tick data to a single StockData description
     */
    private void reduce() {
        localDateStockData = stockData.stream()
            .reduce(StockData::reduce)
            .orElseThrow(
                ()->new IllegalStateException("No stockData for localDate " + (localDate!=null?localDate:"unknown"))
            );
    }
}
