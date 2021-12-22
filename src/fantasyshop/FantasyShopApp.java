package fantasyshop;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FantasyShopApp {
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


    public FantasyShopApp() {

        createTable();
        btnAddItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.addItemToCart(tableStock.getSelectedRow());
                refreshCart();
            }
        });
        btnDeleteProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.removeItemCart(tableCart.getSelectedRow());
                refreshCart();
            }
        });
        refreshCart();
        btnNewStockItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var newItemWindow = new NewItem();
                newItemWindow.setVisible(true);
                manager.addNewItem(newItemWindow.newItem);
                stockTableModel.fireTableDataChanged();

            }
        });
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
        manager.cart.add(new Item("MSZ-010", "ZZ Gundam", 105));
        manager.cart.add(new Item("MSN-00100", "Hyaku Shiki", 205));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new FantasyShopApp().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }



}
