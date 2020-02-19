package org.dsinczak.fp.fsm;

import io.vavr.PartialFunction;
import io.vavr.Tuple2;

public interface StateChangeConsumer<S> extends PartialFunction<Tuple2<S, S>, Void> {
}
