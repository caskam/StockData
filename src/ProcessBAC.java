
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * Read 5 minute tick stock data from http://stooq.com/db/d/?b=5_us_txt
 * assumed at this point to be data for a 5 minute period
 * Currently hard coded to read specific downloaded data.
 *
 * This is just a playground project for practicing Java Lambda language features.
 *
 *  @author Karl Nicholas
 *  @version Jan 19, 2017
 */
public class ProcessBAC {
    /**
     * main method for running application
     * @param args command line strings -- ignored
     */
    public static void main(String[] args) {
        new ProcessBAC().run();
    }
    private void run() {

        LocalDateStockDataList localDateStockDataList;

        try ( Stream<String> lines = Files.lines(Paths.get("C:/Users/quix0/Downloads/5_us_txt/data/5 min/us/nyse stocks/1/bac.us.txt")) ) {
            localDateStockDataList = lines.skip(1).map(StockData::new)
                .collect(
                    Collector.of(
                        LocalDateStockDataList::new,
                        LocalDateStockDataList::add,
                        LocalDateStockDataList::merge
                )
            );
            lines.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println( localDateStockDataList.assertSorted() );
        System.out.println(localDateStockDataList);

        // print out reduced data
        localDateStockDataList.streamLocalDateStockData()
            .forEach((localDateStockData)->System.out.println(localDateStockData.getLocalDateStockData()));

//        localDateStockDataList.streamLocalDateStockDataList().map(LocalDateStockData::getStockDataStats).forEach(System.out::println);

    }

}

/*
Stats stats = lines.skip(1).map(StockData::new)
.collect(
    collectingAndThen(
        Collector.of(
            StatsAccumulator::new,
            (sa, stockData)->sa.add(stockData.getVolume()),
            (left, right)-> { left.addAll(right.snapshot()); return left;}
        ),
        StatsAccumulator::snapshot
    )
);

System.out.println(stats);
*/
/*
Map<LocalDate, List<StockData>> myMap = lines.skip(1).map(StockData::new)
.collect( groupingBy( d->d.getDateTime().toLocalDate() ) );

myMap.forEach( (key, value)->{
    System.out.println(
            "Key = " + key +
            " Min = " + value.stream().map(StockData::getOpen).min( BigDecimal::compareTo ) +
            " Max = " + value.stream().map(StockData::getOpen).max( BigDecimal::compareTo ));
} );
*/
/*
//Function<StockData, LocalDate> classifier = d->d.getDateTime().toLocalDate();
//Supplier<LocalDateStockDataMap> supplier = LocalDateStockDataMap::new;
//BiConsumer<LocalDateStockDataMap,Map.Entry<LocalDate,List<StockData>>> accumulator = LocalDateStockDataMap::add;
//BinaryOperator<LocalDateStockDataMap> combiner = LocalDateStockDataMap::merge;

LocalDateStockDataList list = lines.skip(1).map(StockData::new)
    .collect( groupingBy(stockData->stockData.getDateTime().toLocalDate()))
    .entrySet().stream()
    .collect(
        Collector.of(
            LocalDateStockDataList::new,
            LocalDateStockDataList::add,
            LocalDateStockDataList::merge
        )
    );

System.out.println( list.assertSorted() );
System.out.println(list);
*/
/*
LocalDateStockDataList list = lines.skip(1).map(StockData::new)
    .collect(
        Collector.of(
            LocalDateStockDataList::new,
            LocalDateStockDataList::add,
            LocalDateStockDataList::merge
    )
);

*/
/*
lines.skip(1).map(StockData::new).forEach( stockData->list.add(stockData));
*/
