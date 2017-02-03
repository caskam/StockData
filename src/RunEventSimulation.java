
import java.util.stream.Collector;
import java.util.stream.Stream;
import static java.util.stream.Collectors.*;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import static java.util.function.Function.*;

/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author quix0
 *  @version Feb 2, 2017
 */
public class RunEventSimulation
{
    /**
     * Place a description of your method here.
     * @param args
     */
    public static void main(String[] args) {
        new RunEventSimulation().run();
    }
    private void run() {

/*
        StockDataProcessor<LocalDateStockDataList, LocalDateStockDataList> processor =
            new StockDataProcessor<LocalDateStockDataList, LocalDateStockDataList>(Collector.of(
                LocalDateStockDataList::new,
                LocalDateStockDataList::add,
                LocalDateStockDataList::merge) );
*/
        class StockDataListEventListener implements StockDataListener {
            public LocalDateStockDataList localDateStockDataList;
            private String stockSymbol;
            public StockDataListEventListener() {
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
//        StockDataEventSource eventSource = new StockDataEventSource( new StockDataListEventListener() );


//      Map<String, StockDataListener> listStocks =
//      List<StockDataListener> listStocks =

        Map<String, StockDataListener> mapStocks =
        Stream.of("bac", "ge", "dis", "ibm", "jnj", "bud")
        .map(StockDataEventSource::new)
        .map(sdes->sdes.process(new StockDataListEventListener()))
        .collect(toMap(sdl->sdl.getStockSymbol(), identity()));
//        .collect(toMap(sdl->sdl.getStockSymbl(), identity()));

        mapStocks.forEach( (k, v)->System.out.println(k + " = " + v.returnResults()));
//        listStocks.forEach(sdl->System.out.println(sdl.getStockSymbol() + " " + sdl));

    }


}
