package results;

import com.google.common.math.Stats;
import stockdata.StockResults;

/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author quix0
 *  @version Feb 17, 2017
 */
public class StatResults implements StockResults
{
    //~ Fields ................................................................
    private String stockSymbol;
    private Stats volumeStats;
    private Stats highStats;
    private Stats lowStats;
    //~ Constructors ..........................................................
    public StatResults(
        String stockSymbol,
        Stats volumeStats,
        Stats highStats,
        Stats lowStats)
    {
        super();
        this.stockSymbol = stockSymbol;
        this.volumeStats = volumeStats;
        this.highStats = highStats;
        this.lowStats = lowStats;
    }
    //~Public  Methods ........................................................
    /**
     * {@inheritDoc}
     */
    @Override
    public String getStockSymbol()
    {
        return stockSymbol;
    }
    public Stats getVolumeStats()
    {
        return volumeStats;
    }
    public Stats getHighStats()
    {
        return highStats;
    }
    public Stats getLowStats()
    {
        return lowStats;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public StatResults getResults() {
        return this;
    }
}
