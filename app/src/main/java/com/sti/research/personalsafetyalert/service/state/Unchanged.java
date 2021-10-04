package com.sti.research.personalsafetyalert.service.state;

public class Unchanged implements ConfigureState {


    @Override
    public ConfigureState onChanged() {
        return new Changed();
    }

    @Override
    public ConfigureState onUnchanged() {
        return this;
    }
}