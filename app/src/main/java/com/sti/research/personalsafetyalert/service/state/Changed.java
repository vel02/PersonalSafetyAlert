package com.sti.research.personalsafetyalert.service.state;

public class Changed implements ConfigureState {

    @Override
    public ConfigureState onChanged() {
        return this;
    }

    @Override
    public ConfigureState onUnchanged() {
        return new Unchanged();
    }
}
