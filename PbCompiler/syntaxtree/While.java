package syntaxtree;
import visitor.SymbolTable;
import visitor.Visitor;
import visitor.TypeVisitor;

public class While extends Statement {
  public Exp e;
  public Statement s;

  public While(Exp ae, Statement as) {
    e=ae; s=as; 
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

