package Model;

public class CostModel {
    public String itemName;
    public int quantity;
    public String amount;
    public String purchaseDate;
    public String month;
    public String remarks;

    public CostModel(String itemName, int quantity, String amount,
                     String purchaseDate, String month, String remarks) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.amount = amount;
        this.purchaseDate = purchaseDate;
        this.month = month;
        this.remarks = remarks;
    }
}

