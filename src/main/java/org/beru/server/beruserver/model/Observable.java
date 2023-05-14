package org.beru.server.beruserver.model;

@FunctionalInterface
public interface Observable {
    void notifyObserver();
}
