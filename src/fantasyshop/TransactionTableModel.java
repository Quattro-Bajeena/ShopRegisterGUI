package fantasyshop;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class TransactionTableModel extends AbstractTableModel {

    String[] columnNames = new String[]{"Code", "Items", "Full Price"};
    ArrayList<Transaction> transactions;
    public TransactionTableModel(ArrayList<Transaction> transactions){
        this.transactions = transactions;
    }

    @Override
    public int getRowCount() {
        return transactions.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        var transaction = transactions.get(rowIndex);
        if(columnIndex == 0)
            return transaction.code;
        else if(columnIndex == 1){
            String itemsStr = "";
            for(var item: transaction.itemsBought){
                itemsStr += item.code + "|";
            }
            return itemsStr;
        }
        else return transaction.price;
    }
}
