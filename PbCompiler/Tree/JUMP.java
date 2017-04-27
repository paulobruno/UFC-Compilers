package Tree;
import Temp.Label;
import java.util.LinkedList;
public class JUMP extends Stm {
  public Exp exp;
  public LinkedList<Label> targets;
  public JUMP(Exp e, LinkedList<Label> t) { exp=e; targets=t; }
  public JUMP(Label target) {
	exp = new NAME(target);
	targets = new LinkedList<Label>();
	targets.addFirst(target);
  }
  public ExpList kids() {return new ExpList(exp,null);}
  public Stm build(ExpList kids) {
    return new JUMP(kids.head,targets);
  }
  
  public void accept(CodeVisitor v) { 
          v.visit(this); 
  }
}

