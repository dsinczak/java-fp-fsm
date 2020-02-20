package org.dsinczak.fp.fsm;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SimpleFsmTest {

    enum States {A, B, C, NotSupported}

    static class FsmNotInitialized extends FiniteStateMachineDsl<States, String> {
    }

    static class FsmInitializedWithNoState extends FiniteStateMachineDsl<States, String> {
        { initialize(); }
    }

    static class Fsm extends FiniteStateMachineDsl<States, String> {

        private Set<String> stateChanges = new HashSet<>();
        private Set<Object> unhandledMessages = new HashSet<>();

        {
            startWith(States.A, "init state A");

            when(States.A,
                    matchMessageEquals("go to B", (msg, data) -> goTo(States.B).using("went from A to B"))
                            .matchMessage(String.class, (msg, data) -> "go to C".equals(msg), (msg, data) -> goTo(States.C).using("went from A to C"))
            );

            onStateChange(
                    matchState(States.A, States.B, (a, b) -> stateChanges.add("Went from A to B"))
                            .matchState(States.A, States.C, (a, c) -> stateChanges.add("Went from A to C"))
                            .matchState(States.B, States.C, (b, c) -> stateChanges.add("Went from B to C"))
                            .matchState(States.B, States.A, (b, a) -> stateChanges.add("Went from B to A"))
            );

            whenUnhandled(
                    matchAnyMessage((msg, data) -> {
                        unhandledMessages.add(msg);
                        return stay();
                    })
            );

            initialize();
        }

        public Set<String> getStateChanges() {
            return stateChanges;
        }

        public Set<Object> getUnhandledMessages() {
            return unhandledMessages;
        }
    }

    @Test
    public void shouldFailToHandleMessagesWhenItsNotInitialized() {
        // Given
        var fsm = new FsmNotInitialized();
        // When, Then
        assertThatThrownBy(() -> fsm.handle("do something"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Finite state machine is not initialized. No initial state was set.");
    }

    @Test
    public void shouldFailToInitializedFsmWithoutInitialState() {
        assertThatThrownBy(FsmInitializedWithNoState::new)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("No initial state was set.");
    }
}
