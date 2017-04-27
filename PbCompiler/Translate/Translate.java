package Translate;

import java.io.*;

import syntaxtree.*;

import java.util.*;

import Frame.*;
import Mips.*;
import visitor.*;

public class Translate implements TranslateVisitor {

	// Frame pro metodo atual
	private Frame frame;	
	private List<Frag> frags;
	private String currentClass;
	private String currentMethod;
	private String idType;


	public Translate(Frame frame) {
		this.frame = frame;
		frags = new ArrayList<Frag>();
	}

	public void translate(Program program) {
		BuildSymbolTableVisitor symbolTableBuilder = new BuildSymbolTableVisitor();
		symbolTableBuilder.visit(program);
		SymbolTable symbolTable = symbolTableBuilder.getSymTab();
		program.accept(this, symbolTable);		
	}

	public List<Frag> getFrags() {
		return frags;
	}
	
	
	public void procEntryExit(Tree.Stm body, Exp retexp, Frame frame) {
		Tree.Stm procStm;
		if (body != null){
			procStm = new Tree.MOVE(new Tree.TEMP(frame.RV()), new Tree.ESEQ(body,retexp.unEx()));
		}
		else{
			procStm = new Tree.MOVE(new Tree.TEMP(frame.RV()), retexp.unEx());
		}
		ProcFrag frag = new ProcFrag(procStm, frame);
		frags.add(frag);
	}


	// Exp e1,e2;
	public Exp visit(And n, SymbolTable symbolTable){
		Exp exp1 = n.e1.accept(this, symbolTable);
		Exp exp2 = n.e2.accept(this, symbolTable);
		return new Ex(new Tree.BINOP(Tree.BINOP.AND, exp1.unEx(), exp2.unEx()));
	}

	public Exp visit(ArrayAssign n, SymbolTable symbolTable){
        Exp e1 = n.e1.accept(this, symbolTable);
        Exp e2 = n.e2.accept(this, symbolTable);
        Exp expId = n.i.accept(this, symbolTable);
        return new Nx(new Tree.MOVE(new Tree.BINOP(Tree.BINOP.PLUS, new Tree.MEM(expId.unEx()), 
        		new Tree.BINOP(Tree.BINOP.MUL, e1.unEx(), new Tree.CONST(4))), e2.unEx()));
	}

	// TODO
	public Exp visit(ArrayLength n, SymbolTable symbolTable){
		return null;
	}

	// TODO
	public Exp visit(ArrayLookup n, SymbolTable symbolTable) {		
		return null;
	}

	// Identifier i;
	// Exp e;
	public Exp visit(Assign n, SymbolTable symbolTable) {
	    Exp i = n.i.accept(this, symbolTable);
	    Exp e = n.e.accept(this, symbolTable);
	    return new Nx(new Tree.MOVE(i.unEx(), e.unEx()));
	}

	public Exp visit(Block n, SymbolTable symbolTable){
        for ( int i = 0; i < n.sl.size(); i++ ) {
            n.sl.elementAt(i).accept(this, symbolTable);
      }
      return null;
	}

	public Exp visit(BooleanType n, SymbolTable symbolTable){return null;}

	// Exp e;
	// Identifier i;
	// ExpList el;
	public Exp visit(Call n, SymbolTable symbolTable) {
		Tree.ExpList el = null;
		
		for (int i = 0; i < n.el.size(); ++i){
			Exp ex = n.el.elementAt(i).accept(this, symbolTable);
			el = new Tree.ExpList (ex.unEx(), el);
		}

		return new Ex(new Tree.CALL(new Tree.NAME(new Temp.Label(n.e.accept(this, symbolTable).toString())), el)); 
	}
	
	// Identifier i;
	// Identifier j;
	// VarDeclList vl;
	// MethodDeclList ml;
	public Exp visit(ClassDeclExtends n, SymbolTable symbolTable) {
		currentClass = n.i.toString();
		for ( int i = 0; i < n.ml.size(); i++ ) {
			n.ml.elementAt(i).accept(this,symbolTable);
		}
		return null;
	}

	// Identifier i;
	// VarDeclList vl;
	// MethodDeclList ml;
	public Exp visit(ClassDeclSimple n, SymbolTable symbolTable) {
		currentClass = n.i.toString();
		for ( int i = 0; i < n.ml.size(); i++ ) {
			n.ml.elementAt(i).accept(this,symbolTable);
		}
		return null;
	}

	public Exp visit(False n, SymbolTable symbolTable) {
		return new Ex(new Tree.CONST(0));
	}

	public Exp visit(Formal n, SymbolTable symbolTable) {
		return null;
	}
	
	// String s;
	public Exp visit(Identifier n, SymbolTable symbolTable) {
		Temp.Label l = new Temp.Label(n.s);
	    return new Nx(new Tree.LABEL(l));
	}

	// TODO
	// String s;
	public Exp visit(IdentifierExp n, SymbolTable symbolTable) {
		return null;
	}    

	public Exp visit(IdentifierType n, SymbolTable symbolTable) {
		return null;
	}

	// Exp e;
	// Statement s1,s2;
	public Exp visit(If n, SymbolTable symbolTable) {
		Exp exp1 = n.e.accept(this, symbolTable);
		Exp exp2 = n.s1.accept(this, symbolTable);
		Exp exp3 = n.s2.accept(this, symbolTable);
		IfThenElseExp ifExp = new IfThenElseExp(exp1, exp2, exp3);
		ifExp.unEx();
		return ifExp;
	}

	public Exp visit(IntArrayType n, SymbolTable symbolTable) {
		return null;
	}

	// int i;
	public Exp visit(IntegerLiteral n, SymbolTable symbolTable) {
		return new Ex(new Tree.CONST(n.i));
	}

