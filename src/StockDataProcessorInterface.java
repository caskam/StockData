import java.util.function.Consumer;

/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author quix0
 *  @version Feb 2, 2017
 */
public interface StockDataProcessorInterface extends Consumer<StockData> {
    /**
     * Place a description of your method here.
     * @return StockSymbol
     */
    public String getStockSymbol();
    /**
     * Place a description of your method here.
     * @param stockData parsed data tick
     */
    void accept(StockData stockData);
    /**
     * Place a description of your method here.
     * @return results
     */
    public String returnResults();
}
