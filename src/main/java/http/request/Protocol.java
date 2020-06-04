package http.request;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import lombok.Getter;

import java.util.*;

@Getter
public class Protocol {
    static final String HTTP = "HTTP";
    static final Set<String> ALLOWED_HTTP_VERSION = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("1.0", "1.1")));

    private static final String SPLITTER = "\\/";

    private final String protocol;
    private final String version;

    public static Protocol of(String protocolAndVersion) {
        if (StringUtils.isEmpty(protocolAndVersion)) {
            throw new IllegalArgumentException();
        }

        String[] split = protocolAndVersion.split(SPLITTER);

        if (split.length != 2) {
            throw new IllegalArgumentException();
        }

        return new Protocol(split[0], split[1]);
    }

    public Protocol(String protocol, String version) {
        if (!HTTP.equals(protocol) || !ALLOWED_HTTP_VERSION.contains(version)) {
            throw new IllegalArgumentException();
        }

        this.protocol = protocol;
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Protocol protocol1 = (Protocol) o;
        return Objects.equals(protocol, protocol1.protocol) &&
            Objects.equals(version, protocol1.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol, version);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("protocol은 ").append(protocol).append("\r\n");
        sb.append("version은 ").append(version);

        return sb.toString();
    }
}
