import java.util.*;
import java.io.*;

public class Main {
     static String input_path = "/Users/dileepdomakonda/Documents/DS2/Files";
     static String output_path = "/Users/dileepdomakonda/Documents/DS2/output.csv";
     static String graph_data = "/Users/dileepdomakonda/Documents/DS2/graph_data";
     static Long no_of_records = Long.valueOf(0);
     static int no_of_data_points = 100;
    private static void mergeFiles() throws IOException {
        File dir = new File(input_path);
        PrintWriter pw = new PrintWriter(output_path);
        String[] fileNames = dir.list();
        int c = 0;
        for (String fileName : fileNames) {
            System.out.println("Reading from " + fileName);
            File f = new File(dir, fileName);
            BufferedReader br = new BufferedReader(new FileReader(f));
            //pw.println("Contents of file " + fileName);
            String line = br.readLine();
            while (line != null) {
                if (!line.contains("Txn Hash")) {
                    pw.println(line);
                }
                if (line.contains("Txn Hash") && c == 0) {
                    pw.println(line);
                    c = 1;
                }
                line = br.readLine();
            }
            pw.flush();
        }
        System.out.println("Reading from all files" +
                " in directory " + dir.getName() + " Completed");
        System.out.println(fileNames.length);
    }

    private static NFTTracker setNftTracker(String[] words) {
        NFTTracker nftTracker = new NFTTracker();
        for (int i = 0; i < words.length; i++) {
            //System.out.println(words[i]);
            switch (i) {
                case 0:
                    List<String> txnHash = new ArrayList<>();
                    txnHash.add(words[i]);
                    nftTracker.setTxn_Hash(txnHash);
                    break;
                case 1:
                    List<String> unixTimeStamp = new ArrayList<>();
                    unixTimeStamp.add(words[i]);
                    nftTracker.setUnixTimestamp(unixTimeStamp);
                    break;
                case 2:
                    List<String> dateTime = new ArrayList<>();
                    dateTime.add(words[i]);
                    nftTracker.setDate_Time(dateTime);
                    break;
                case 3:
                    List<String> action = new ArrayList<>();
                    action.add(words[i]);
                    nftTracker.setAction(action);
                    break;
                case 4:
                    List<String> buyer = new ArrayList<>();
                    buyer.add(words[i]);
                    nftTracker.setBuyer(buyer);
                    break;
                case 5:
                    List<String> nft = new ArrayList<>();
                    nft.add(words[i]);
                    nftTracker.setNFT(nft);
                    break;
                case 6:
                    nftTracker.setToken_ID(words[i]);
                    break;
                case 7:
                    List<String> type = new ArrayList<>();
                    type.add(words[i]);
                    nftTracker.setType(type);
                    break;
                case 8:
                    List<String> quantity = new ArrayList<>();
                    quantity.add(words[i]);
                    nftTracker.setQuantity(quantity);
                    break;
                case 9:
                    List<String> price = new ArrayList<>();
                    price.add(words[i]);
                    nftTracker.setPrice(price);
                    break;
                case 10:
                    List<String> market = new ArrayList<>();
                    market.add(words[i]);
                    nftTracker.setMarket(market);
                    break;
                default:
                    System.out.println("Error****  !! at loading data at setNftTracker");
                    break;
            }

        }
        nftTracker.setNoOfTransactions(1);
        return nftTracker;
    }

