package skop.site.java.recode.deobfuscator.core.mappings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class ProguardMap implements MapInterface {
    
    public List<ClassToken> tokenClasses = new ArrayList<>();
    
    public void parse (Reader reader) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            StringBuilder stringBuilder = new StringBuilder();
    
            int length;
            char[] buffer = new char[1];
            
            while ((length = bufferedReader.read(buffer)) != -1) {
                stringBuilder.append(buffer).setLength(length);
                
            }
        }
    }

    private static final String DIRECTION_TOKEN = "->";
    private static final String METHOD_DELIMITER_TOKEN = ":";
    
    private static class ClassToken {
        
        private String name;
        private String direct;
        
        private VariableToken[] variables;
        private MethodToken[] methods;
        
    }
    
    private static class VariableToken {
        
        private String name;
        private Class<?> type;
        private String direct;
        
    }
    
    private static class MethodToken {
        
        private String name;
        private Class<?> rType; // Return Type
        private int line;
        private int column;
        
    }
    
}
