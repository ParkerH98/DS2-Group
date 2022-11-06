import java.util.*;
import java.io.*;

public class Query1 {
     static String input_path = "./";
     static String merged_path = "./Quey1_merged.csv";
     //static String graph_data = "/Users/dileepdomakonda/Documents/DS2/graph_data";
     static String output_path = "./";
     static Long no_of_records = Long.valueOf(0);
     static int no_of_data_points = 100;
    private static void mergeFiles() throws IOException {
        File dir = new File(input_path);
        Writer pw = new FileWriter(merged_path, false);
        String[] fileNames = dir.list();

        int c = 0;
        for (String fileName : fileNames) {
            //System.out.println("Reading from " + fileName);
            if(!fileName.contains("csv") || fileName.equals(merged_path))
                continue;

            File f = new File(dir, fileName);
            BufferedReader br = new BufferedReader(new FileReader(f));
            //pw.println("Contents of file " + fileName);
            String line = br.readLine();
            while (line != null) {
                if (!line.contains("Txn Hash")) {
                    pw.write(line+"\n");
                    no_of_records++;
                }
                if (line.contains("Txn Hash") && c == 0) {
                    pw.write(line+"\n");
                    c = 1;
                }
                line = br.readLine();

            }
            pw.flush();
        }
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
        Scanner sc = new Scanner(new FileReader(merged_path));
        String line = sc.nextLine();

        while (line != null) {
            if(interval == 0)
                break;
             
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
            line = sc.nextLine();
        }
        return map;
    }

    private static HashMap<String, NFTTracker> loadData() throws IOException {

        HashMap<String, NFTTracker> map = new HashMap<>();

        //String line = sc.nextLine();
        File dir = new File(input_path);
        String[] fileNames = dir.list();

        for (String fileName : fileNames) {
            if(!fileName.contains("csv"))
                continue;

            File f = new File(dir, fileName);
            Scanner sc = new Scanner(new FileReader(f));
            //pw.println("Contents of file " + fileName);
            //String line = br.readLine();
            while (sc.hasNextLine()) {
                //if(interval == 0)
                // break;
                String line = sc.nextLine();

                if (line.contains("Txn Hash"))
                    continue;

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
                //interval--;
                //line = sc.nextLine();
            }
        }
        return map;
    }

