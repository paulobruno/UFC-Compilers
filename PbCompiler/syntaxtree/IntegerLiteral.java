package syntaxtree;
import visitor.SymbolTable;
import visitor.Visitor;
import visitor.TypeVisitor;

public class IntegerLiteral extends Exp {
  public int i;

  public IntegerLiteral(int ai) {
    i=ai;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
  
  public Translate.Exp accept(Translate.TranslateVisitor v, SymbolTable st) {
	  return v.visit(this, st);
  }
}
