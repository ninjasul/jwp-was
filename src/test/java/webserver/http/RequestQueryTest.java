package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class RequestQueryTest {

    @DisplayName("빈값의 쿼리스트링을 생성하면 빈값이다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
            " "
    })
    void empty(final String rawQueryString) {
        // when
        final RequestQuery requestQuery = RequestQuery.of(rawQueryString);

        // then
        assertThat(requestQuery).isEqualTo(RequestQuery.EMPTY);
    }

    @DisplayName("쿼리스트링의 값을 가져온다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "a", "b", "c"
    })
    void getString(final String key) {
        // given
        final RequestQuery requestQuery = RequestQuery.of("a=a&b=b&c=c");

        // when
        final String value = requestQuery.getString(key);

        // then
        assertThat(value).isEqualTo(key);
    }

    @DisplayName("쿼리스트링의 값이 없으면 null을 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "a", "b", "c"
    })
    void notFound(final String key) {
        // given
        final RequestQuery requestQuery = RequestQuery.of("a=a&b=b&c=c");
        final String padding = "!!";

        // when
        final String value = requestQuery.getString(key + padding);

        // then
        assertThat(value).isNull();
    }
}