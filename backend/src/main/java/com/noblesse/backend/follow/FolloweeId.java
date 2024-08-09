package com.noblesse.backend.follow;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class FolloweeId {

    @Column(name="FOLLOWEE_ID")
    private Long followeeId;

    protected FolloweeId() {}

    public FolloweeId(Long followeeId) {
        this.followeeId = followeeId;
    }

    public Long getFolloweeId() {
        return followeeId;
    }

    @Override
    public String toString() {
        return "FolloweeId{" +
                "followeeId=" + followeeId +
                '}';
    }
}