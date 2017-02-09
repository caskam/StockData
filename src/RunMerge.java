
import com.google.common.collect.Streams;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import stockdata.StockData;

/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author quix0
 *  @version Feb 2, 2017
 */
public class RunMerge
{
    static final String[] dirs = {
        "C:/Users/quix0/Downloads/5_us_txt/new/data/5 min/us/nyse stocks/1",
        "C:/Users/quix0/Downloads/5_us_txt/new/data/5 min/us/nyse stocks/2"
    };
    /**
     * Place a description of your method here.
     * @param args not used
     */
    public static void main(String[] args) {
        new RunMerge().run();
    }
    private void run() {
        for ( String dir: dirs) {
            try ( DirectoryStream<Path> files = Files.newDirectoryStream(Paths.get(dir))) {
                Streams.stream(files)
                .forEach(p->{
                    Set<StockData> setStockData = new java.util.HashSet<>();
                    try ( Stream<String>lines = Files.lines(p) ) {
                        lines
                        .skip(1)
                        .map(StockData::new)
                        .collect( Collectors.toCollection(()->setStockData) );
                        lines.close();
                    } catch (IOException e) { throw new RuntimeException(e); }
                    Path oldPath = Paths.get(p.toString().replace("\\new", ""));
                    if ( Files.exists(oldPath)) {
                        try ( Stream<String>lines = Files.lines(oldPath) ){
                            lines
                            .skip(1)
                            .map(StockData::new)
                            .collect( Collectors.toCollection(()->setStockData) );
                            lines.close();
                        } catch (IOException e) { throw new RuntimeException(e); }
                    }
                    try ( BufferedWriter writer = Files.newBufferedWriter(oldPath) ) {
                        writer.write("Date,Time,Open,High,Low,Close,Volume,OpenInt");
                        writer.newLine();
                        setStockData.stream().sorted().forEach(sd->{
                            try {
                                writer.write(sd.toString());
                                writer.newLine();
                           } catch (IOException e) { throw new RuntimeException(e); }
                        });
                        writer.close();
                    }
                    catch (IOException e)
                    {
                        throw new RuntimeException(e);
                    }
                });
                // close
                files.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }


}
