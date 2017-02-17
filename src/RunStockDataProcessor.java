
import com.google.common.collect.Streams;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import processors.LocalDateStatistics;
import results.StatResults;
import stockdata.StockData;
import stockdata.StockDataProcessor;

/**
 * Write a one-sentence summary of your class here. Follow it with additional
 * details about its purpose, what abstraction it represents, and how to use it.
 *
 * @author quix0
 * @version Feb 2, 2017
 */
public class RunStockDataProcessor
{
    /**
     *
     */
    private static final String STOCK_FILES = "C:/Users/quix0/Downloads/5_us_txt/data/5 min/us/nyse stocks/1";
    private static final String RESULT_FILE = "C:/Users/quix0/Downloads/12stocks.txt";


    /**
     * Place a description of your method here.
     *
     * @param args
     *            not used
     */
    public static void main(String[] args)
    {
        new RunStockDataProcessor().run();
    }


    private void run()
    {
        Object o = new Object();

        Function<Path, String> getStockSymbol = p -> p.getFileName().toString().replace(".us.txt", "");

        try ( BufferedWriter writer = Files.newBufferedWriter(Paths.get(RESULT_FILE))) {
            try ( DirectoryStream<Path> files = Files.newDirectoryStream( Paths.get( STOCK_FILES))) {
                Streams.stream(files)
                .parallel()
                .map(file -> {
                    try (Stream<String> lineStream = Files.lines(file)) {
                        StockDataProcessor processor = new LocalDateStatistics(getStockSymbol.apply(file));
                        processor.process(lineStream.skip(1).map(StockData::new));
                        lineStream.close();
                        return processor;
                    } catch (IOException e) { throw new RuntimeException(e); }
                })
//                .filter(results -> results.returnResults().contains("count=24"))
                .map(results->(StatResults)results.returnResults())
                .collect(Collectors.groupingBy(r->r.getVolumeStats().count(), Collectors.counting()))
                .forEach((k, v)->System.out.println(k +":"+v));
/*
                .forEach(results -> {
                    try {
                        synchronized (o) {
                            writer.write(results.getStockSymbol());
                            writer.newLine();
                            writer.write(results.returnResults());
                            writer.newLine();
                        }
                    } catch (IOException e) { throw new RuntimeException(e); }
                });
*/
            } catch (IOException e) { throw new RuntimeException(e); }
        } catch (IOException e) { throw new RuntimeException(e); }
    }

}
