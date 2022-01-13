package modelshop;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TransactionDetailForm extends JDialog{
    private JTable itemTable;
    private JTextField codeText;
    private JTextField priceText;
    private JButton exitButton;
    private JPanel contentPane;

    ItemTableModel boughtItemsTableModel;

    public TransactionDetailForm(Transaction transaction) {

        setSize(400, 300);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(exitButton);

        boughtItemsTableModel = new ItemTableModel(transaction.itemsBought);
        itemTable.setModel(boughtItemsTableModel);

        codeText.setText(transaction.code);
        priceText.setText(Integer.toString(transaction.price));

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { dispose(); }
        });
    }
}
