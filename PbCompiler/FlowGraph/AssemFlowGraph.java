package FlowGraph;

import java.util.Hashtable;

import Assem.Instr;
import Assem.InstrList;
import Assem.MOVE;
import Assem.OPER;
import Graph.Node;
import Temp.Label;
import Temp.LabelList;
import Temp.Temp;
import Temp.TempList;

public class AssemFlowGraph extends FlowGraph{
    
        /***/
        private Hashtable<Node, Instr> map;
    /***/
        private Hashtable<Instr, Node> revMap;
        
        /***/
    public AssemFlowGraph(InstrList list){
        super();      
        instrs = list;
        map = new Hashtable<Node, Instr>();
        revMap = new Hashtable<Instr, Node>();        
        buildGraph(list);
    }
        
    /***/
    private void buildGraph(InstrList ilist){ 
        Instr i1 = new OPER("", null, null, null);
        Hashtable<Label, Instr> map1 = new Hashtable<Label, Instr>();
        // construindo os nos
        for( InstrList a = ilist ; a != null; a = (InstrList) a.tail ){
                if ( a.head instanceof Assem.LABEL ){
                        if(a.tail!= null)
                                map1.put(((Assem.LABEL)a.head).label, a.tail.head );
                        else{
                                Node n = this.newNode();
                        map.put(n, i1);            
                        revMap.put(i1, n);
                                map1.put(((Assem.LABEL)a.head).label, i1 );
                        }
                }else{
                        Node n = this.newNode();
                        map.put(n, a.head);            
                        revMap.put(a.head, n);            
            }
        }
        for ( InstrList aux = ilist; aux != null; aux = (InstrList) aux.tail ){
                if(!(aux.head instanceof Assem.LABEL)){
                    //LabelList jmps = aux.head.jump();
                	LabelList jmps = null;
                    if ( jmps == null ){
                        if (aux.tail != null){
                                if(!(aux.tail.head instanceof Assem.LABEL)) this.addEdge(revMap.get(aux.head), revMap.get(aux.tail.head));
                                else  this.addEdge(revMap.get(aux.head), revMap.get(map1.get(((Assem.LABEL)aux.tail.head).label)));
                        } 
                    }
                    else{
                        for ( LabelList a = jmps; a != null; a = (LabelList) a.tail ){
                                Node v = revMap.get(aux.head);
                                Instr i = map1.get(a.head);
                                Node u = revMap.get(i);
                            this.addEdge(revMap.get(aux.head),
                                    revMap.get(map1.get(a.head)));
                        }
                    }
                }
        }        
    }
    
    /***/
    public Instr instr(Node node){
        return map.get(node);
    }
    
    /***/
    public Node node(Instr instr){
        return revMap.get(instr);
    }

    /***/
    public TempList def(Node node){
        Instr i = map.get(node);       
        if ( i == null )
            return null;
        //return i.def();
        return null;
    }

    /***/
    public TempList use(Node node){
        Instr i = map.get(node);
        if ( i == null )
            return null;
        //return i.use();
        return null;
    }

    public boolean isMove(Node node){
        Instr i = map.get(node);
        if ( i == null )
            return false;
        return i instanceof MOVE;
    }
    public FlowGraph rebuild(Temp temp) {
    	return null;
    }
    /*  @Override
        public FlowGraph rebuild(Temp temp) {
                InstrList newList = null;
                for(InstrList aux = this.instrs; aux != null; aux = (InstrList) aux.tail){
                        //if( aux.head.def.contains(temp) )
                	if( false )
                	{
                                Temp newTemp = new Temp();
                                newList = new InstrList(aux.head, newList);
                                s
                               // Instr write = new OPER("STORE M[r0] <- "+ newTemp.toString()+"\n", null, new TempList(newTemp,null), null);
                                //newList = new InstrList(write, newList);
                        }else{
                                if(aux.head.use.contains(temp)){
                                        Temp newTemp = new Temp();
                                        Instr write = new OPER("STORE M[r0] <- "+ newTemp.toString()+"\n", null, new TempList(newTemp,null), null);
                                        Instr read = new OPER("LOAD" + newTemp.toString()+"<-M[r0]"+"\n", new TempList(newTemp,null),null, null);
                                        newList = new InstrList(read, new InstrList(write, newList));
                                }else
                                        newList = new InstrList(aux.head, newList);
                        }
                }
                return new AssemFlowGraph(newList);
        }*/
}
