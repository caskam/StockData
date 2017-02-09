
import com.google.common.collect.Streams;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import processors.LocalDateStatistics;
import stockdata.StockDataSource;

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
        try ( DirectoryStream<Path> files = Files.newDirectoryStream(Paths.get("C:/Users/quix0/Downloads/5_us_txt/data/5 min/us/nyse stocks/1"))) {
            try ( BufferedWriter writer = Files.newBufferedWriter(Paths.get("C:/Users/quix0/Downloads/12stocks.txt"))) {
                Streams.stream(files)
                .parallel()
                .map(p->p.getFileName().toString()).filter(s->!(s.contains("_")||s.contains("-"))).map(s->s.replace(".us.txt", ""))
//              .map(LocalDateStockDataList::new)
              .map(LocalDateStatistics::new)
              .map(StockDataSource::new)
              .map(StockDataSource::process)
              .filter(processor->processor.returnResults().contains("count=12"))
//              .map(p->p.getStockSymbol())
              .forEach(processor -> {
                    try
                    {
                        synchronized(o) {
                            writer.write(processor.getStockSymbol());
                            writer.newLine();
                            writer.write(processor.returnResults());
                            writer.newLine();
                        }
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                });
//              .collect(toList());
//              .collect(toMap(p->p.getStockSymbol(), identity()))
//              .forEach((s)->System.out.println(s.getStockSymbol() + " = " + s.returnResults()));
//            .forEach((s)->System.out.println(s.getStockSymbol()));

            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }


}
