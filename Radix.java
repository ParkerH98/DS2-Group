// Radix sort Java implementation

import java.io.*;
import java.util.*;

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
