package org.magiaperro.operations;

@FunctionalInterface
public interface OperationConsumer {
    void run(int cycle);
}
