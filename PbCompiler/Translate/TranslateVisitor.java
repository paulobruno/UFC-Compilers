package Translate;

import syntaxtree.*;
import visitor.SymbolTable;

public interface TranslateVisitor {
	  public Exp visit(Program n, SymbolTable s);
	  public Exp visit(MainClass n, SymbolTable s);
	  public Exp visit(ClassDeclSimple n, SymbolTable s);
	  public Exp visit(ClassDeclExtends n, SymbolTable s);
	  public Exp visit(VarDecl n, SymbolTable s);
	  public Exp visit(MethodDecl n, SymbolTable s);
	  public Exp visit(Formal n, SymbolTable s);
	  public Exp visit(IntArrayType n, SymbolTable s);
	  public Exp visit(BooleanType n, SymbolTable s);
	  public Exp visit(IntegerType n, SymbolTable s);
	  public Exp visit(IdentifierType n, SymbolTable s);
	  public Exp visit(Block n, SymbolTable s);
	  public Exp visit(If n, SymbolTable s);
	  public Exp visit(While n, SymbolTable s);
	  public Exp visit(Print n, SymbolTable s);
	  public Exp visit(Assign n, SymbolTable s);
	  public Exp visit(ArrayAssign n, SymbolTable s);
	  public Exp visit(And n, SymbolTable s);
	  public Exp visit(LessThan n, SymbolTable s);
	  public Exp visit(Plus n, SymbolTable s);
	  public Exp visit(Minus n, SymbolTable s);
	  public Exp visit(Times n, SymbolTable s);
	  public Exp visit(ArrayLookup n, SymbolTable s);
	  public Exp visit(ArrayLength n, SymbolTable s);
	  public Exp visit(Call n, SymbolTable s);
	  public Exp visit(IntegerLiteral n, SymbolTable s);
	  public Exp visit(True n, SymbolTable s);
	  public Exp visit(False n, SymbolTable s);
	  public Exp visit(IdentifierExp n, SymbolTable s);
	  public Exp visit(This n, SymbolTable s);
	  public Exp visit(NewArray n, SymbolTable s);
	  public Exp visit(NewObject n, SymbolTable s);
	  public Exp visit(Not n, SymbolTable s);
	  public Exp visit(Identifier n, SymbolTable s);
}
