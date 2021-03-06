package syn;
import java.io.*;
import java.util.*;
%%

%unicode
%line
%column
%cup
%implements Symbols

%{
    static symbol_table.Symbol symbol(String s) {
		return symbol_table.Symbol.symbol(s);
	}
	private int commentCount = 0;
	private String str="";
	private void err(String message) {
		System.out.println("Scanning error in line " + yyline + ", column " + yycolumn + ": " + message);
	}

	private java_cup.runtime.Symbol tok(int kind) {
		return new java_cup.runtime.Symbol(kind, yyline, yycolumn);
	}

	private java_cup.runtime.Symbol tok(int kind, Object value) {
		return new java_cup.runtime.Symbol(kind, yyline, yycolumn, value);
	}
	private Integer change(String st)
	{
		if (st.length()==1) return st.charAt(0)-'0';
		if (st.charAt(0)=='0' && st.charAt(1)!='x' && st.charAt(1)!='X')
		{
		    Integer t=st.charAt(1)-'0';
		    for (int i=2;i<=st.length()-1;++i)
		        t=t*8+(st.charAt(i)-'0');
		    return t;
		}
		else if (st.charAt(0)=='0')
		{
			Integer t;
			if (st.charAt(2)>='0' && st.charAt(2)<='9') t=st.charAt(2)-'0';
			else if (st.charAt(2)>='a' && st.charAt(2)<='f') t=st.charAt(2)-'a'+10;
			else t=st.charAt(2)-'A'+10;
		    for (int i=3;i<=st.length()-1;++i)
		    	if (st.charAt(i)>='0' && st.charAt(i)<='9') t=t*16+(st.charAt(i)-'0');
				else if (st.charAt(i)>='a' && st.charAt(i)<='f') t=t*16+(st.charAt(i)-'a')+10;
				else t=t*16+(st.charAt(i)-'A')+10;
		    return t;
		}
		else
		{
			Integer t=st.charAt(0)-'0';
		    for (int i=1;i<=st.length()-1;++i)
		        t=t*10+(st.charAt(i)-'0');
		    return t;
		}
	}
%}

%eofval{
	{
		if (yystate() == YYCOMMENT) {
			err("Comment symbol do not match (EOF)!");
		}
		return tok(EOF, null);
	}
%eofval}

LineTerm = \n|\r|\r\n
Identifier = [A-Za-z$_][A-Za-z$_0-9]*
Number = ([0-9]+)|(0[0-7]+)|(0[Xx][a-fA-F0-9]+)
Whitespace = {LineTerm}|[ \t\f]

%state	YYCOMMENT
%state STRING
%state YYCOM

%%

<YYINITIAL> {
      "/*" { commentCount = 1; yybegin(YYCOMMENT); }
	"*/" { err("Comment symbol do not match!"); }
	"//" { yybegin(YYCOM);}

	 "\""                             { str="";yybegin(STRING); }

	"int"    {return tok(INT); }
      "for"    {return tok(FOR); }
	"return" {return tok(RETURN); }

	"*" {return tok(TIMES); }
	"(" {return tok(LPAREN); }
	")" {return tok(RPAREN); }
	"{" {return tok(LBRACE); }
	"}" {return tok(RBRACE); } 
      "[" {return tok(LBRACKET); }
      "]" {return tok(RBRACKET); }
      "++" {return tok(SELFPLUS);}

	";" {return tok(SEMICOLON); }
      "," {return tok(COMMA); }

      "+=" {return tok(PLUS_ASSIGN); }
      
	"<"  {return tok(LT); }

	"=" {return tok(ASSIGN); } 

	{Identifier} { return tok(ID,yytext());}
	{Number} {return tok(NUM, change(yytext())); }
	{LineTerm} {}
    {Whitespace} {}

	[^] { throw new RuntimeException("Illegal character " + yytext() + " in line " + (yyline + 1) + ", column " + (yycolumn + 1)); }
}

<STRING> {
  "\""                             { yybegin(YYINITIAL);
                                     return tok(String_const,str); }
  "\\t"                            { str+="\\t"; }
  "\\n"                            { str+="\\n"; }

  "\\r"                            { str+="\\r"; }
  "\\\""                           { str+="\\\""; }
  "\\"                             { str+='\\'; }
  [^]                              { str+= yytext() ; }
}

<YYCOMMENT> {
	"/*" { commentCount++; }
	"*/" { commentCount=0; if (commentCount == 0) yybegin(YYINITIAL); }
	[^]  {}
}

<YYCOM>
{
    [\n]  {yybegin(YYINITIAL);}
	[^] {}
}
