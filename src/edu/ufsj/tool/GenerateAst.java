package edu.ufsj.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class GenerateAst {
    public static void main(String[] args) throws IOException{
        if (args.length != 1){
            System.err.println("Usage: java edu.ufsj.tool.GenerateAst" + "<output dir>");
            System.exit(64);
        }
        String outputDir = args[0];
        defineAst(outputDir, "Expr", Arrays.asList(
            "Binary  : Expr left, Token operator, Expr right",
            "Grouping: Expr expression",
            "Literal : Object value",
            "Unary   : Token operator, Expr right"));
    }

    private static void defineAst(String outputDir, String baseName, List<String> types) throws IOException{
            String path = outputDir + "/" + baseName + ".java";
            PrintWriter writer = new PrintWriter(path);
            writer.println("package edu.ufsj.tool;");
            writer.println();
            writer.println("import java.util.List;");
            writer.println();
            writer.println("class " + baseName + "(");

            writer.println(")");
            writer.close();
    }
}