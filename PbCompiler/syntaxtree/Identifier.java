package syntaxtree;
import visitor.SymbolTable;
import visitor.Visitor;
import visitor.TypeVisitor;

public class Identifier {
  public String s;

  public Identifier(String as) { 
    s=as;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }

  public String toString(){
    return s;
  }
  
  public Translate.Exp accept(Translate.TranslateVisitor v, SymbolTable st) {
	  return v.visit(this, st);
  }
}