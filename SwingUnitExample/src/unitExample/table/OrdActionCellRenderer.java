/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.table;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractCellEditor;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.text.TextAction;

/**
 * http://tips4java.wordpress.com/2009/07/12/table-button-column/
 *@version $Id$
 */
public class OrdActionCellRenderer extends AbstractCellEditor
    implements
    TableCellRenderer,
    TableCellEditor,
    ActionListener,
    MouseListener {
    
    

    private static final long serialVersionUID = 7927809717734024866L;

    private JTable table;
    private Action action;

    private JButton renderButton;
    private JButton editButton;
    private Object editorValue;
    private boolean isButtonColumnEditor;
   
    public OrdActionCellRenderer(JTable table, Action action, int column) {
        this.table = table;
        this.action = action;

        renderButton = new JButton();

        editButton = new JButton();
        editButton.setFocusPainted(false);
        editButton.addActionListener(this);

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(column).setCellRenderer(this);
        columnModel.getColumn(column).setCellEditor(this);
        table.addMouseListener(this);
    }

    @Override
    public Component getTableCellRendererComponent(
        JTable table,
        Object value,
        boolean isSelected,
        boolean hasFocus,
        int row,
        int column) {

        if (isSelected) {
            renderButton.setForeground(table.getSelectionForeground());
            renderButton.setBackground(table.getSelectionBackground());
        }
        else {
            renderButton.setForeground(table.getForeground());
            renderButton.setBackground(UIManager.getColor("Button.background"));
        }

        //renderButton.setAction(action);
        renderButton.setText((String) action.getValue(Action.NAME));
//        renderButton.setText( "action.name");
        return renderButton;
    }

    @Override
    public Object getCellEditorValue() {
        return editorValue;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value == null) {
            editButton.setText("");
            editButton.setIcon(null);
        }else {
            editButton.setText((String) action.getValue(Action.NAME));
            editButton.setIcon(null);
        }

        this.editorValue = value;
        return editButton;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (table.isEditing() && table.getCellEditor() == this){
            isButtonColumnEditor = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isButtonColumnEditor && table.isEditing()){
            table.getCellEditor().stopCellEditing();
        }
        isButtonColumnEditor = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int row = table.convertRowIndexToModel(table.getEditingRow());
        fireEditingStopped();

        //  Invoke the Action
        ActionEvent event = new ActionEvent(table, ActionEvent.ACTION_PERFORMED, "" + row);
        action.actionPerformed(event);
    }
    
     public static  void createPanel() {
        JFrame f = new JFrame("Wallpaper");
        f.getContentPane().setLayout(new FlowLayout());
        Object[] heads = {"d","s","dd"};  
        
         
        DefaultTableModel model = new  DefaultTableModel  (heads,5);         
        JTable t=new JTable();         
        t.setModel(model);
        
        //重点是这里设置 自定义的单元格
        Action action=new javax.swing.text.TextAction("heLLOWORLD") {

             @Override
             public void actionPerformed(ActionEvent e) {
                 JOptionPane.showMessageDialog(null, "heloo world");
             }
         };
 
 

             
        OrdActionCellRenderer  tedt=new OrdActionCellRenderer (t,action,1);
        t.getColumnModel().getColumn(2).setCellEditor(tedt);
        
        //用JScrollPane 能正常顺利显示处JTable
        final JScrollPane scrollPane = new JScrollPane(); 
        scrollPane.setViewportView(t);
        f.getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        f.setSize(450, 300);
        f.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo (null);
        f.setVisible (true);
    }
      
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {      
                createPanel();
            }
        });
    } 

}
