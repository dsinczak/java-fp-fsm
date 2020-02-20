package org.dsinczak.fp.func;

import org.junit.jupiter.api.Test;

import java.util.Date;
import static org.assertj.core.api.Assertions.*;

public class PartialFunctionBuilderTest {

    @Test
    public void shouldMatchClassToItsHandlingFunction() {
        // Given
        var pf = new PartialFunctionBuilder<Object, String>()
                .match(String.class, s->"object is string")
                .match(Date.class, d->"object is date")
                .match(Integer.class, i -> "object is int")
                .build();

        // When, Then
        assertThat(pf.isDefinedAt("string")).isEqualTo(true);
        assertThat(pf.apply("string")).isEqualTo("object is string");

        assertThat(pf.isDefinedAt(new Date())).isEqualTo(true);
        assertThat(pf.apply(new Date())).isEqualTo("object is date");

        assertThat(pf.isDefinedAt(Integer.MAX_VALUE)).isEqualTo(true);
        assertThat(pf.apply(Integer.MAX_VALUE)).isEqualTo("object is int");

        assertThat(pf.isDefinedAt(Double.MAX_VALUE)).isEqualTo(false);

        assertThatThrownBy(()->pf.apply(Double.MAX_VALUE)).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void shouldMatchClassToItsHandlingFunctionWithPredicateCheck() {
        // Given
        var pf = new PartialFunctionBuilder<Object, String>()
                .match(String.class, s->s.contains("cat"), s->"string with cat")
                .match(String.class, s->s.contains("dog"), s->"string with dog")
                .match(Date.class, d-> "just a date")
                .build();

        // When, Then
        assertThat(pf.isDefinedAt("i hate cats")).isEqualTo(true);
        assertThat(pf.apply("i hate cats")).isEqualTo("string with cat");

        assertThat(pf.isDefinedAt("i love dogs")).isEqualTo(true);
        assertThat(pf.apply("i love dogs")).isEqualTo("string with dog");

        assertThat(pf.isDefinedAt(new Date())).isEqualTo(true);
        assertThat(pf.apply(new Date())).isEqualTo("just a date");
    }

    @Test
    public void shouldMatchValueToItsHandlingFunctionOrGoDefault() {
        // Given
        var pf = new PartialFunctionBuilder<Object, String>()
                .matchEquals("BMW", s->"this is BMW")
                .matchEquals("Renault", s->"its going to break a lot")
                .matchEquals(Integer.MAX_VALUE, i->"To the maxxxx")
                .matchAny(any->"anything else")
                .build();

        // When, Then
        assertThat(pf.isDefinedAt("BMW")).isEqualTo(true);
        assertThat(pf.apply("BMW")).isEqualTo("this is BMW");

        assertThat(pf.isDefinedAt("Renault")).isEqualTo(true);
        assertThat(pf.apply("Renault")).isEqualTo("its going to break a lot");

        assertThat(pf.isDefinedAt(Integer.MAX_VALUE)).isEqualTo(true);
        assertThat(pf.apply(Integer.MAX_VALUE)).isEqualTo("To the maxxxx");

        assertThat(pf.isDefinedAt(new Date())).isEqualTo(true);
        assertThat(pf.apply(new Date())).isEqualTo("anything else");

    }
}
