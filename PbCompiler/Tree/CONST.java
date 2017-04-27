package Tree;
import Temp.Temp;

public class CONST extends Exp {
        public int value;

        public CONST(int v) {
                value = v;
        }

        public ExpList kids() {
                return null;
        }

        public Exp build(ExpList kids) {
                return this;
        }
        
        public Temp accept(CodeVisitor v) { 
                return v.visit(this); 
        }
}
