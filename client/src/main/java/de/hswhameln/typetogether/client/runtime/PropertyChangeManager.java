package de.hswhameln.typetogether.client.runtime;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Class that handles listening to property changes and calling the correct
 * methods accordingly. For each Property Id, a list of Method is stored that
 * are called when a property with the fitting propertyId is modified.
 *
 */
public class PropertyChangeManager implements PropertyChangeListener {

    /**
     * Map which stores a List of PropertyChangeHandler responsible for a Property
     */
    public Map<String, List<Consumer<PropertyChangeEvent>>> propertyChangeHandlers;

    public PropertyChangeManager() {
        this.propertyChangeHandlers = new HashMap<>();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.propertyChangeHandlers
                .computeIfAbsent(evt.getPropertyName(), k -> new ArrayList<>())
                .forEach(propertyChangeHandler -> propertyChangeHandler.accept(evt));
    }

    /**
     * Adds a method that will be called when the source Object's property with the
     * given name is changed.
     *
     * @param propertyName Name of the property to be observed
     * @param consumer     Method to be called on property change
     */
    public void onPropertyChange(String propertyName, Consumer<PropertyChangeEvent> consumer) {
        this.propertyChangeHandlers.computeIfAbsent(propertyName, k -> new ArrayList<>()).add(consumer);
    }

}