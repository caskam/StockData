
import com.google.common.collect.Streams;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.stream.Stream;
import processors.LocalDateStockDataList;
import stockdata.StockData;
import stockdata.StockDataProcessorInterface;

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
     * @param args not used
     */
    public static void main(String[] args) {
        new RunStockDataProcessor().run();
    }
    private void run() {
        Object o = new Object();

/*
        Map<String, StockDataProcessorInterface> r2 =  Stream.of("bac", "ge", "dis", "ibm", "jnj", "bud")
        .parallel()
//        .map(LocalDateStockDataList::new)
        .map(LocalDateStatistics::new)
        .map(StockDataSource::new)
        .map(StockDataSource::process)
        .collect(toMap(p->p.getStockSymbol(), identity()));

        r2.forEach((k,v)->System.out.println(k + " = " + v.returnResults()));
*/
        // p->p.getFileName().toString()).filter(s->!(s.contains("_")||s.contains("-"))).map(s->s.replace(".us.txt", "")
        Function<Path, String> getStockSymbol = p->p.getFileName().toString().replace(".us.txt", "");

        try ( DirectoryStream<Path> files = Files.newDirectoryStream(Paths.get("C:/Users/quix0/Downloads/5_us_txt/data/5 min/us/nyse stocks/1"))) {
            try ( BufferedWriter writer = Files.newBufferedWriter(Paths.get("C:/Users/quix0/Downloads/12stocks.txt"))) {
                StockDataProcessorInterface processor = new LocalDateStockDataList(getStockSymbol);
                Streams.stream(files)
                .forEach(p->{
                    try ( Stream<String> lines = Files.lines(p) ) {
                        processor.process(lines.skip(1).map(StockData::new));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

//                .parallel()
//              .map(getStockSymbol)
//              .map(LocalDateStockDataList::new)
//              .map(p->new LocalDateStatistics(getStockSymbol))    // processor (String StockSymbolName)
//              .map(p->StockDataSource.process(p));    // execute(should have Stream<String>) process function
/*
              .filter(processor->processor.returnResults().contains("count=12"))
//              .map(p->p.getStockSymbol())
              .forEach(processor -> {
                    try {
                        synchronized(o) {
                            writer.write(processor.getStockSymbol());
                            writer.newLine();
                            writer.write(processor.returnResults());
                            writer.newLine();
                        }
                    } catch (IOException e) { throw new RuntimeException(e); }
                });
*/
//              .collect(toList());
//              .collect(toMap(p->p.getStockSymbol(), identity()))
//              .forEach((s)->System.out.println(s.getStockSymbol() + " = " + s.returnResults()));
//            .forEach((s)->System.out.println(s.getStockSymbol()));

            }
        } catch (IOException e) { throw new RuntimeException(e); }
    }


}
