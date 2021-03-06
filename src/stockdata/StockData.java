package stockdata;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAmount;
import java.util.regex.Pattern;

/**
 * Immutable StockData object from stooq market data download
 *
 * @author Karl Nicholas
 * @version 2017-02-04
 *
 */
public class StockData implements Comparable<StockData>{
	private static final Pattern pattern = Pattern.compile(",");
	private static final String warsawTZString = "+01:00[Europe/Warsaw]";
	private static final TemporalAmount fiveMinutes = Duration.ofMinutes(5);
	private static final ZoneId usEasternTimezone = ZoneId.of("US/Eastern");
    private static final ZoneId warsawTimezone = ZoneId.of("Europe/Warsaw");
	private static final char T = 'T';

	private ZonedDateTime	openDateTime;
    private ZonedDateTime   closeDateTime;
	private BigDecimal		open;
	private BigDecimal		high;
	private BigDecimal		low;
	private BigDecimal		close;
	private Long			volume;
	private Long			openInt;
	/**
	 * Create an immutable StockData object.
	 * @param line String from 5 min stooq market data
	 */
	public StockData(String line) {
		// 2016-12-27,15:35:00,22.71,22.735,22.605,22.665,2648082,0
		String[] tokens = pattern.split(line);
		openDateTime = ZonedDateTime.parse(
		    new StringBuilder(40).append(
		        tokens[0].length()==10?tokens[0]:tokens[0].substring(0, 8)+'0'+tokens[0].substring(8))
		    .append(T).append(tokens[1]).append(warsawTZString)
		);
		openDateTime = ZonedDateTime.ofInstant(openDateTime.toInstant(), usEasternTimezone);
        closeDateTime = openDateTime.plus(fiveMinutes);
		// Create a DecimalFormat that fits your requirements
		this.open = new BigDecimal(tokens[2]);
		this.high = new BigDecimal(tokens[3]);
		this.low = new BigDecimal(tokens[4]);
		this.close = new BigDecimal(tokens[5]);
		this.volume = new Long(tokens[6]);
		this.openInt = new Long(tokens[7]);
	}
	/**
	 * Return ZonedDateTime
	 * @return {@link ZonedDateTime}
	 */
	public ZonedDateTime getOpenDateTime() {
		return openDateTime;
	}
	/**
	 * return open price
	 * @return open price
	 */
	public BigDecimal getOpen() {
		return open;
	}
	/**
	 * return high price of this period
	 * @return high price of this period
	 */
	public BigDecimal getHigh() {
		return high;
	}
	/**
	 * return low price of this period
	 * @return low price of this period
	 */
	public BigDecimal getLow() {
		return low;
	}
	/**
	 * Place a description of your method here.
	 * @return closing price of this tick
	 */
	public BigDecimal getClose() {
		return close;
	}
	/**
	 * return volume of shares traded
	 * @return volume of shares traded
	 */
	public Long getVolume() {
		return volume;
	}
	/**
	 * return open interest
	 * @return open interest
	 */
	public Long getOpenInt() {
		return openInt;
	}
	/**
	 * reduce stockData by finding the lowest low, highest high, sum of volumes, etc.
	 * @param stockData {@link StockData}
	 * @return {@link StockData} reduced results
	 */
	public StockData reduce(StockData stockData) {
	    // set open and close to earliest and latest values
	    if ( openDateTime.compareTo(stockData.openDateTime) > 0 ) {
	        open = stockData.open;
	        openDateTime = stockData.openDateTime;
	    }
	    if ( closeDateTime.compareTo(closeDateTime) < 0 ) {
	        close = stockData.close;
	        closeDateTime = stockData.closeDateTime;
	    }
	    // set high to higher value
	    if ( high.compareTo(stockData.high) < 0 ) {
	        high = stockData.high;
	    }
	    // set low to lower value
	    if ( low.compareTo(stockData.low ) > 0 ) {
	        low = stockData.low;
	    }
	    // sum volumes and openInt
	    volume = Long.sum(volume, stockData.volume);
        openInt = Long.sum(openInt, stockData.openInt);
        // return this
        return this;
	}
	@Override
	/**
	 * Produces a string in the same format as the input string,
	 * but in US/Eastern time zone, therefore exported strings
	 * cannot be re-imported back into the constructor.
	 *
	 * e.g.:
	 *  Date,Time,Open,High,Low,Close,Volume,OpenInt
	 *  2016-12-27,15:35:00,22.71,22.735,22.605,22.665,2648082,0
	 */
	public String toString() {
	    return String.format("%1$tY-%1$tm-%1$te,%1$tH:%1$tM:%1$tS,%2$s,%3$s,%4$s,%5$s,%6$d,%7$d",
	        ZonedDateTime.ofInstant(openDateTime.toInstant(), warsawTimezone),
	        open, high, low, close, volume, openInt );
	}
    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(StockData o)
    {
        return openDateTime.compareTo(o.openDateTime);
    }
    @Override
    public int hashCode()
    {
        return openDateTime.hashCode();
    }
    @Override
    public boolean equals(Object obj)
    {
        return openDateTime.equals(((StockData)obj).openDateTime);
    }
}
