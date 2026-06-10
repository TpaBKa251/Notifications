package ru.tpu.hostel.notifications.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.tpu.hostel.notifications.TestData;
import ru.tpu.hostel.notifications.entity.Token;
import ru.tpu.hostel.notifications.repository.util.RepositoryTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
class TokenRepositoryTest {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        tokenRepository.deleteAll();
    }

    private Token buildToken(UUID id, UUID userId, String tokenValue) {
        Token token = new Token();
        token.setId(id);
        token.setUserId(userId);
        token.setToken(tokenValue);
        return token;
    }

    @Test
    void findByUserIdWhenExists() {
        tokenRepository.save(buildToken(TestData.TOKEN_ID, TestData.USER_ID, TestData.DEVICE_TOKEN));

        List<Token> result = tokenRepository.findByUserId(TestData.USER_ID);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getToken()).isEqualTo(TestData.DEVICE_TOKEN);
    }

    @Test
    void findByUserIdWhenNotExists() {
        List<Token> result = tokenRepository.findByUserId(TestData.OTHER_USER_ID);

        assertThat(result).isEmpty();
    }

    @Test
    void findByUserIdReturnsAllUserTokens() {
        tokenRepository.save(buildToken(TestData.TOKEN_ID, TestData.USER_ID, TestData.DEVICE_TOKEN));
        tokenRepository.save(buildToken(UUID.randomUUID(), TestData.USER_ID, TestData.OTHER_DEVICE_TOKEN));

        List<Token> result = tokenRepository.findByUserId(TestData.USER_ID);

        assertThat(result)
                .extracting(Token::getToken)
                .containsExactlyInAnyOrder(TestData.DEVICE_TOKEN, TestData.OTHER_DEVICE_TOKEN);
    }

    @Test
    void deleteByUserIdWithSuccess() {
        tokenRepository.save(buildToken(TestData.TOKEN_ID, TestData.USER_ID, TestData.DEVICE_TOKEN));

        tokenRepository.deleteByUserId(TestData.USER_ID);

        assertThat(tokenRepository.findByUserId(TestData.USER_ID)).isEmpty();
    }

    @Test
    void upsertTokenNativeInsertsNewRow() {
        UUID id = UUID.randomUUID();
        tokenRepository.save(buildToken(id, TestData.USER_ID, TestData.DEVICE_TOKEN));

        tokenRepository.upsertTokenNative(TestData.OTHER_USER_ID, TestData.DEVICE_TOKEN);
        entityManager.flush();
        entityManager.clear();

        List<Token> result = tokenRepository.findByUserId(TestData.OTHER_USER_ID);
        assertThat(result)
                .extracting(Token::getToken)
                .contains(TestData.DEVICE_TOKEN);
    }
}
