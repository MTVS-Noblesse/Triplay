package com.noblesse.backend.follow;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class FollowerId {

    @Column(name="FOLLOWER_ID")
    private Long followerId;

    protected FollowerId() {}

    public FollowerId(Long followerId) {
        this.followerId = followerId;
    }

    public Long getFollowerId() {
        return followerId;
    }

    @Override
    public String toString() {
        return "FollowerId{" +
                "followerId=" + followerId +
                '}';
    }
}