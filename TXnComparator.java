import java.util.Comparator;

public class TXnComparator implements Comparator<NFTTracker> {
    public int compare(NFTTracker s1, NFTTracker s2)
    {
        if (s1.getNoOfTransactions() == s2.getNoOfTransactions())
            return 0;
        else if (s1.getNoOfTransactions() < s2.getNoOfTransactions())
            return 1;
        else
            return -1;
    }
}
