import java.util.*;
public class NFTTracker {

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
    }

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
