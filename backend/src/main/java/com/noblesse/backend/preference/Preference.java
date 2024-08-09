package com.noblesse.backend.preference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="preference")
public class Preference {
    @Id
    @Column(name="PREFERENCE_ID")
    private int preferenceId;

    @Column(name="PREFERENCE_NAME")
    private String preferenceName;

    public Preference() {}

    public Preference(int preferenceId, String preferenceName) {
        this.preferenceId = preferenceId;
        this.preferenceName = preferenceName;
    }

    public int getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(int preferenceId) {
        this.preferenceId = preferenceId;
    }

    public String getPreferenceName() {
        return preferenceName;
    }

    public void setPreferenceName(String preferenceName) {
        this.preferenceName = preferenceName;
    }

    @Override
    public String toString() {
        return "Preference{" +
                "preferenceId=" + preferenceId +
                ", preferenceName='" + preferenceName + '\'' +
                '}';
    }
}
