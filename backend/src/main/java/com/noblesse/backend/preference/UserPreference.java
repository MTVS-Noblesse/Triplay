package com.noblesse.backend.preference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="user_preference")
public class UserPreference {
    @Id
    @Column(name="user_preference_id")
    private int userPreferenceId;

    @Column(name="user_id")
    private int userId;

    public UserPreference() {}

    public UserPreference(int userPreferenceId, int userId) {
        this.userPreferenceId = userPreferenceId;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserPreference{" +
                "userPreferenceId=" + userPreferenceId +
                ", userId=" + userId +
                '}';
    }
}
