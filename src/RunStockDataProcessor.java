
import java.util.stream.Stream;
import static java.util.stream.Collectors.*;
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
public class RunStockDataProcessor
{
    /**
     * Place a description of your method here.
     * @param args
     */
    public static void main(String[] args) {
        new RunStockDataProcessor().run();
    }
    private void run() {

        Map<String, StockDataProcessorInterface> r2 =  Stream.of("bac", "ge", "dis", "ibm", "jnj", "bud")
        .parallel()
        .map(LocalDateStockDataList::new)
        .map(StockDataSource::new)
        .map(StockDataSource::process)
        .collect(toMap(p->p.getStockSymbol(), identity()));

        r2.forEach((k,v)->System.out.println(k + " = " + v.returnResults()));


    }


}
