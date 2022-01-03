package fantasyshop;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ItemTableModel extends AbstractTableModel{

    String[] columnNames = new String[]{"Code", "Name", "Price"};
    ArrayList<Item> items;
    public ItemTableModel(ArrayList<Item> items){
        this.items = items;
    }

    @Override
    public int getRowCount() {
        return items.size();
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
        var item = items.get(rowIndex);
        if(columnIndex == 0)
            return item.code;
        else if(columnIndex == 1)
            return item.name;
        else return item.price;
    }


}
