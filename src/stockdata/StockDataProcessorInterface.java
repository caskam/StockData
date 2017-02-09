package stockdata;
import java.util.function.Consumer;

/**
 *  A StockDatea processor is a {@link Consumer} of {@link StockData}.
 *
 *  A processor must also implement a getter for a String getter for the Stock Symbol.
 *
 *  @author Karl Nicholas
 *  @version 2017-02-01
 */
public interface StockDataProcessorInterface extends Consumer<StockData> {
    /**
     * Place a description of your method here.
     * @return StockSymbol
     */
    public String getStockSymbol();
    /**
     * Place a description of your method here.
     * @return results
     */
    public String returnResults();
}
