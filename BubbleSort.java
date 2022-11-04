import java.util.*;
class BubbleSort {
    void bubbleSort(int arr[], NFTTracker[] nftTrackers)
    {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (arr[j] < arr[j + 1]) {
                    // swap arr[j+1] and arr[j]
                    NFTTracker nftTrackerTemp = nftTrackers[j];
                    int temp = arr[j];
                    nftTrackers[j] = nftTrackers[j + 1];
                    arr[j] = arr[j + 1];
                    nftTrackers[j + 1] = nftTrackerTemp;
                    arr[j + 1] = temp;
                }
    }


    public List<NFTTracker> start(List<NFTTracker> nftTrackerList) {
        BubbleSort ob = new BubbleSort();
        int n = nftTrackerList.size();
        int arr[] = new int[n];
        NFTTracker[] nftTrackersOutput = new NFTTracker[n];
        for(int i = 0; i < n; i++) {
            arr[i] = nftTrackerList.get(i).getNoOfTransactions();
            nftTrackersOutput[i] = nftTrackerList.get(i);
        }
        nftTrackerList.clear();
        ob.bubbleSort(arr, nftTrackersOutput);
        for(int i = 0; i < n; i++) {
            nftTrackerList.add(nftTrackersOutput[i]);
        }
    return nftTrackerList;
    }
}