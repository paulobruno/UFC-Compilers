package Tree;

public class EXPE extends Stm{
    
    public Exp exp; 
    
    public EXPE(Exp e) {
            exp=e;
    }
     
    public ExpList kids() {
            return new ExpList(exp,null);
    }
    
    public Stm build(ExpList kids) {
        return new EXPE(kids.head);
    }
    
    public void accept(CodeVisitor v) { 
            v.visit(this); 
    }
}
