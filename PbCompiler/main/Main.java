package main;

import java.io.*;
import java.util.List;

import syntaxtree.Program;
import visitor.BuildSymbolTableVisitor;
import visitor.PrettyPrintVisitor;
import visitor.TypeCheckVisitor;
import Translate.Frag;
import analizer.MiniJavaParser;
import analizer.ParseException;
import analizer.TokenMgrError;

public class Main {
	public static void main(String args[]) throws ParseException {
		MiniJavaParser parser;
		try {
			parser = new MiniJavaParser(new FileInputStream(
					//"E:/ArquivoFonteTeste.txt"));
					"C:/Users/Lucas/Dropbox/UFC/Compiladores/MiniJavaFinal/ufc-2015-compiladores-4/PbCompiler/src/main/ArquivoFonteTeste.txt"));
			Program root = parser.parse();

			BuildSymbolTableVisitor v1 = new BuildSymbolTableVisitor();
			root.accept(v1);
			root.accept(new TypeCheckVisitor(v1.getSymTab()));
			root.accept(new PrettyPrintVisitor());
			
			//translation
			Translate.Translate translator = new Translate.Translate(new Mips.MipsFrame());
/*			translator.translate(root);
			List<Frag> frags = translator.getFrags(); 
			//imprimir frags
			try {
				FileWriter outfile = new FileWriter(
						//"E:/TranslateOutput.txt");
						"C:/Users/Lucas/Dropbox/UFC/Compiladores/MiniJavaFinal/ufc-2015-compiladores-4/PbCompiler/src/main/TranslateOutput.txt");
				PrintWriter writeFile = new PrintWriter(outfile);

				writeFile.printf("Translator: %n");

				for (Frag f : frags) {
					writeFile.printf("TODO");
				}
				writeFile.printf("%n");			

				outfile.close();
			} catch (IOException e) {
				e.printStackTrace();
			} */
			
			
			
			System.out.println("Successful!");
		} catch (FileNotFoundException e) {
			System.out.println("Fail! File not found: " + args[0]);
			return;
		} catch (TokenMgrError e) {
			System.out.println("Fail! TokenMgrError: " + e);
			return;
		} catch (Error e) {
			System.out.println("Fail! Error: " + e);
			return;
		}
	}
}

/*
 * public class Main { public static void main(String args []) throws
 * ParseException { MiniJavaParser parser; try { parser = new MiniJavaParser(new
 * FileInputStream("E:/ArquivoFonteTeste.txt")); } catch (FileNotFoundException
 * e) { System.out.println("File not found: " + args [0]); return; } try {
 * parser.Program(); } catch (ParseException e) { System.out.println(e); return;
 * } catch (TokenMgrError e) { System.out.println(e); return; }
 * System.out.println("AAAAA Successful parse");
 * 
 * } }
 */