    public static void main(String[] args) throws IOException {

        //System.out.println(args[0]);
        //System.out.println(args[1]);

        if (args.length == 1) {
            input_path = args[0];
        }

        //mergeFiles();



        //Long interval = no_of_records / no_of_data_points;
        //List<String> bubbleSortTime = new ArrayList<>();
        //List<String> quickSortTime = new ArrayList<>();
        //List<String> radixSortTime = new ArrayList<>();
        List<NFTTracker> nftTrackerList_radix = null;
        List<NFTTracker> nftTrackerList_quicksort = null;
        System.out.println("Initializations Done.. !");
        //System.out.println("Preparing for Asymptotic runs..");
        //System.out.println("Asymptotic runs started.. would take 2 to 3 minutes");

        //for (int i = 1; i <= no_of_data_points ; i++) {
            //HashMap<String, NFTTracker> map = loadData(i * interval);
            HashMap<String, NFTTracker> map = loadData();
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
            //long bubbleSumTime = 0;
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
            //for (int k = 0; k < 100; k++) {
                QuickSort quickSort = new QuickSort();
                startTime = System.currentTimeMillis();
                nftTrackerList_quicksort = quickSort.start(nftTrackerList);
                endTime = System.currentTimeMillis();
                //System.out.println("Quick Sort execution time: " + (endTime - startTime));
                quickSumTime = quickSumTime + (endTime - startTime);
            //}
            //Double pq = (quickSumTime * 1.0);
            quickSumTime  = quickSumTime * 1000000;
            //pq = pq * 1000000;
            //String sq = String.format("%.1f", pq);
            System.out.println("Running time for QuickSort " + quickSumTime + " nano seconds.");

            long radixSumTime = 0;

            //int x = nftTrackerList.size();
            //for (int k = 0; k < 100; k++) {
                Radix radix = new Radix();
                startTime = System.currentTimeMillis();
                nftTrackerList_radix = radix.start(nftTrackerList);
                //Collections.reverse(nftTrackerList_radix);
                endTime = System.currentTimeMillis();
                //System.out.println("Radix Sort execution time: " + (endTime - startTime));
                radixSumTime = radixSumTime + (endTime - startTime);
            //}
            //Double pr = (radixSumTime * 1.0);
            radixSumTime = radixSumTime * 1000000;
            //String sr = String.format("%.1f", pr);
            System.out.println("Running time for RadixSort " + radixSumTime + " nano seconds.");

        /*System.out.println("Asymptotic runs.. Completed");
        System.out.println("Preparing Graphs..");*/

        /*Graph g = new Graph();
        g.createGraph(quickSortTime, radixSortTime, no_of_records, no_of_data_points);
        System.out.println("Graphs Created..");
        System.out.println("check, quickSort.png and radixsort.png in the current directory");*/
        writeOutputPath(nftTrackerList_quicksort, nftTrackerList_radix);

    }
    private static void writeOutputPath(List<NFTTracker> nftTrackerList_quicksort, List<NFTTracker> nftTrackerList_radix) throws IOException {

        String quickdataoutput = output_path + "Query1_quicksortoutput.csv";
        String radixdataoutput = output_path + "Query1_radixsortoutput.csv";
        Writer quickWriter = new FileWriter(quickdataoutput, false);
        Writer radixWriter = new FileWriter(radixdataoutput, false);
        String prev = null;
        quickWriter.write("Token ID : Txn Hash,Date Time (UTC),Buyer,NFT,Type,Quantity,Price\n\n");
        for(int i = 0; i < nftTrackerList_quicksort.size(); i++) {
            NFTTracker curr = nftTrackerList_quicksort.get(i);
            if(prev == null || !prev.equals(curr.getToken_ID())) {
                quickWriter.write("\n");
                quickWriter.write("Token Id : " + curr.getToken_ID() + " (frequency = " + curr.getNoOfTransactions() + " )" + "\n\n");
                prev = curr.getToken_ID();
            }
            for(int k = 0; k < curr.getNoOfTransactions(); k++) {

                quickWriter.write(curr.getToken_ID()  + " : " + curr.getTxn_Hash().get(k)
                        + "," +
                        "\"" + curr.getDate_Time().get(k) + "\"" + "," + "\"" + curr.getBuyer().get(k) + "\""
                        + "," +
                        "\"" + curr.getNFT().get(k) + "\"" + "," + "\"" + curr.getType().get(k) + "\""
                        + "," + "\"" + curr.getQuantity().get(k) + "\"" + "," +
                        "\"" + curr.getPrice().get(k) + "\"" + "\n");
            }

        }

        radixWriter.write("Token ID : Txn Hash,Date Time (UTC),Buyer,NFT,Type,Quantity,Price\n\n");
        Collections.reverse(nftTrackerList_radix);
        prev = null;
        for(int i = 0; i < nftTrackerList_radix.size(); i++) {
            NFTTracker curr = nftTrackerList_radix.get(i);
            if(prev == null || !prev.equals(curr.getToken_ID())) {
                radixWriter.write("\n");
                radixWriter.write("Token Id : " + curr.getToken_ID() + " (frequency = " + curr.getNoOfTransactions() + " )" + "\n\n");
                prev = curr.getToken_ID();
            }
            for(int k = 0; k < curr.getNoOfTransactions(); k++) {
                radixWriter.write(curr.getToken_ID()  + " : " + curr.getTxn_Hash().get(k)
                        + "," +
                        "\"" + curr.getDate_Time().get(k) + "\"" + "," + "\"" + curr.getBuyer().get(k) + "\""
                        + "," +
                        "\"" + curr.getNFT().get(k) + "\"" + "," + "\"" + curr.getType().get(k) + "\""
                        + "," + "\"" + curr.getQuantity().get(k) + "\"" + "," +
                        "\"" + curr.getPrice().get(k) + "\"" + "\n");
            }

        }
        System.out.println("Output saved to current directory " + quickdataoutput + "  and  " + radixdataoutput);
    }
}

class Radix {
    static int getMax(int arr[], int n) {
        int mx = arr[0];
        for (int i = 1; i < n; i++)
            if (arr[i] > mx)
                mx = arr[i];
        return mx;
    }

    static void countSort(int arr[], int n, int exp, List<NFTTracker> nftTrackerList) {
        int output[] = new int[n];
        NFTTracker[] nftTrackersOutput = new NFTTracker[n];
        int i;
        int count[] = new int[10];
        Arrays.fill(count, 0);
        for (i = 0; i < n; i++)
            count[(arr[i] / exp) % 10]++;

        for (i = 1; i < 10; i++)
            count[i] += count[i - 1];

        for (i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            nftTrackersOutput[count[(arr[i] / exp) % 10] - 1] = nftTrackerList.get(i);
            count[(arr[i] / exp) % 10]--;
        }
        nftTrackerList.clear();

        for (i = 0; i < n; i++) {
            arr[i] = output[i];
            nftTrackerList.add(nftTrackersOutput[i]);
        }
    }

    static void radixsort(int arr[], int n, List<NFTTracker> nftTrackerList) {
        int m = getMax(arr, n);

        for (int exp = 1; m / exp > 0; exp *= 10)
            countSort(arr, n, exp, nftTrackerList);
    }


    public List<NFTTracker> start(List<NFTTracker> nftTrackerList) {
        int n = nftTrackerList.size();
        int arr[] = new int[n];

        for(int i = 0; i < n; i++) {
            arr[i] = nftTrackerList.get(i).getNoOfTransactions();
        }

        radixsort(arr, n, nftTrackerList);
        return nftTrackerList;
    }
}

class QuickSort {

    static void swap(int[] arr, int i, int j, NFTTracker[] nftTrackers) {
        NFTTracker nftTrackerTemp = nftTrackers[i];
        int temp = arr[i];
        nftTrackers[i] = nftTrackers[j];
        arr[i] = arr[j];
        nftTrackers[j] = nftTrackerTemp;
        arr[j] = temp;
    }

