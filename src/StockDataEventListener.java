/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author quix0
 *  @version Feb 4, 2017
 */
public class StockDataEventListener implements StockSymbolListener {
    public LocalDateStockDataList localDateStockDataList;
    private String stockSymbol;
    public StockDataEventListener(String stockSymbol) {
        this.stockSymbol = stockSymbol;
        localDateStockDataList = new LocalDateStockDataList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onTick(StockData stockData)
    {
        localDateStockDataList.add(stockData);
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
    public void setStockSymbol(String stockSymbol)
    {
        this.stockSymbol = stockSymbol;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String returnResults()
    {
        return localDateStockDataList.returnResults();
    }

}
