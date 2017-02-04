
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
//        StockDataEventSource eventSource = new StockDataEventSource( new StockDataListEventListener() );


//      Map<String, StockDataListener> listStocks =
//      List<StockDataListener> listStocks =

//        Map<String, StockDataListener> mapStocks =
/*
        List<StockDataEventSource<StockSymbolListener>> list = Stream.of("bac", "ge", "dis", "ibm", "jnj", "bud")
        .map(StockDataEventListener::new)
        .map(StockDataEventSource::new)
        .collect(toList());

        List<StockSymbolProcessor> r = list
        .parallelStream()
        .map(StockDataEventSource::process)
        .collect(toList());
*/
/*
        List<StockSymbolProcessor> r2 =  Stream.of("bac", "ge", "dis", "ibm", "jnj", "bud")
        .map(StockDataEventListener::new)
        .map(StockDataEventSource::new)
        .map(StockDataEventSource::process)
        .collect(toList());
*/
        Map<String, StockSymbolProcessor> r2 =  Stream.of("bac", "ge", "dis", "ibm", "jnj", "bud")
        .parallel()
        .map(StockDataEventListener::new)
        .map(StockDataEventSource::new)
        .map(StockDataEventSource::process)
        .collect(toMap(p->p.getStockSymbol(), identity()));

        r2.forEach((k,v)->System.out.println(k + " = " + v.returnResults()));

//        .map();

//        .collect(toMap(sdl->sdl.getStockSymbol(), identity()));
//        .collect(toMap(sdl->sdl.getStockSymbl(), identity()));


//        mapStocks.forEach( (k, v)->System.out.println(k + " = " + v.returnResults()));
//        listStocks.forEach(sdl->System.out.println(sdl.getStockSymbol() + " " + sdl));

    }


}
