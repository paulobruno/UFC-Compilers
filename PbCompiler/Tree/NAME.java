package Tree;
import Temp.Label;
import Temp.Temp;


public class NAME extends Exp {
        public Label label;

        public NAME(Label l) {
                super();
                label = l;
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
