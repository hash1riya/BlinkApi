package com.blink.BlinkApi.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface FriendshipRepository extends MongoRepository<Friendship, String> {
    @Query("{ '$or': [ { 'requesterId': '?0' }, { 'receiverId': '?0' } ] }")
    List<Friendship> findAllRequestsPerUser(String userId);

    @Query("{ '$or': [ { 'requesterId': '?0' }, { 'receiverId': '?0' } ], 'status': 'ACCEPTED' }")
    List<Friendship> findAllAcceptedFriendships(String userId);

    @Query("{ 'receiverId': '?0', 'status': 'PENDING' }")
    List<Friendship> findAllPendingRequestsReceived(String userId);

    @Query("{ 'requesterId': '?0', 'status': 'PENDING' }")
    List<Friendship> findAllPendingRequestsSent(String userId);

    @Query("{ '$or': [ { 'requesterId': '?0', 'receiverId': '?1' }, { 'receiverId': '?0', 'requesterId': '?1' } ] }")
    Optional<Friendship> findFriendshipBetween(String userA, String userB);

    @Query("{ '$or': [ { 'requesterId': '?0', 'receiverId': '?1' }, { 'receiverId': '?0', 'requesterId': '?1' } ] }")
    @Update("{ '$set' : { 'status': '?2' } }")
    void updateStatus(String userA, String userB, FriendshipStatus status);

}
