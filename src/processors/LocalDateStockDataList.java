package processors;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import stockdata.StockData;
import stockdata.StockDataProcessorInterface;

/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author Karl Nicholas
 *  @version Jan 19, 2017
 */
public class LocalDateStockDataList implements StockDataProcessorInterface {
    private String stockSymbol;
    private List<LocalDateStockData> localDateStockDataList;

    /**
     * Create a new LocalDateStockDataList object.
     * @param getStockSymbol found on exchange
     */
    public LocalDateStockDataList(Function<Path, String> getStockSymbol) {
        stockSymbol = getStockSymbol();
        localDateStockDataList = new ArrayList<LocalDateStockData>();
    }
    public void add(StockData stockData)
    {
        LocalDate localDate = stockData.getOpenDateTime().toLocalDate();
        int index = Collections.binarySearch(localDateStockDataList, localDate );
        LocalDateStockData localDateStockData;
        if ( index < 0 ) {
            localDateStockData = new LocalDateStockData();
            localDateStockDataList.add((0-index)-1, localDateStockData);
        } else {
            localDateStockData = localDateStockDataList.get(index);
        }
        localDateStockData.add(stockData);
    }

    public void process(Stream<StockData> stockDataStream)
    {
        stockDataStream.forEachOrdered(sd->add(sd));
    }
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
    /**
     * {@inheritDoc}
     */
    @Override
    public String getStockSymbol()
    {
        return stockSymbol;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String returnResults()
    {
        return localDateStockDataList.toString();
    }
}
