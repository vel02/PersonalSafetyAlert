package com.sti.research.personalsafetyalert.service;


import com.sti.research.personalsafetyalert.service.state.ConfigureState;
import com.sti.research.personalsafetyalert.service.state.Unchanged;

public class ConfigurationChangeStatus {

    private ConfigureState state;

    public ConfigurationChangeStatus() {
        this.state = new Unchanged();
    }

    public void onConfigChange() {
        this.state = this.state.onChanged();
    }

    public void onConfigUnchanged() {
        this.state = this.state.onUnchanged();
    }

    public boolean isConfigRemainUnchanged() {
        return this.state instanceof Unchanged;
    }
}
