/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.annotation;

import java.lang.reflect.Method;  
import java.util.HashSet;  
import java.util.Set;  
  
public class TestAnnotation {  
    /** 
    * author lighter 
    * 说明:具体关天Annotation的API的用法请参见javaDoc文档 
    */  
    public static void main(String[] args) throws Exception {  
        String  CLASS_NAME = JavaEyer.class.getName();
        Class  test = Class.forName(CLASS_NAME);  
        Method[] method = test.getMethods();  
        boolean flag = test.isAnnotationPresent(Description.class);  
        if(flag)  
        {  
            Description des = (Description)test.getAnnotation(Description.class);  
            System.out.println("描述:"+des.value());  
            System.out.println("-----------------");  
        }  

        //把JavaEyer这一类有利用到@Name的全部方法保存到Set中去  
        Set<Method> set = new HashSet<Method>();  
        for(int i=0;i<method.length;i++)  
        {  
            boolean otherFlag = method[i].isAnnotationPresent(Name.class);  
            if(otherFlag) set.add(method[i]);  
        }  
        for(Method m: set)  
        {  
            Name name = m.getAnnotation(Name.class);  
            System.out.println(name.originate());  
            System.out.println("创建的社区:"+name.community());  
            System.out.println("====================");  
            System.out.println(m.getName()+";");
        }  
    }  
}  
