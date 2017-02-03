
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * Read 5 minute tick stock data from http://stooq.com/db/d/?b=5_us_txt
 * assumed at this point to be data for a 5 minute period
 * Currently hard coded to read specific downloaded data.
 *
 * This is just a playground project for practicing Java Lambda language features.
 *
 * @author Karl Nicholas
 * @version Jan 19, 2017
 * @param <A> Accumulator
 * @param <R> Result
 */
public class StockDataProcessor<A, R extends StockSymbolHolder> implements Function<String, R> {
    private static final String sourceDirectory = "C:/Users/quix0/Downloads/5_us_txt/data/5 min/us/";
    private static final String[] nyseSourceExchanges = {"nyse stocks/1/"};
    private static final String datafileExtension = ".us.txt";
    private Collector<StockData, A, R> collector;
    private String stockName;

    /**
     * Create a new ProcessStock object.
     * @param collector
     */
    public StockDataProcessor(Collector<StockData, A, R> collector) {
        this.collector = collector;
    }
    /**
     * Place a description of your method here.
     * @param stockSymbol
     * @return R result
     */
    @Override
    public R apply(String stockSymbol )
    {
        this.stockName = stockSymbol;
        EXCHANGES exchange = EXCHANGES.NYSE;
        String[] exchanges;
        switch ( exchange ) {
            case NASDAQ:
                exchanges = nyseSourceExchanges;
                break;
            case NYSE:
                exchanges = nyseSourceExchanges;
                break;
            default:
                exchanges = nyseSourceExchanges;
                break;
        }

        try ( Stream<String> lines = Files.lines(Paths.get(sourceDirectory + exchanges[0] + stockSymbol.toLowerCase() + datafileExtension)) ) {
            R r = lines.skip(1).map(StockData::new).collect( collector );
            r.setStockSymbol(stockSymbol);
            return r;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Place a description of your method here.
     * @return stockName
     */
    public String getStockName() {
        return stockName;
    }

    /**
     *  Enumeration of possible stock exchanges
     */
    public static enum EXCHANGES {
     /**
     * New York Stock Exchange
     */
    NYSE,
    /**
     * NASDAQ Stock Exchange
     */
    NASDAQ}

}

