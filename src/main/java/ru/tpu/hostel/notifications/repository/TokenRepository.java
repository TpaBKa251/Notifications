package ru.tpu.hostel.notifications.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tpu.hostel.notifications.entity.Token;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {

    List<Token> findByUserId(UUID userId);

    void deleteByUserId(UUID userId);

    @Modifying
    @Transactional
    @Query(value = """
            INSERT INTO notifications.tokens (user_id, token)
            VALUES (:userId, :token)
            ON CONFLICT (token) DO UPDATE SET user_id = EXCLUDED.user_id
            """,
            nativeQuery = true
    )
    void upsertTokenNative(@Param("userId") UUID userId, @Param("token") String token);

    default void upsertToken(Token token) {
        upsertTokenNative(token.getUserId(), token.getToken());
    }

}
