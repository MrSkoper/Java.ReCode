package site.skop.java.recode.deobfuscator.core.entities.transformations;

import site.skop.java.recode.deobfuscator.core.transformations.Transformation;

import java.util.Iterator;

public interface TransformationEntity extends Iterator<Transformation> {
    
    TransformationEntity parent ();
    
    TransformationEntity children ();
    
    interface Reader {
        
        Transformation read ();
        
    }
    
    interface Writer {
        
        void write (Transformation transformation);
        
    }
    
}