    private static HashMap<String, NFTTracker> loadData(Long interval) throws IOException {

        HashMap<String, NFTTracker> map = new HashMap<>();
        Scanner sc = new Scanner(new File(output_path));

        while (sc.hasNextLine()) {
            if(interval == 0)
                break;
            String line = sc.nextLine();
            String words[] = line.split("\",\"");
            words[0] = words[0].substring(1); // remove first character of first string
            words[words.length - 1] = words[words.length - 1].substring(0, words[words.length - 1].length() - 1); // remove last character of last string

            NFTTracker nftTracker = setNftTracker(words);

            if (!map.containsKey(nftTracker.getToken_ID())) {
                map.put(nftTracker.getToken_ID(), nftTracker);
            } else {
                NFTTracker nftTrackerStored = map.get(nftTracker.getToken_ID());
                nftTrackerStored.getTxn_Hash().addAll(nftTracker.getTxn_Hash());
                nftTrackerStored.getUnixTimestamp().addAll(nftTracker.getUnixTimestamp());
                nftTrackerStored.getDate_Time().addAll(nftTracker.getDate_Time());
                nftTrackerStored.getAction().addAll(nftTracker.getAction());
                nftTrackerStored.getBuyer().addAll(nftTracker.getBuyer());
                nftTrackerStored.getNFT().addAll(nftTracker.getNFT());
                nftTrackerStored.getType().addAll(nftTracker.getType());
                nftTrackerStored.getQuantity().addAll(nftTracker.getQuantity());
                nftTrackerStored.getPrice().addAll(nftTracker.getPrice());
                nftTrackerStored.getMarket().addAll(nftTracker.getMarket());
                nftTrackerStored.setNoOfTransactions(nftTrackerStored.getNoOfTransactions() + 1);
            }
            interval--;
        }
        return map;
    }

    public static void main(String[] args) throws IOException {

        File f = new File(output_path);
        if (!f.exists() && !f.isDirectory()) {
            mergeFiles();
        }

        Scanner sc1 = new Scanner(new File(output_path));

        if(no_of_records == 0) {
            while (sc1.hasNextLine()) {
                sc1.nextLine();
                no_of_records++;
            }
        }

        Long interval = no_of_records / no_of_data_points;
        List<String> bubbleSortTime = new ArrayList<>();
        List<String> quickSortTime = new ArrayList<>();
        List<String> radixSortTime = new ArrayList<>();

        for (int i = 1; i <= no_of_data_points ; i++) {
            HashMap<String, NFTTracker> map = loadData(i * interval);
            List<NFTTracker> nftTrackerList = new ArrayList<>();
            for (Map.Entry<String, NFTTracker> ele : map.entrySet()) {
                if (ele.getKey().equals("Token ID")) {
                    //System.out.println("token....... continue");
                    continue;
                }
                nftTrackerList.add(ele.getValue());
            }

            //Collections.sort(nftTrackerList, new TXnComparator());

            long startTime, endTime;
            long bubbleSumTime = 0;
            /*for (int k = 0; k < 100; k++) {
                BubbleSort bubbleSort = new BubbleSort();
                startTime = System.currentTimeMillis();
                List<NFTTracker> nftTrackerList_bubblesort = bubbleSort.start(nftTrackerList);
                endTime = System.currentTimeMillis();
                //System.out.println("Bubble Sort execution time: " + (endTime - startTime));
                bubbleSumTime = bubbleSumTime + (endTime - startTime);
            }
            bubbleSortTime.add(bubbleSumTime);*/

            long quickSumTime = 0;
            for (int k = 0; k < 100; k++) {
                QuickSort quickSort = new QuickSort();
                startTime = System.currentTimeMillis();
                List<NFTTracker> nftTrackerList_quicksort = quickSort.start(nftTrackerList);
                endTime = System.currentTimeMillis();
                //System.out.println("Quick Sort execution time: " + (endTime - startTime));
                quickSumTime = quickSumTime + (endTime - startTime);
            }
            Double pq = quickSumTime * 1.0 / 100;
            pq = pq * 1000000;
            String sq = String.format("%.1f", pq);
            quickSortTime.add(sq);

            long radixSumTime = 0;
            //int x = nftTrackerList.size();
            for (int k = 0; k < 100; k++) {
                Radix radix = new Radix();
                startTime = System.currentTimeMillis();
                List<NFTTracker> nftTrackerList_radix = radix.start(nftTrackerList);
                Collections.reverse(nftTrackerList_radix);
                endTime = System.currentTimeMillis();
                //System.out.println("Radix Sort execution time: " + (endTime - startTime));
                radixSumTime = radixSumTime + (endTime - startTime);
            }
            Double pr = radixSumTime * 1.0 / 100;
            pr = pr * 1000000;
            String sr = String.format("%.1f", pr);
            radixSortTime.add(sr);
        }

        Graph g = new Graph();
        g.createGraph(bubbleSortTime, quickSortTime, radixSortTime, graph_data, no_of_records, no_of_data_points);
    }
}