    static int partition(int[] arr, int low, int high, NFTTracker[] nftTrackers) {

        int pivot = arr[high];

        int i = (low - 1);

        for (int j = low; j <= high - 1; j++) {

            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j, nftTrackers);
            }
        }
        swap(arr, i + 1, high, nftTrackers);
        return (i + 1);
    }

    static void quickSort(int[] arr, int low, int high, NFTTracker[] nftTrackers) {
        if (low < high) {
            int pi = partition(arr, low, high, nftTrackers);
            quickSort(arr, low, pi - 1, nftTrackers);
            quickSort(arr, pi + 1, high, nftTrackers);
        }
    }


    // Driver Code
    public static List<NFTTracker> start(List<NFTTracker> nftTrackerList) {
        List<NFTTracker> nftTrackerListOutput = new ArrayList<>();
        int n = nftTrackerList.size();
        int[] arr = new int[n];
        NFTTracker[] nftTrackerOutput = new NFTTracker[n];
        for(int i = 0; i < n; i++) {
            arr[i] = nftTrackerList.get(i).getNoOfTransactions();
            nftTrackerOutput[i] = nftTrackerList.get(i);
        }

        quickSort(arr, 0, n - 1, nftTrackerOutput);
        for(int i = n - 1; i >= 0; i--)
            nftTrackerListOutput.add(nftTrackerOutput[i]);
        return nftTrackerListOutput;
    }
}

class NFTTracker {

    private List<String> Txn_Hash,  UnixTimestamp, Date_Time, Action, Buyer, NFT, Type, Quantity, Price, Market;
    private String  Token_ID;

    private int noOfTransactions;
    public NFTTracker() {
    }
    public NFTTracker(List<String> Txn_Hash, List<String>  UnixTimestamp,
                      List<String>  Date_Time, List<String>  Action, List<String>  Buyer, List<String>  NFT,
                      String Token_ID, List<String>  Type, List<String>  Quantity, List<String>  Price,
                      List<String>  Market) {
        this.Txn_Hash = Txn_Hash;
        this.UnixTimestamp = UnixTimestamp;
        this.Date_Time = Date_Time;
        this.Action = Action;
        this.Buyer = Buyer;
        this.NFT = NFT;
        this.Token_ID = Token_ID;
        this.Type = Type;
        this.Quantity = Quantity;
        this.Price = Price;
        this.Market = Market;
    }

    public List<String> getTxn_Hash() {
        return Txn_Hash;
    }

    public void setTxn_Hash(List<String> txn_Hash) {
        Txn_Hash = txn_Hash;
    }

    public List<String> getUnixTimestamp() {
        return UnixTimestamp;
    }

    public void setUnixTimestamp(List<String> unixTimestamp) {
        UnixTimestamp = unixTimestamp;
    }

    public List<String> getDate_Time() {
        return Date_Time;
    }

    public void setDate_Time(List<String> date_Time) {
        Date_Time = date_Time;
    }

    @Override
    public String toString() {
        return Token_ID +" :" + Txn_Hash +"," +
                Date_Time + "," + Buyer + "," +
                NFT + "," + Type + "," + Quantity +
                "," + Price;
    }
    /*public String toString() {
        return "NFTTracker{" +
                "Txn_Hash=" + Txn_Hash +
                ", UnixTimestamp=" + UnixTimestamp +
                ", Date_Time=" + Date_Time +
                ", Action=" + Action +
                ", Buyer=" + Buyer +
                ", NFT=" + NFT +
                ", Type=" + Type +
                ", Quantity=" + Quantity +
                ", Price=" + Price +
                ", Market=" + Market +
                ", Token_ID='" + Token_ID + '\'' +
                ", noOfTransactions=" + noOfTransactions +
                '}';
    }*/

    public List<String> getAction() {
        return Action;
    }

    public void setAction(List<String> action) {
        Action = action;
    }

    public List<String> getBuyer() {
        return Buyer;
    }

    public void setBuyer(List<String> buyer) {
        Buyer = buyer;
    }

    public List<String> getNFT() {
        return NFT;
    }

    public void setNFT(List<String> NFT) {
        this.NFT = NFT;
    }

    public List<String> getType() {
        return Type;
    }

    public void setType(List<String> type) {
        Type = type;
    }

    public List<String> getQuantity() {
        return Quantity;
    }

    public void setQuantity(List<String> quantity) {
        Quantity = quantity;
    }

    public List<String> getPrice() {
        return Price;
    }

    public void setPrice(List<String> price) {
        Price = price;
    }

    public List<String> getMarket() {
        return Market;
    }

    public void setMarket(List<String> market) {
        Market = market;
    }

    public String getToken_ID() {
        return Token_ID;
    }

    public void setToken_ID(String token_ID) {
        Token_ID = token_ID;
    }
    public int getNoOfTransactions() {
        return noOfTransactions;
    }

    public void setNoOfTransactions(int noOfTransactions) {
        this.noOfTransactions = noOfTransactions;
    }
}
