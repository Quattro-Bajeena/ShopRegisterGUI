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
                    "insert into items values (?, ?, ?, ?);");
            prep.setNull(1, 0);
            prep.setString(2, item.code);
            prep.setString(3, item.name);
            prep.setInt(4, item.price);
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
                int id = rs.getInt("id");
                String code = rs.getString("code");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                var newItem = new Item(code, name, price);
                newItem.id = id;
                items.add(newItem);
            }
        }
        catch (SQLException e){
            System.err.println(e);
        }

        return items;
    }

    public void addTransaction(Transaction transaction){

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
                    "SELECT items.id, items.code, items.name, items.price\n" +
                        "FROM items\n" +
                        "JOIN transactionitems ON items.id = transactionitems.itemid\n" +
                        "JOIN transactions ON transactionitems.transactionid = ?;");
            prep.setInt(1, transactionId);
            prep.addBatch();
            //prep.executeBatch();
            var rs = prep.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                String code = rs.getString("code");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                var item = new Item(code, name, price);
                item.id = id;
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

        var transactions = repo.getTransactions();
    }


}