	public Exp visit(IntegerType n, SymbolTable symbolTable) {
		return null;
	}

	// Exp e1,e2;
	public Exp visit(LessThan n, SymbolTable symbolTable) {
		Exp exp1 = n.e1.accept(this, symbolTable);
		Exp exp2 = n.e2.accept(this, symbolTable); 
		return new RelCx(Tree.CJUMP.LT, exp1.unEx(), exp2.unEx());
	}
	
	// Identifier i1,i2;
	// Statement s;
	public Exp visit(MainClass n, SymbolTable symbolTable) {
		currentClass = symbolTable.getClass(n.i1.toString()).getId();
		currentMethod = "main";
		frame = symbolTable.getMethod(currentMethod,currentClass).frame();
		Exp exp = n.s.accept(this,symbolTable);
		ProcFrag frag = new ProcFrag(exp.unNx(), frame);
		frags.add(frag);
		currentMethod = null;
		return null;
	}

	// Type t;
	// Identifier i;
	// FormalList fl;
	// VarDeclList vl;
	// StatementList sl;
	// Exp e;
	public Exp visit(MethodDecl m, SymbolTable symbolTable){
		Tree.Stm stms;
		currentMethod = m.i.toString();

		frame = symbolTable.getMethod(currentMethod,currentClass).frame();
		Exp exp;
		//empty block
		if (m.sl.size()==0){
			stms = null;
			exp = m.e.accept(this,symbolTable); 
			procEntryExit(null,exp,frame);
		}
		// nonempty block
		else{ 
			exp = m.sl.elementAt(0).accept(this,symbolTable);
			stms= exp.unNx();
			for(int i = 1; i<m.sl.size();i++){
				exp = m.sl.elementAt(i).accept(this,symbolTable);
				stms=new Tree.SEQ(stms,exp.unNx());
			}
			exp = m.e.accept(this,symbolTable);
			procEntryExit(stms,exp,frame);
		}
		currentMethod = null;
		return null;
	}

	public Exp visit(Minus n, SymbolTable symbolTable){
		Exp exp1 = n.e1.accept(this, symbolTable);
		Exp exp2 = n.e2.accept(this, symbolTable);
		return new Ex(new Tree.BINOP(Tree.BINOP.MINUS, exp1.unEx(), exp2.unEx()));
	}

	public Exp visit(NewArray n, SymbolTable symbolTable){
		Exp e = n.e.accept(this, symbolTable);
		List<Tree.Exp> params = new ArrayList<Tree.Exp>();
		params.add(e.unEx());
		Temp.Temp t = new Temp.Temp();

		return new Ex(new Tree.ESEQ(new Tree.MOVE(new Tree.TEMP(t), frame.externalCall("newArray", params)), new Tree.TEMP(t)));
	}

	// TODO
	// Identifier i;
	public Exp visit(NewObject n, SymbolTable symbolTable) {
		return null;
	}

	// Exp e;
	public Exp visit(Not n, SymbolTable symbolTable){
		Exp exp = n.e.accept(this, symbolTable);
		return new Ex(new Tree.BINOP(Tree.BINOP.MINUS, new Tree.CONST(1), exp.unEx()));
	}
	
	// Exp e1,e2;
	public Exp visit(Plus n, SymbolTable symbolTable){
		Exp exp1 = n.e1.accept(this, symbolTable);
		Exp exp2 = n.e2.accept(this, symbolTable);
		return new Ex(new Tree.BINOP(Tree.BINOP.PLUS, exp1.unEx(), exp2.unEx()));
	}

	// Exp e;
	public Exp visit(Print n, SymbolTable symbolTable) {
		Exp exp = n.e.accept(this,symbolTable);
		List<Tree.Exp> args = new LinkedList<Tree.Exp>();
		args.add(exp.unEx());
		return new Ex(frame.externalCall("printint",args));
	}
	
	// MainClass m;
	// ClassDeclList cl;
	public Exp visit(Program n, SymbolTable symbolTable) {
		n.m.accept(this, symbolTable);
		for ( int i = 0; i < n.cl.size(); i++ ) 
			n.cl.elementAt(i).accept(this,symbolTable);
		DataFrag frag = new DataFrag(frame.programTail());
		frags.add(frag);
		return null;
	}
	
	// TODO
	public Exp visit(This n, SymbolTable symbolTable){
		return null;
	}
	
	public Exp visit(Times n, SymbolTable symbolTable){
		Exp exp1 = n.e1.accept(this, symbolTable);
		Exp exp2 = n.e2.accept(this, symbolTable);
		return new Ex(new Tree.BINOP(Tree.BINOP.MUL, exp1.unEx(), exp2.unEx()));
	}

	public Exp visit(True n, SymbolTable symbolTable) {
		return new Ex(new Tree.CONST(1));
	}

	public Exp visit(VarDecl n, SymbolTable symbolTable) {
		return null;
	}
		
	// Exp e;
	// Statement s;
	/*
      start:
            if (!test) goto done
	    body
	    goto start
      done:
	 */
	public Exp visit(While n, SymbolTable symbolTable) {
        Temp.Label test = new Temp.Label();
        Temp.Label T = new Temp.Label();
        Temp.Label F = new Temp.Label();
	    Exp exp = n.e.accept(this, symbolTable);
	    Exp body = n.s.accept(this, symbolTable);
	    
	    return new Nx(new Tree.SEQ(new Tree.SEQ(new Tree.SEQ(new Tree.LABEL(test),
	    		(new Tree.CJUMP(Tree.CJUMP.EQ, exp.unEx(),new Tree.CONST(1),T,F))),
	    		(new Tree.SEQ( new Tree.LABEL(T),body.unNx()))),new Tree.LABEL(F)));
	}
}