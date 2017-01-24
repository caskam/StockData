
import java.math.BigDecimal;
import java.util.IntSummaryStatistics;
import java.util.LongSummaryStatistics;

// -------------------------------------------------------------------------
/**
 *  java.util.Collector compatible class to hold java.util.SummaryStatistics
 *  for all StockData values. Generated and stored by LocalDataStockData.
 *  See LocalDataStockData::generateStats
 *
 *  @author Karl Nicholas
 *  @version Jan 19, 2017
 */
public class StockDataStats
{
    /**
     * The amount the IntSummaryStatistics have been multiplied by.
     */
    public static final BigDecimal MULTIPLIER = new BigDecimal(10000);
    //~ Fields ................................................................
    private IntSummaryStatistics openStats;
    private IntSummaryStatistics closeStats;
    private IntSummaryStatistics highStats;
    private IntSummaryStatistics lowStats;
    private LongSummaryStatistics volumeStats;
    private LongSummaryStatistics openIntStats;

    //~ Constructors ..........................................................
    // ----------------------------------------------------------
    /**
     * Create a new StockDataStats object.
     */
    public StockDataStats() {
        openStats = new IntSummaryStatistics();
        closeStats = new IntSummaryStatistics();
        highStats = new IntSummaryStatistics();
        lowStats = new IntSummaryStatistics();
        volumeStats = new LongSummaryStatistics();
        openIntStats = new LongSummaryStatistics();
    }

    //~Public  Methods ........................................................
    // ----------------------------------------------------------
    /**
     * Add a new StockData point to computations
     * @param stockData to add
     */
    public void add( StockData stockData ) {
        openStats.accept(stockData.getOpen().multiply(MULTIPLIER).intValue());
        closeStats.accept(stockData.getClose().multiply(MULTIPLIER).intValue());
        highStats.accept(stockData.getHigh().multiply(MULTIPLIER).intValue());
        lowStats.accept(stockData.getLow().multiply(MULTIPLIER).intValue());
        volumeStats.accept(stockData.getVolume().longValue());
        openIntStats.accept(stockData.getOpenInt().longValue());
    }
    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * @param stockDataStats to merge
     * @return StockDataStats this
     */
    public StockDataStats merge( StockDataStats stockDataStats ) {
        openStats.combine(stockDataStats.getOpenStats());
        closeStats.combine(stockDataStats.getCloseStats());
        highStats.combine(stockDataStats.getHighStats());
        lowStats.combine(stockDataStats.getLowStats());
        volumeStats.combine(stockDataStats.getVolumeStats());
        openIntStats.combine(stockDataStats.getOpenIntStats());
        return this;
    }
    // ----------------------------------------------------------
    /**
     * Return StockData::getOpen statistics.
     * @return StockData::getOpen
     */
    public IntSummaryStatistics getOpenStats()
    {
        return openStats;
    }
    // ----------------------------------------------------------
    /**
     * Return StockData::getClose statistics.
     * @return StockData::getClose statistics.
     */
    public IntSummaryStatistics getCloseStats()
    {
        return closeStats;
    }
    // ----------------------------------------------------------
    /**
     * return IntSummaryStatistics of StockData::getHigh
     * @return IntSummaryStatistics of StockData::getHigh
     */
    public IntSummaryStatistics getHighStats()
    {
        return highStats;
    }
    // ----------------------------------------------------------
    /**
     * return IntSummaryStatistics of StockData::getLow
     * @return IntSummaryStatistics of StockData::getLow
     */
    public IntSummaryStatistics getLowStats()
    {
        return lowStats;
    }
    // ----------------------------------------------------------
    /**
     * return LongSummaryStatistics of StockData::getVolume
     * @return LongSummaryStatistics of StockData::getVolume
     */
    public LongSummaryStatistics getVolumeStats()
    {
        return volumeStats;
    }
    // ----------------------------------------------------------
    /**
     * LongSummaryStatistics of StockData::getOpenInt
     * @return LongSummaryStatistics of StockData::getOpenInt
     */
    public LongSummaryStatistics getOpenIntStats()
    {
        return openIntStats;
    }

    @Override
    public String toString()
    {
        return "StockDataStats [openStats=" + openStats + ", closeStats="
            + closeStats + ", highStats=" + highStats + ", lowStats=" + lowStats
            + ", volumeStats=" + volumeStats + ", openIntStats=" + openIntStats
            + "]";
    }
}
