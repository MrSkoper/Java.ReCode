package skop.site.java.recode.deobfuscator.core.mappings;

import java.io.IOException;
import java.io.Reader;

public class ProguardMap implements MapInterface {
    
    protected enum TokenEnum {
        DIRECTION("->", 0),
        
        DELIMITER(":", 1),
        EXPANDER(":", 2),
        
        COMPONENT("\t", 3);
    
        private final char[] chars;
        private final int id;
        
        TokenEnum (String string, int id) {
            this.chars = string.toCharArray();
            this.id = id;
        }
    
        public int getId () {
            return id;
        }
        
        public char[] getChars () {
            return chars;
        }
        
        public boolean isLong () {
            return chars.length > 1;
        }
    }
    
    public static class ParserStream {
        
        private final Reader reader;
        
        public ParserStream (Reader reader) {
            this.reader = reader;
        }
        
        private StringBuffer stringBuffer = new StringBuffer();
        
        private void loop () throws IOException {
            int length;
            char[] chars = new char[1];
            while ((length = reader.read(chars)) != -1) {
                if (isToken(chars[0]))
            }
        }
        
        
        private TokenEnum token;
        private char[] tokenBuffer = new char[0];
        
        private boolean isToken (final char character) {
            for (TokenEnum tokenEnum: TokenEnum.values()) {
                this.token = tokenEnum;
                
                char[] chars = tokenEnum.getChars();
                if (!tokenEnum.isLong()) return character == chars[0];
                
                char[] buffer = new char[tokenBuffer.length + 1];
                System.arraycopy(tokenBuffer, 0, buffer, 0, tokenBuffer.length);
                buffer[tokenBuffer.length] = character;
                tokenBuffer = buffer;
                
                return tokenBuffer == chars;
            }
            
            return false;
        }
        
        public TokenEnum readToken () throws IOException {
            
    
            return new TokenEnum();
        }
        
    }
    
}
