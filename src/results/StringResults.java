package results;

import stockdata.StockResults;

/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author quix0
 *  @version Feb 17, 2017
 */
public class StringResults
    implements StockResults
{
    //~ Fields ................................................................
    private String stockSymbol;
    private String results;

    //~ Constructors ..........................................................
    public StringResults(String stockSymbol, String results)
    {
        this.stockSymbol = stockSymbol;
        this.results = results;
    }
    //~Public  Methods ........................................................

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStockSymbol()
    {
        // TODO Auto-generated method stub
        return null;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public StringResults getResults() {
        return this;
    }

}
