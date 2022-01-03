package fantasyshop;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShopApp {
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
        btnNewStockItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var newItemWindow = new NewItemForm(manager.stock);
                newItemWindow.setVisible(true);
                manager.addNewItem(newItemWindow.newItem);
                stockTableModel.fireTableDataChanged();
            }
        });
        btnTransactions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var transactions = manager.getTransactions();
                var transactionsPanel = new TransactionsForm(transactions);
                transactionsPanel.setVisible(true);
            }
        });
        btnCompleteTransaction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(manager.cart.size() == 0){
                    JOptionPane.showMessageDialog(panelMain, "There has to be at least 1 item in the cart");
                    return;
                }
                manager.completeTransaction();
                refreshCart();
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
    }

    public static void main(String[] args) {
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch(Exception ignored){}

        JFrame frame = new JFrame("App");
        frame.setContentPane(new ShopApp().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }



}
