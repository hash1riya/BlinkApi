package com.blink.BlinkApi.room;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface MembershipRepository extends MongoRepository<Membership, String> {
    @Query("{ 'roomId': '?0', 'userId': '?1' }")
    Optional<Membership> findByRoomUserId(String roomId, String userId);

    @Query("{ 'roomId': '?0' }")
    List<Membership> findByRoomId(String roomId);

    @Query("{ 'userId':  '?0' }")
    List<Membership> findByUserId(String userId);

    @Query("{ 'roomId': '?0', 'userId': '?1' }")
    @Update("{ '$set' : { 'role': '?2' } }")
    void updateRole(String roomId, String userId, MemberRole role);
}
