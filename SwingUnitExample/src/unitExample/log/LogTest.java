/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.log;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import unitExample.divpanel.DivPanelDemo;

/**
 *日志使用初步：
 *  或1 java全局设置。配置文件位于：
 *   jre/lib/logging.properties (注：jdk或jre文件夹下。如果运行出错，在这里配置
 *   了输出到文件，那么也能查开。）
 *  或2 通过LogManager 动态设置，重新加载等
 *  或3：通过Logger.getLoger方法，创建或得到对象，然后设置。
 *     最后通过logger.log方法记录日志。 
 *  日起级别有这些：（从高到低 级别按从左到右，从上到下排。）
 *  off/on----开或关日起记录；   SEVERE------服务；     WARNING-------出错
 *  INFO------发信息；           CONFIG-----静态配置；  FINE--------消息跟踪，
 *  FINER-----消息详尽跟踪；     FINEST--------消息最详细跟踪
 * 日起属性文件设置关键说明：
 * handlers= java.util.logging.FileHandler, java.util.logging.ConsoleHandler 
 * 上句，指出日志还输到文件。
 * .level= INFO //指出所有日志对象的默认级别
 * java.util.logging.FileHandler.pattern = %h/%s.log 
 * //文件名字和路径,默认为用户的根目录
 * java.util.logging.FileHandler.append=true 
 * //不重新创建文件,在原文件里追加 
 * java.util.logging.FileHandler.limit = 50000 //文件最大数 
 * java.util.logging.FileHandler.count = 1 
 * java.util.logging.FileHandler.formatter = java.util.logging.SimpleFormatter 
 * //简单格式输出
 * 
 */
public class LogTest {
    //全局对象。统一记录
//    private static final Logger log1=Logger.getGlobal();
    //如果该名称的日起对象存在，则返回；否则，创建。 这是事件出错消息
    private static final Logger 
            log2=Logger.getLogger("java.awt.event.EventDispatchThread");
    
    private static ActionListener action=new ActionListener(){
  
        public void actionPerformed(ActionEvent e) {
            try {
                URI uri = (ClassLoader.getSystemResource("/")).toURI();
    //                log1.log(Level.SEVERE,"值为："+uri);
                    String ss=uri.getPath();
                    String [] strs=ss.split("/\\w*/\\w*/\\w*\\w*$");
            
//                    log1.log(Level.SEVERE,null,ex); 如果要用日志，取消注释
//                    log2.log(Level.SEVERE, null, ex);
//                    log1.log(Level.SEVERE, "uri出错");
            } catch (Exception ex) {
                setLog();
                Logger.getLogger(LogTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
    };
    
 
            
    public static void setLog(){
         LogManager lm = LogManager.getLogManager();
         
//        log1.setLevel(Level.SEVERE);
        log2.setLevel(Level.INFO);
        
//        log1.setLevel(Level.SEVERE);
//        log2.setLevel(Level.SEVERE);
        FileHandler fh=null;
        try {
            fh=new FileHandler("testlog%g.log");
        } catch (IOException ex) {
            Logger.getLogger(LogTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(LogTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        fh.setFormatter(new SimpleFormatter());// 这是调式控制台哪种输出格式
//        log1.addHandler(fh);
        log2.addHandler(fh); 
        String[] strs = null;
        
    }
    
 
    
    public static  void createPanel() {       
        
        
        JFrame f = new JFrame("Wallpaper");   
        f.setSize(400, 300);
        JPanel p = (JPanel) f.getContentPane();
        p.setLayout(new BorderLayout());
        
        JButton bt=new JButton("点我");
        bt.addActionListener(action);
        JPanel p1=new JPanel();
        p1.add(bt);
        f.add(p1,BorderLayout.EAST);
        
        
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


