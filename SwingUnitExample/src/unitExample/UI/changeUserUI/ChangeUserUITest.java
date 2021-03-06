/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.UI.changeUserUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.plaf.LayerUI;

public class ChangeUserUITest {    

   
    public static void main(String[] args) throws 
            UnsupportedLookAndFeelException, 
            ClassNotFoundException, InstantiationException,
            IllegalAccessException {
        JFrame  frame=new JFrame();        
        String lnfName ="com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        
        JButton b=new JButton("ddd");
        frame.getContentPane().setLayout(new FlowLayout());        
        frame.getContentPane(). add(b, 0); 
        JLabel l=new JLabel("labelddd");
         frame.getContentPane(). add(l, 0); 
        
        b.setPreferredSize(new Dimension(50,20));     
        l.setPreferredSize(new Dimension(50,20));     
        UIManager.setLookAndFeel(lnfName); 
        
 
     
        //第一个参数是指明label组件使用的UI标识，
        //第二个参数指明所用UI的类的路径。一定是字符串，系统通过类反射调用函数
        //createUI(JComponent c)，从而生成UI对象
        UIManager.put("ButtonUI", Bui.class.getName());   
        
        UIManager.put("LabelUI", Lui.class.getName());   
       
        //仅对指定的组件的子孙进行更换皮肤。
        SwingUtilities.updateComponentTreeUI(frame);
        
        frame.setSize(300, 300);
        frame.setVisible(true);
    }

}
