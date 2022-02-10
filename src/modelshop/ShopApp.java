package modelshop;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;

public class ShopApp extends JFrame{
    private JPanel panelMain;
    private JButton btnAddItem;
    private JButton btnCompleteTransaction;
    private JButton btnDeleteProduct;
    private JButton btnNewStockItem;
    private JButton btnTransactions;
    private JTable tableStock;
    private JTable tableCart;
    private JTextField textFieldPrice;

    ItemTableModel stockTableModel;
    ItemTableModel cartTableModel;

    ShopManager manager = new ShopManager();


    public ShopApp() {
        setupFrame();
        createTable();
        btnAddItem.addActionListener(e -> addItem());
        btnDeleteProduct.addActionListener(e -> deleteProduct());
        btnNewStockItem.addActionListener(e -> newStockItem());
        btnTransactions.addActionListener(e -> seeTransactions());
        btnCompleteTransaction.addActionListener(e -> completeTransaction());
    }

    private void setupFrame(){
        setContentPane(panelMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500, 400));
        pack();
        setVisible(true);

    }

    private void addItem(){
        manager.addItemToCart(tableStock.getSelectedRow());
        refreshCart();
    }

    private void deleteProduct(){
        manager.removeItemCart(tableCart.getSelectedRow());
        refreshCart();
    }

    private void newStockItem(){
        var newItemWindow = new NewItemForm(manager.stock);
        newItemWindow.setVisible(true);
        manager.addNewItem(newItemWindow.newItem);
        stockTableModel.fireTableDataChanged();
    }

    private void seeTransactions(){
        var transactions = manager.getTransactions();
        var transactionsPanel = new TransactionsForm(transactions);
        transactionsPanel.setVisible(true);
    }

    private void completeTransaction(){
        if(manager.cart.size() == 0){
            JOptionPane.showMessageDialog(panelMain, "There has to be at least 1 item in the cart");
            return;
        }
        manager.completeTransaction();
        refreshCart();
    }

    private void refreshCart(){
        textFieldPrice.setText(Integer.toString(manager.getCartTotal()));
        cartTableModel.fireTableDataChanged();
    }

    private void createTable(){
        tableStock.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableCart.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        stockTableModel = new ItemTableModel(manager.stock);
        tableStock.setModel(stockTableModel);

        cartTableModel = new ItemTableModel(manager.cart);
        tableCart.setModel(cartTableModel);
    }

//    public static void main(String[] args) {
//
//        JFrame frame = new JFrame("App");
//        frame.setContentPane(new ShopApp().panelMain);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
//    }



}
