package com.noblesse.backend.follow;

import jakarta.persistence.*;

@Entity
@Table(name="follow")
public class Follow {
    
    //해킹함 낄낄

    @EmbeddedId
    private FollowCompositeKey followInfo;

    protected Follow() {}

    public Follow(FollowCompositeKey followInfo) {
        this.followInfo = followInfo;
    }

    public FollowCompositeKey getFollowInfo() {
        return followInfo;
    }

    @Override
    public String toString() {
        return "Follow{" +
                "followInfo=" + followInfo +
                '}';
    }
}