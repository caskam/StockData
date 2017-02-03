
import java.util.stream.Collector;
import java.util.stream.Stream;
import static java.util.stream.Collectors.*;
import java.util.List;
import java.util.Map;
import static java.util.function.Function.*;

/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author quix0
 *  @version Feb 2, 2017
 */
public class RunSimulation
{
    //~ Fields ................................................................

    //~ Constructors ..........................................................

    //~Public  Methods ........................................................
    public static void main(String[] args) {
        new RunSimulation().run();
    }
    private void run() {

/*
        Stream.of("bac")
        .parallel()
        .forEach(s-> localDateStockDataList = new ProcessStock(s, ProcessStock.EXCHANGES.NYSE,  Collector.of(
            LocalDateStockDataList::new,
            LocalDateStockDataList::add,
            LocalDateStockDataList::merge ) ) );
*/
        StockDataProcessor<LocalDateStockDataList, LocalDateStockDataList> processor =
            new StockDataProcessor<LocalDateStockDataList, LocalDateStockDataList>(Collector.of(
                LocalDateStockDataList::new,
                LocalDateStockDataList::add,
                LocalDateStockDataList::merge) );

        Map<String, LocalDateStockDataList> mapStocks = Stream.of("bac", "ge", "dis", "ibm", "jnj", "bud")
        .map(processor)
        .collect(toMap(LocalDateStockDataList::getStockSymbol, identity()));

        mapStocks.forEach( (k, v)->System.out.println(k + " = " + v));


    }



}
