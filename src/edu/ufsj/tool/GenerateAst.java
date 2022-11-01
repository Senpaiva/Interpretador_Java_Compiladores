package edu.ufsj.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class GenerateAst {
    public static void main(String[] args) throws IOException {
        if(args.length !=1){
            System.err.println("Usage: java edu.ufsj.tool.GenerateAst <output dir>");
            System.exit(64);
        }
        String outputDir = args[0];
        defineAst(outputDir, "Expr",Arrays.asList(
            "Binary: Expr left, Token Operator, Expr right",
			"Ternary: Expr left, Token leftOperator, Expr middle, Token rightOperator, Expr right",
            "Grouping: Expr expression",			
            "Unary: Token operator, Expr right",
            "Literal: Object value"
        ));
        
    }

    private static void defineAst ( String outputDir ,
		String baseName , List < String > types )
		throws IOException {
		String path = outputDir + " / " + baseName +
		" . java " ;
		PrintWriter writer =
		new PrintWriter ( path , " UTF -8 " );
		writer.println ( " package edu.ufsj.lox ; " );
		writer.println ();
		writer.println ( " import java.util.List ; " );
		writer.println ();
		writer.println ( " abstract class " + baseName + " { " );
		defineVisitor(writer, baseName, types);
        writer.println("    abstract <R> R accept(Visitor<R> visitor);");
		writer.println ( " } " );
		writer.close ();
		}
    
    private static void defineVisitor (
    		PrintWriter writer , String baseName ,
    		List < String > types ) {
    		writer . println ( " interface Visitor <R > { " );
    		for ( String type : types ) {
    		String typeName = type . split ( " : " )[0]. trim ();
    		writer . println ( " R visit " + typeName +
    		baseName + " ( " + typeName + " " +
    		baseName . toLowerCase () + " ); " );
    		}
    		writer . println ();
    		writer . println ( " abstract <R > R accept " +
    		" ( Visitor <R > visitor ); " );
    		writer . println ( " } " );
    		}

    private static void defineType ( PrintWriter writer ,
		String baseName , String className ,
		String fieldList ) {
		writer . println ( " static class " + className +
		" extends " + baseName + " { " );
		// construtor
		writer . println ( " " + className + " ( " +
		fieldList + " ) { " );
		// inicializa campos
		String [] fields = fieldList . split ( " , " );
		for ( String field : fields ) {
		String name = field . split ( " " )[1];
		writer . println ( " this . " + name +
		" = " + name + " ; " );
		}
		writer . println ( " } " ); // fecha def construtor
		writer . println ();
		writer . println ("		@Override " );
		writer . println ("		<R > R accept ( Visitor <R > " +
					" visitor ) { " );
		writer . println ("		 return visitor.visit " +
				className + baseName + " ( this ); " );
		writer.println ( " } " );
	}
}
