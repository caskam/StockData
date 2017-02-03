import java.util.EventListener;

/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author Karl Nicholas
 *  @version Feb 2, 2017
 */
public interface StockDataListener extends StockSymbolProcessor
{
    /**
     * Place a description of your method here.
     * @param stockData
     */
    public void onTick(StockData stockData);
}
