package processors;

import com.google.common.math.StatsAccumulator;
import java.nio.file.Path;
import java.time.LocalDate;
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
public class LocalDateStatistics implements StockDataProcessorInterface {
    private String stockSymbol;
    private LocalDateStockData localDateStockData;
    private StatsAccumulator volumeStats;
    private StatsAccumulator highStats;
    private StatsAccumulator lowStats;

    /**
     * Create a new LocalDateStockDataList object.
     * @param getGtockSymbol function for getting symbol found on exchange
     */
    public LocalDateStatistics(Function<Path, String> getGtockSymbol) {
        stockSymbol = getStockSymbol();
        volumeStats = new StatsAccumulator();
        highStats = new StatsAccumulator();
        lowStats = new StatsAccumulator();
    }

    public void add(StockData stockData)
    {
        LocalDate localDate = stockData.getOpenDateTime().toLocalDate();
        if ( localDateStockData == null ) {
            localDateStockData = new LocalDateStockData();
        } else if ( localDateStockData.getLocalDate().compareTo(localDate) != 0 ) {
            volumeStats.add(localDateStockData.getLocalDateStockData().getVolume());
            highStats.add(localDateStockData.getLocalDateStockData().getHigh().floatValue());
            lowStats.add(localDateStockData.getLocalDateStockData().getLow().floatValue());
            localDateStockData = new LocalDateStockData();
        }
        localDateStockData.add(stockData);
    }

    public void process(Stream<StockData> stockDataStream) {
        stockDataStream.forEachOrdered(sd->add(sd));
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
        return String.format("\nVolume %s\n  High %s\n   Low %s",
            volumeStats.snapshot().toString(),
            highStats.snapshot().toString(),
            lowStats.snapshot().toString()
        );
    }
}
