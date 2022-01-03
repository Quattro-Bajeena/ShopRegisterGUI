package fantasyshop;


import java.sql.*;
import java.util.ArrayList;

public class DbRepository {

    private Connection conn;

    public DbRepository(String connection_str){
        try{
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(connection_str);
        }
         catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void addItem(Item item) {
        try{
            PreparedStatement prep = conn.prepareStatement(
                    "insert into items values (?, ?, ?);");
            prep.setString(1, item.code);
            prep.setString(2, item.name);
            prep.setInt(3, item.price);
            prep.addBatch();
            prep.executeBatch();
        }
        catch (SQLException e){
            System.err.println(e);
        }

    }

    public ArrayList<Item> getStock(){
        var items = new ArrayList<Item>();
        try{
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("select * from items;");
            while (rs.next()) {
                String code = rs.getString("code");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                var newItem = new Item(code, name, price);
                items.add(newItem);
            }
        }
        catch (SQLException e){
            System.err.println(e);
        }

        return items;
    }

    public void addTransaction(Transaction transaction){
        try{
            PreparedStatement prepInsTransaction = conn.prepareStatement(
                    "INSERT INTO transactions VALUES (?, ?, ?);");
            prepInsTransaction.setNull(1, 0);
            prepInsTransaction.setString(2, transaction.code);
            prepInsTransaction.setInt(3, transaction.price);
            prepInsTransaction.addBatch();
            prepInsTransaction.executeBatch();

            PreparedStatement prepGetTransactionId = conn.prepareStatement(
                    "SELECT id FROM transactions WHERE code = ?;");
            prepGetTransactionId.setString(1, transaction.code);

            var result = prepGetTransactionId.executeQuery();
            result.next();
            var transactionId = result.getInt("id");


            for(var item : transaction.itemsBought){
                PreparedStatement prepInsertTransactionItem = conn.prepareStatement(
                        "INSERT INTO transactionitems VALUES (?, ?, ?);");
                prepInsertTransactionItem.setNull(1, 0);
                prepInsertTransactionItem.setInt(2, transactionId);
                prepInsertTransactionItem.setString(3, item.code);
                prepInsertTransactionItem.addBatch();
                prepInsertTransactionItem.executeBatch();
            }

        }
        catch (SQLException e){
            System.err.println(e);
        }
    }

    public ArrayList<Transaction> getTransactions(){
        var transactions = new ArrayList<Transaction>();
        try{
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("select * from transactions;");
            while(rs.next()){
                var id = rs.getInt("id");
                var code = rs.getString("code");
                var price = rs.getInt("price");

                var items = getTransactionItems(id);
                var transaction = new Transaction(code, price, items);

                transactions.add(transaction);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return transactions;
    }

    public ArrayList<Item> getTransactionItems(int transactionId){
        var items = new ArrayList<Item>();
        try {
            PreparedStatement prep = conn.prepareStatement(
                    "SELECT items.code, items.name, items.price FROM items\n" +
                        "JOIN transactionitems ON items.code = transactionitems.itemcode\n" +
                        "JOIN transactions ON  transactions.id = transactionitems.transactionid\n" +
                        "WHERE transactions.id = ?;");
            prep.setInt(1, transactionId);


            var rs = prep.executeQuery();
            while(rs.next()) {
                String code = rs.getString("code");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                var item = new Item(code, name, price);
                items.add(item);
                //System.out.println(id + "|" + code + "|" + name);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }

    public static void main(String[] args){
        var repo = new DbRepository("jdbc:sqlite:shop.db");
        //var transactions = repo.getTransactions();
//        var items = new ArrayList<Item>();
//        var item1 = new Item("MSZ-010", "ZZ Gundam", 105);
//        item1.id = 6;
//        var item2 = new Item("MSN-00100", "Hyaku Shiki", 205);
//        item2.id = 7;
//
//        items.add(item1);
//        items.add(item2);
//
//        var transaction = new Transaction("trans",0, items);
//        transaction.recalculatePrice();
//
//        repo.addTransaction(transaction);

    }


}
