package org.example.fundamental;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledForJreRange;
import org.junit.jupiter.api.condition.DisabledIf;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

@DisplayName("< Examples of Jupiter build-in condition execution >")
class JupiterConditionExecutionTest {

    @Test
    @DisabledOnJre(value = JRE.JAVA_17, disabledReason = "disabled due to JDK 17")
    void disabledWhenJDK17() {

    }

    @Test
    @EnabledOnJre(value = JRE.JAVA_17)
    void enabledWhenJDK17() {

        System.out.println(System.getProperty("java.version"));
    }

    @Test
    @DisabledForJreRange(min = JRE.JAVA_9, max = JRE.JAVA_19)
    void disableWhenJdkMatchedRange() {

    }

    @Test
    @EnabledForJreRange(min = JRE.JAVA_9, max = JRE.JAVA_19)
    void enableWhenJdkMatchedRange() {

    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void enableOnWins() {

    }

    @Test
    @DisabledOnOs(OS.WINDOWS)
    void disableOnWins() {

    }

    @Test
    @EnabledIfEnvironmentVariable(named = "M2_HOME", matches = ".*")
    void enabledIfEnvironmentContainsMavenHome() {

    }

    @Test
    @EnabledIfSystemProperty(named = "env", matches = "dev")
    void enabledIfSystemProperty() {

    }

    @Test
    @EnabledIf("customCondition")
        //IF FALSE => skip the test
    void customEnabledIf() {

    }

    @Test
    @DisabledIf("customCondition")
        //IF FALSE => skip the test
    void customDisabledIf() {

    }

    private boolean customCondition() {

        return Boolean.TRUE;
    }

    @BeforeEach
    void setUp() {

        System.getProperties()
              .put("env", "dev");
    }

    //is, equalTo are matchers

    @Test
    void testByHamcrestMatcher() {

        assertThat("Hello", anyOf(is("Hello"), equalTo("Hello"), containsString("llo"), endsWith("o")));
    }
}
