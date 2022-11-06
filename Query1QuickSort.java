// Java implementation of QuickSort
import java.io.*;
import java.util.*;

class Query1QuickSort {

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
