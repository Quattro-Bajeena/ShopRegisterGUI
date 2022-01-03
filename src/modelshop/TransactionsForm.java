package modelshop;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TransactionsForm extends JDialog{
    private JPanel contentPane;
    private JTable tableTransactions;
    private JButton buttonOk;

    TransactionTableModel transactionTableModel;

    public TransactionsForm(ArrayList<Transaction> transactions){
        setSize(700, 400);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOk);

        buttonOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        tableTransactions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        transactionTableModel = new TransactionTableModel(transactions);
        tableTransactions.setModel(transactionTableModel);
    }
}


