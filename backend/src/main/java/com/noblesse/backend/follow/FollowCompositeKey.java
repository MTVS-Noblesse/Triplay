package com.noblesse.backend.follow;

import jakarta.persistence.*;

@Embeddable
public class FollowCompositeKey {

    @Embedded
    private FollowerId followerId;

    @Embedded
    private FolloweeId followeeId;

    protected FollowCompositeKey() {}

    public FollowCompositeKey(FollowerId followerId, FolloweeId followeeId) {
        this.followerId = followerId;
        this.followeeId = followeeId;
    }

    public FollowerId getFollowerId() {
        return followerId;
    }

    public FolloweeId getFolloweeId() {
        return followeeId;
    }

    @Override
    public String toString() {
        return "FollowCompositeKey{" +
                "followerId=" + followerId +
                ", followeeId=" + followeeId +
                '}';
    }
}