package edu.ufsj.lox;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

import static edu.ufsj.lox.TokenType.*;

class Scanner {
	private final String source;
	private final List<Token> tokens = new ArrayList<Token>();
	private int start = 0;
	private int current = 0;
	private int line = 1;
	
	Scanner(String source){
		this.source = source;
	}
    
	private static final Map<String, TokenType> keywords; static {
	    keywords = new HashMap<>();
	    keywords.put("and", AND);
	    keywords.put("class", CLASS);
	    keywords.put("else", ELSE);
	    keywords.put("false", FALSE);
	    keywords.put("for", FOR);
	    keywords.put("fun", FUN);
	    keywords.put("if", IF);
	    keywords.put("nil", NIL);
	    keywords.put("or", OR);
	    keywords.put("print", PRINT);
	    keywords.put("return", RETURN);
	    keywords.put("super", SUPER);
	    keywords.put("this", THIS);
	    keywords.put("ture", TRUE);
	    keywords.put("var", VAR);
	    keywords.put("while", WHILE);
	}
  
	List<Token> scanTokens(){
		while(!isAtEnd()){ //add tokens ate chegar ao final da string
			//inicio do proximo lexema
			start = current;
			scanToken();
	}
		tokens.add(new Token(EOF,"",null,line)); //construtor do token
	    return tokens;
	}

	private void scanToken(){
	    char c = advance();
	    switch(c) {
	        case '(': addToken(LEFT_PAREN); break;
	        case ')': addToken(RIGHT_PAREN); break;
	        case '{': addToken(LEFT_BRACE); break;
	        case '}': addToken(RIGHT_BRACE); break;
	        case ',': addToken(COMMA); break;
	        case '.': addToken(DOT); break;
	        case '-': addToken(MINUS); break;
	        case '+': addToken(PLUS); break;
	        case ';': addToken(SEMICOLON); break;
	        case '*': addToken(STAR); break;
			case '?': addToken(QUESTION); break;
			case ':': addToken(COLON); break;
	        case '!': addToken(match('=') ? BANG_EQUAL : BANG); break;
	        case '=': addToken(match('=') ? EQUAL_EQUAL : EQUAL); break;
	        case '<': addToken(match('=') ? LESS_EQUAL : LESS); break;
	        case '>': addToken(match('=') ? GREATER_EQUAL : GREATER); break;
		    case ' ':
		    case '\r':
		    case '\t':
		        break;
		    case '\n':
		        line ++; 
		        break;
		    case '/':
		        if(match('/')) {
		          //comentarios de uma linha
		        	while(peek() != '\n' && !isAtEnd())
		        		advance();
		        }
		        //comentarios de multiplas linhas
		        else if(match('*')) {
			    	if(isAtEnd()) {
			    		Lox.error(line, "unterminated  comment"); 
			    		return;
			    	}
			    	else {
			    		multicomment(); 
			    	}
			    }
		         else {
		            addToken(SLASH);
		        } break;
		      case '"': string(); break;
		      case 'o': 
			      if (peek() == 'r'){
			        addToken(OR);
		      } break;
		
		      default :
		    	  if ( isDigit (c)) {
		    		  number ();
		    	  } else if (isAlpha(c)) {
		    		  identifier();
		    	  } else {
		    		  Lox.error(line,"unexpected character.");
		    	  } break;
	    }
	  }
  
	private void identifier(){
		while(isAlphaNumeric(peek())) advance();

	    String text = source.substring(start, current);
	    TokenType type = keywords.get(text);

	    if (type == null) type = IDENTIFIER;
	    addToken(type);
	}

	private void string(){
	  
	    while(peek() != '"' && !isAtEnd()){
		    if(peek()== '\n') line ++;
		    advance();
	    }
	    if(isAtEnd()){
	      Lox.error(line, "unterminated string.");
	      return;
	    }
	    //o fecha "
	    advance();
	    //remove os "s
	    String value = source.substring(start + 1, current - 1); //extrai valor literal da string
	    addToken(STRING, value);
	  }
  
	private void multicomment() {
	  
		while(true) {
			if(peek() == '*') {
				advance();
				if(match('/')) return;
			}
			else if(peek() == '/') {
				advance();
				if(match('*')) {
					multicomment();
				}
			}
			else if(peek() == '\n') {
				line++;
				}
		  
			else if(!isAtEnd()) {
	        	advance();
	      }
	  }
  }
 

	private boolean isDigit(char c) {
		return c >= '0' && c <= '9';
  }
  
	private void number(){
		while (isDigit(peek())) advance();
		//procura a parte fracionaria
		if (peek() == '.' && isDigit(peekNext())) {
		//consome o "."
		    advance();
		    while (isDigit(peek())) advance();
		}
		addToken(NUMBER, Double.parseDouble(source.substring(start, current)));
  }

	private char peekNext(){
		if(current + 1 >= source.length())
			return '\0';
		return source.charAt(current+1);
  }

	private boolean isAlpha(char c){
	    return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_';
	}
	
	private boolean isAlphaNumeric(char c){
	    return isAlpha(c) || isDigit(c);
	}
	private boolean isAtEnd(){
	    return current >= source.length();
	 }
	private char advance(){
	    current++;
	    return source.charAt(current-1);
	}
	private boolean match(char expected){
	    if(isAtEnd()) return false;
	    if(source.charAt(current)!= expected) return false;
	    current++;
	    return true;
	}
	public void addToken(TokenType type){
		addToken(type, null);
	}

	public void addToken(TokenType type, Object literal){
	    String text = source.substring(start, current);
	    tokens.add(new Token(type,text,literal,line));
	  }

	private char peek(){
	    if(isAtEnd()) return '\0';
	    return source.charAt(current);
	  }

}
