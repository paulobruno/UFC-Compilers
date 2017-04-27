package syntaxtree;

import java.util.Vector;

import visitor.SymbolTable;

public class ClassDeclList {
   private Vector<ClassDecl> list;

   public ClassDeclList() {
      list = new Vector<ClassDecl>();
   }

   public void addElement(ClassDecl n) {
      list.addElement(n);
   }

   public ClassDecl elementAt(int i)  { 
      return (ClassDecl)list.elementAt(i); 
   }

   public int size() { 
      return list.size(); 
   }
}