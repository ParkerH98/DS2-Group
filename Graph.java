import java.io.*;
import java.util.List;

public class Graph {


    public static void createGraph(List<String> bubbleSortTime, List<String> quickSortTime, List<String> radixSortTime,
                                   String graph_data, Long total_records, int total_datapoints) throws IOException {
        File theDir = new File(graph_data);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
        //Writer bubbleWriter = new FileWriter(graph_data + "/bubblesortdata.txt", false);
        String quickdata = graph_data + "/quicksortdata.txt";
        String radixdata = graph_data + "/radixsortdata.txt";
        Writer quickWriter = new FileWriter(quickdata, false);
        Writer radixWriter = new FileWriter(radixdata, false);

        for(int i = 0; i < total_datapoints; i++) {
            /*bubbleWriter.write(String.valueOf(bubbleSortTime.get(i).intValue()));
            if(i != bubbleSortTime.size() - 1)
                bubbleWriter.write(",");*/
            quickWriter.write(quickSortTime.get(i));
            if(i != quickSortTime.size() - 1)
                quickWriter.write(",");
            radixWriter.write(radixSortTime.get(i));
            if(i != radixSortTime.size() - 1)
                radixWriter.write(",");
        }
        //bubbleWriter.close();
        quickWriter.close();
        radixWriter.close();
        String Command = "python /Users/dileepdomakonda/graph.py "+total_records.toString()+" "+
                total_datapoints+ " "+
                quickdata + " " + radixdata;
        //Process p = Runtime.getRuntime().exec(Command);
        ProcessBuilder processBuilder = new ProcessBuilder();

        // -- Linux --

        // Run a shell command
        processBuilder.command("bash", "-c", Command);

        // Run a shell script
        //processBuilder.command("path/to/hello.sh");

        // -- Windows --

        // Run a command
        //processBuilder.command("cmd.exe", "/c", "dir C:\\Users\\mkyong");

        // Run a bat file
        //processBuilder.command("C:\\Users\\mkyong\\hello.bat");

        try {

            Process process = processBuilder.start();

            StringBuilder output = new StringBuilder();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
                System.out.println(output);
                //System.exit(0);
            } else {
                //abnormal...
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
