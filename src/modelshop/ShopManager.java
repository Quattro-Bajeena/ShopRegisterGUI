package modelshop;

import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class ShopManager {
    DbRepository repository = new DbRepository("jdbc:sqlite:shop.db");

    public ArrayList<Item> stock = new ArrayList<>();
    public ArrayList<Item> cart = new ArrayList<>();

    public ShopManager(){
        stock = repository.getStock();
    }

    public void addItemToCart(int index){
        if(index >= 0 && index < stock.size()){
            var item = stock.get(index);
            cart.add(item);
        }
    }

    public void removeItemCart(int index){
        if(index >= 0 && index < stock.size()){
            cart.remove(index);
        }
    }

    public void addNewItem(Item newItem){
        if(newItem == null)
            return;

        repository.addItem(newItem);
        stock.add(newItem);
    }

    public int getCartTotal(){
        int totalPrice = 0;
        for(var item : cart){
            totalPrice += item.price;
        }
        return totalPrice;
    }

    public ArrayList<Transaction> getTransactions(){
        return repository.getTransactions();
    }

    public void completeTransaction(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        var code = dtf.format(now);

        var transaction = new Transaction(code , 0,  cart);
        transaction.recalculatePrice();
        repository.addTransaction(transaction);
        cart.clear();
    }

}
