package syntaxtree;
import visitor.SymbolTable;
import visitor.Visitor;
import visitor.TypeVisitor;

public abstract class Exp {
  public abstract void accept(Visitor v);
  public abstract Type accept(TypeVisitor v);
  public abstract Translate.Exp accept(Translate.TranslateVisitor v, SymbolTable st);
}