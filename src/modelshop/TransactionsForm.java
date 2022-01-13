package modelshop;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TransactionsForm extends JDialog{
    private JPanel contentPane;
    private JTable tableTransactions;
    private JButton buttonOk;
    private JButton transactionDetailsButton;

    TransactionTableModel transactionTableModel;
    ArrayList<Transaction> transactions;

    public TransactionsForm(ArrayList<Transaction> transactions){
        setSize(700, 400);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOk);



        tableTransactions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        transactionTableModel = new TransactionTableModel(transactions);
        tableTransactions.setModel(transactionTableModel);

        this.transactions = transactions;

        buttonOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        transactionDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableTransactions.getSelectedRow();
                if(row == -1)
                    return;

                var transaction = transactions.get(row);

                var transactionDetailForm = new TransactionDetailForm(transaction);
                transactionDetailForm.setVisible(true);

            }
        });


    }
}


