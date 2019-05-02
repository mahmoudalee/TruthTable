//====================================================================================================
//The Free Edition of C# to Java Converter limits conversion output to 100 lines per snippet.

//To subscribe to the Premium Edition, visit our website:
//https://www.tangiblesoftwaresolutions.com/order/order-csharp-to-java.html
//====================================================================================================

package com.example.truthtable;

import java.util.*;

public class StatementSplitters {
    private String complexStatement;
    private char[] simpleStatements;
    private boolean[] simpleStatementsValues;

    public final String getComplexStatement() {
        return complexStatement;
    }

    public final void setComplexStatement(String value) {
        complexStatement = value;
        simpleStatements = extractSimpleStatements(complexStatement);
        simpleStatementsValues = new boolean[simpleStatements.length];
    }

    public final char[] getSimpleStatements() {
        return simpleStatements;
    }

    public final boolean[] getSimpleStatementValues() {
        return simpleStatementsValues;
    }

    public final void setSimpleStatementValues(boolean[] value) {
        simpleStatementsValues = value;
    }


    public StatementSplitters() {
        setComplexStatement("");
    }

    public StatementSplitters(String ComplexStatement) {
        this.setComplexStatement(ComplexStatement);
    }

    public StatementSplitters(String ComplexStatement, boolean[] SimpleStatementsValues) {
        this.setComplexStatement(ComplexStatement);
        this.setSimpleStatementValues(SimpleStatementsValues);
    }

    private char[] extractSimpleStatements(String ComplexStatement) {
        String chars = "";
        ComplexStatement.replaceAll(" ","");
        ComplexStatement.toLowerCase();
        for (int i=0 ;i<ComplexStatement.length() ; i++){
            if(ComplexStatement.charAt(i)>='a' && ComplexStatement.charAt(i)<='z'){
                if(chars.indexOf(ComplexStatement.charAt(i)) < 0){
                    chars += ComplexStatement.charAt(i);
                }
            }
        }

        char[] simpleStatements = tangible.CharacterLists.toArray(chars);

        return simpleStatements;
    }

    private ArrayList<String> getWithinBraces(String cs) {
        int s = -1, e = -1, cnt = 0;
        ArrayList<String> subCsList = new ArrayList<String>();
        for (int i = 0; i < cs.length(); i++) {
            cnt += cs.charAt(i) == '(' ? 1 : 0;
            cnt -= cs.charAt(i) == ')' ? 1 : 0;

            if (cnt == 1 && cs.charAt(i) == '(') {
                s = i;
            } else if (cnt == 0 && cs.charAt(i) == ')') {
                e = i;
            }

            if (s != -1 && e != -1) {
                subCsList.add(cs.substring(s + 1, s + 1 + e - s - 1));
                s = -1;
                e = -1;
            }
        }
        return subCsList;
    }

    private String convertStatementsToBool() {
        String statement = complexStatement;
        for (int i = 0; i < simpleStatements.length; i++) {

            String[] temp = statement.split(java.util.regex.Pattern.quote(String.valueOf(simpleStatements[i])), -1);

            statement = tangible.StringHelper.join(simpleStatementsValues[i] == true ? "T" : "F", temp);
        }
        return statement;
    }

    private String solveAndOr(String cs) {
        if (cs.indexOf("^") > -1) {
            cs = cs.replace("T^T", "T");
            cs = cs.replace("F^T", "F");
            cs = cs.replace("T^F", "F");
            cs = cs.replace("F^F", "F");
        }

        if (cs.indexOf("&") > -1) {
            cs = cs.replace("T&T", "T");
            cs = cs.replace("F&T", "F");
            cs = cs.replace("T&F", "F");
            cs = cs.replace("F&F", "F");
        }

        if (cs.indexOf("|") > -1) {
            cs = cs.replace("T|T", "T");
            cs = cs.replace("F|T", "T");
            cs = cs.replace("T|F", "T");
            cs = cs.replace("F|F", "F");
        }


        return cs;
    }

    private String solveConditionalBiStatements(String cs) {

        if (cs.indexOf("<->") > -1) {
            cs = cs.replace("T<->T", "T");
            cs = cs.replace("T<->F", "F");
            cs = cs.replace("F<->T", "F");
            cs = cs.replace("F<->F", "T");
        }

        if (cs.indexOf("->") > -1) {
            cs = cs.replace("T->T", "T");
            cs = cs.replace("T->F", "F");
            cs = cs.replace("F->T", "T");
            cs = cs.replace("F->F", "T");
        }

        return cs;
    }

    private String solveNegation(String cs) {
        cs = cs.replace("!F", "T");
        cs = cs.replace("!T", "F");

        return cs;
    }

    public final boolean solve() {
        return solve(convertStatementsToBool());
    }

    private boolean solve(String cs) {

        if (cs.indexOf("(") > -1) {
            ArrayList<String> ls = getWithinBraces(cs);
            for (String item : ls) {
                boolean result = solve(item);
                String temp = "(" + item + ")";
                cs = cs.replace(temp, result == true ? "T" : "F");
            }
        }

        if (cs.indexOf("!") > -1) {
            cs = solveNegation(cs);
        }

        if (cs.indexOf("^") > -1 || cs.indexOf("&") > -1 || cs.indexOf("|") > -1) {
            cs = solveAndOr(cs);
        }

        if (cs.indexOf("->") > -1 || cs.indexOf("<->") > -1) {
            cs = solveConditionalBiStatements(cs);
        }

        return cs.equals("T") ? true : false;
    }
}
