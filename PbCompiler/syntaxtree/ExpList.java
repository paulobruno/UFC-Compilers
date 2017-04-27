package syntaxtree;

import java.util.Vector;

public class ExpList {
   private Vector<Exp> list;

   public ExpList() {
      list = new Vector<Exp>();
   }

   public ExpList(Exp e, ExpList l) {
	  list = new Vector<Exp>();
      list.add(e);
      
      for (int i = 0; i < l.size(); ++i) {
    	  list.add(l.elementAt(i));
      }
   }

   public void addElement(Exp n) {
      list.addElement(n);
   }

   public Exp elementAt(int i)  { 
      return (Exp)list.elementAt(i); 
   }

   public int size() { 
      return list.size(); 
   }
}
