package org.dsinczak.fp.fsm;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @param <S> state type
 * @param <D> data type
 */
public abstract class FiniteStateMachine<S, D> {

    private static final Logger LOG = LoggerFactory.getLogger(FiniteStateMachine.class);

    private State<S, D> currentState;
    private State<S, D> nextState;

    private final Map<S, StateChangeFunction<S, D>> stateChangeFunctions = new HashMap<>();
    private final List<StateChangeConsumer<S>> stateChangeHandlers = new ArrayList<>();

    private StateChangeFunction<S, D> defaultStateChangeFunction = new StateChangeFunction<S, D>() {
        @Override
        public State<S, D> apply(Message<D> message) {
            LOG.warn("Unhandled message {}", message.content);
            return goTo(currentState.stateType);
        }

        @Override
        public boolean isDefinedAt(Message<D> value) {
            return true;
        }
    };

    private StateChangeFunction<S, D> unhandledStateChangeFunction = defaultStateChangeFunction;

    public final void handle(Object o) {
        Objects.requireNonNull(o, "FSM message must not be null");
        if (this.currentState == null) {
            throw new IllegalStateException("Finite state machine is not initialized. No initial state was set.");
        }
        var msg = new Message<>(o, this.currentState.stateData);
        handleMessage(msg);
    }

    public final void initialize() {
        if (currentState != null) changeState(currentState);
        else throw new IllegalArgumentException("No initial state was set.");
    }

    public final State<S, D> state() {
        return currentState;
    }

    public final S stateType() {
        return currentState.stateType;
    }

    public final D stateData() {
        return currentState.stateData;
    }

    public final D nextStateData() {
        if (nextState != null) return nextState.stateData;
        else throw new IllegalStateException("Next state is available only during sate change phase");
    }

    protected final void initState(S state, D data) {
        if (currentState != null) {
            throw new IllegalArgumentException("FSM already initialized.");
        }
        currentState = new State<>(state, data);
    }

    protected final void registerStateChangeFunction(S state, StateChangeFunction<S, D> function) {
        if (stateChangeFunctions.containsKey(state)) {
            stateChangeFunctions.computeIfPresent(state, (s, f) -> f.orElse(function));
        } else {
            stateChangeFunctions.put(state, function);
        }
    }

    protected final void registerUnhandledStateChangeFunction(StateChangeFunction<S, D> function) {
        this.unhandledStateChangeFunction = function.orElse(defaultStateChangeFunction);
    }

    protected final void registerStateChangeHandler(StateChangeConsumer<S> handler) {
        this.stateChangeHandlers.add(handler);
    }

    protected final State<S, D> goTo(S nextStateName) {
        return new State<>(nextStateName, currentState.stateData);
    }

    private void handleMessage(Message<D> message) {
        var newState = findStateChangeFunction(message)
                .map(sf -> sf.apply(message))
                .getOrElse(() -> unhandledStateChangeFunction.apply(message));
        changeState(newState);
    }

    private void changeState(State<S, D> newState) {
        if (!stateChangeFunctions.containsKey(newState.stateType)) {
            throw new IllegalStateException("Transition to state " + newState.stateType + " impossible. No such state defined.");
        }

        this.nextState = newState;
        LOG.info("FSM State change from {}, to {}", currentState.stateType, nextState.stateType);

        notifyStateChangeHandlers(Tuple.of(currentState.stateType, nextState.stateType));

        this.nextState = null;
        this.currentState = newState;
    }

    private void notifyStateChangeHandlers(Tuple2<S, S> stateChange) {
        stateChangeHandlers.stream()
                .filter(h -> h.isDefinedAt(stateChange))
                .forEach(h -> Try.of(() -> h.apply(stateChange)).onFailure(th -> LOG.error("Error running state change handler", th)));
    }

    private Option<StateChangeFunction<S, D>> findStateChangeFunction(Message<D> message) {
        if (stateChangeFunctions.containsKey(currentState.stateType)) {
            var fnc = stateChangeFunctions.get(currentState.stateType);
            if (fnc.isDefinedAt(message)) {
                return Option.of(fnc);
            }
        }
        return Option.none();
    }

}

class Message<D> {
    Object content;
    D stateData;

    public Message(Object content, D stateData) {
        this.content = content;
        this.stateData = stateData;
    }

    @Override
    public String toString() {
        return "Message{" +
                "content=" + content +
                ", stateData=" + stateData +
                '}';
    }
}

class State<S, D> {
    S stateType;
    D stateData;

    State(S stateType, D stateData) {
        this.stateType = stateType;
        this.stateData = stateData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;
        State<?, ?> state = (State<?, ?>) o;
        return Objects.equals(stateType, state.stateType) &&
                Objects.equals(stateData, state.stateData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stateType, stateData);
    }

    @Override
    public String toString() {
        return "State{" +
                "stateType=" + stateType +
                ", stateData=" + stateData +
                '}';
    }
}
