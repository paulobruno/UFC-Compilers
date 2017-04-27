package Tree;

import java.util.List;

import Temp.Temp;

public class CALL extends Exp {
        public Exp func;
        public ExpList args;
        
        public CALL(Exp f, ExpList a) {
                func=f; 
                args=a;
        }
        
        public CALL(Exp f, List<Exp> a){
                
        }
        
        public ExpList kids() {
                return new ExpList(func,args);
        }
        
        public Exp build(ExpList kids) {
                return new CALL(kids.head,kids.tail);
        }
          
        public Temp accept(CodeVisitor v) { 
                return v.visit(this); 
        }
}