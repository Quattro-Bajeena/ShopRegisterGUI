package modelshop;

import java.util.ArrayList;

public class Transaction {

    public int id;
    public String code;
    public ArrayList<Item> itemsBought;
    public int price;

    public Transaction(String code, int price, ArrayList<Item> itemsBought){
        this.code = code;
        this.itemsBought = itemsBought;
        this.price = price;
    }

    public void recalculatePrice(){
        this.price = 0;
        for(var item : itemsBought){
            price += item.price;
        }
    }

}
