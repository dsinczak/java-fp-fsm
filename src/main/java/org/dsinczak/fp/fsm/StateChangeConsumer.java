package org.dsinczak.fp.fsm;

import org.dsinczak.fp.pf.PartialFunction2;

public interface StateChangeConsumer<S> extends PartialFunction2<S, S, Void> {
}
