/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.jtree;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;

public class JTreePanelDemo2 extends JPanel{
    
    //<editor-fold desc="自定义单元编辑框">
    public class MyTreeCellEditor extends AbstractCellEditor implements TreeCellEditor {
        private transient DefaultTreeCellRenderer renderer ;
        private transient JTree tree;
        private transient Object v;
        private JPanel editPanel;
        private JTextField editText;
        private JButton editButton;
        public MyTreeCellEditor(JTree tree){
            this.tree=tree;
            renderer=(DefaultTreeCellRenderer) tree.getCellRenderer();
            editPanel = new javax.swing.JPanel();
            
            editButton = new javax.swing.JButton();
            editText = new javax.swing.JTextField(); 
            editText.setPreferredSize(new Dimension(120,
                    editText.getPreferredSize().height)
            );
            editButton.setText("*");
            editButton.setPreferredSize(new Dimension(15, 
                    editButton.getPreferredSize().height)
            );
           
            editButton.addActionListener(new ActionListener() {
               
                public void actionPerformed(ActionEvent e) {
                     String dd=JOptionPane.showInputDialog(null,
                             MyTreeCellEditor.this.getCellEditorValue()
                     );   
                     editText.setText(dd);
                }
            });
            editPanel.setLayout(new BorderLayout());             
            editPanel.add(editText,BorderLayout.CENTER);
            editPanel.add(editButton,BorderLayout.EAST);
        }
        @Override
        public Component getTreeCellEditorComponent(JTree tree, 
            Object value, boolean isSelected, boolean expanded,
            boolean leaf, int row) 
        {
             DefaultMutableTreeNode node=(DefaultMutableTreeNode) value;
             
             Data data=(Data) node.getUserObject();
             if (value == null) 
                editText.setText(""); 
            else 
                editText.setText(data.title); 
            return editPanel;
        }
         
        @Override
        public Object getCellEditorValue() {
            DefaultMutableTreeNode node=(DefaultMutableTreeNode) 
                    tree.getSelectionPath().getLastPathComponent();         
            Data data= (Data) node.getUserObject();
            data.title=editText.getText();            
            return node;
        }
         
    }
    //</editor-fold>
    
    //<editor-fold desc="节点数据类">
    public class Data{       
        public String id;
        public String title;
        public Object userData;
        
        public Data(){}
        public Data(String id, String title, Object userData) {
            this.id = id;
            this.title = title;
            this.userData = userData;
        }         
        
        public String toString() {
            return title;
        }                  
    }
    
    //</editor-fold>
    
    private JTree tree;
    private DefaultMutableTreeNode root=null;
    private DefaultTreeModel mode;
    private DefaultMutableTreeNode node1;
    private DefaultMutableTreeNode parent;
    
    public void init(){        
         JScrollPane jsp=new JScrollPane();
          
         root=new DefaultMutableTreeNode();
         root.setUserObject(new Data("0","风格集",null));
 
         DefaultMutableTreeNode node;
         node=new  DefaultMutableTreeNode(); // 定义树结点
         Data data=new Data ("style1","风格ID","风格ID");
         node.setUserObject(data);
         root.add(node);
         parent=node;
         
         node =new  DefaultMutableTreeNode();
         node.setUserObject(new Data("ddd0","xxxx",null));
         parent.add(node);
         parent=node;
         for(int i=0;i<=6;i++)  {
             String s=String.valueOf(i);
            
             if(i%2==0){
                 data=new Data (s,"主题"+s,new JLabel(s));
                
             } else{
                  data=new Data (s,"主题"+s,new JTextField(s));
             }
             node=  new  DefaultMutableTreeNode(data);
             if(i==3) node1=parent;
             parent.add(node);
         }  
        
         tree=new JTree(root);
         mode=(DefaultTreeModel) tree.getModel();
         
         tree.setCellEditor(new MyTreeCellEditor(tree));
         tree.setEditable(true);
         //选中事件处理。显示当前选中节点属性
         tree.addTreeSelectionListener(new TreeSelectionListener(){             
            public void valueChanged(TreeSelectionEvent e) {
                if(e.getNewLeadSelectionPath()==null) return;
                DefaultMutableTreeNode node=(DefaultMutableTreeNode) 
                        e.getNewLeadSelectionPath().getLastPathComponent();
                Data data=(Data) node.getUserObject();
                System.out.println("id:"+data.id+";titel:"+data.title+";data:"+data.userData);
                
            }
         
         });
         tree.addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getModifiers()==18 )//按下Ctrl键  
                {//这里将演示，设置指定节点为选中
                    //mode为默认树模型
                    TreePath  path=new TreePath( mode.getPathToRoot(node1));
                    tree.setSelectionPath(path);                    
                }else if (e.getModifiers()==24 ){//按下alt键 关闭                    
                    tree.collapsePath(tree.getSelectionPath());
                }else if (e.getModifiers()==17 ){//按下shift键 展开第N层  
                    //tree中row行的概念， 就是看得见的树的层次（叶子不算）
                    tree.expandRow(tree.getRowCount()-1);                   
                   
                }                
            }
            public void mousePressed(MouseEvent e) { }
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) { }
            public void mouseExited(MouseEvent e) {}
         
         });
         
         jsp.setViewportView(tree);          
         BorderLayout layout=new BorderLayout();
         this.setLayout(layout); 
         this.add(jsp,BorderLayout.CENTER);      
    }
    
    public Data getDataInstance(String id, String title, Object userData){
        return new Data(id,title,userData);
    }
   
    //<editor-fold desc="test"> 
    public static void createPanel(){        
        JFrame f = new JFrame("Wallpaper");       
        JTreePanelDemo2 scrollPane = new JTreePanelDemo2(); 
        scrollPane.init();
        f.add(scrollPane);
        f.setSize(400, 300);
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
    //</editor-fold>
}
