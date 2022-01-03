package modelshop;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class NewItemForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldCode;
    private JTextField textFieldName;
    private JTextField textFieldPrice;

    public ArrayList<Item> stock;
    Item newItem;

    public NewItemForm(ArrayList<Item> currentStock) {
        this.stock = currentStock;

        setSize(300, 200);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        var code = textFieldCode.getText();
        var name = textFieldName.getText();
        var priceStr = textFieldPrice.getText();

        int price;
        if(priceStr.matches("\\d+")){
            price = Integer.parseInt(priceStr);
        }
        else{
            JOptionPane.showMessageDialog(contentPane, "Price has to be positive integer");
            return;
        }

        boolean codeUnique = true;
        for(var item : stock){
            if(item.code.equals(code)){
                codeUnique = false;
                break;
            }
        }
        if(codeUnique == false){
            JOptionPane.showMessageDialog(contentPane, "Item code has to be uniqe");
            return;
        }

        newItem = new Item(code, name, price);
        dispose();

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
