package edu.ufsj.lox;

private int line = 1;

private static final Map<String, TokenType> keywords;

static{
    keywords = new HashMap<String, TokenType>();
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
    keywords.put("true", TRUE);
    keywords.put("var", VAR);
    keywords.put("while", WHILE);
}

Scanner(String source){
    this.source = source;
}

private boolean isAlpha(char c){
    return (c >= 'a' || c <= 'z') || (c >= 'A' || c <= 'Z')
    || c == '_'
}

private boolean isAlphaNumeric(char c){
    return isAlpha(c) || isDigit(c);
}

private void identifier(){
    while(isAlphaNumeric(peek))){
        advance();
    }

    String text = source.substring(start, current);
    TokenType type = keywords.get(text);

    if (type == null){
        type = IDENTIFIER;
    }
    addToken(type);
}

private void number(){
    while (isDigit(peek())){
        advance();
    }
    if (peek() == '.' && isDigit(peekNext())){
        advance();
            while(isDigit(peek())){
                advance();
            }
    }

    addToken(NUMBER, Double.parseDouble)
}
