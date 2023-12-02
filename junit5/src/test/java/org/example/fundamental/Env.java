package org.example.fundamental;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/*
    ElementType constants provide a classification where annotation may appear in a java program.
    Here: ElementType.TYPE - class/interface level; ElementType.METHOD - method level;
    Retention indicates how long annotations with the annotated interface are to be retained
*/

public interface Env {

    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Tag("dev")
    @Test
    @interface Dev {

    }

    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Tag("sit")
    @Test
    @interface Sit {

    }

    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Tag("uat")
    @Test
    @interface Uat {

    }

    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Tag("prod")
    @Test
    @interface Prod {

    }

    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Dev
    @Sit
    @Uat
    @Prod
    @Test
    @interface All {

    }
}
