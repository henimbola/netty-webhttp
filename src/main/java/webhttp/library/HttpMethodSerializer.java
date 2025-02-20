package webhttp.library;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.netty.handler.codec.http.HttpMethod;

import java.io.IOException;

public class HttpMethodSerializer extends StdSerializer<HttpMethod> {

    public HttpMethodSerializer() {
        this(null);
    }

    public HttpMethodSerializer(Class<HttpMethod> t) {
        super(t);
    }

    @Override
    public void serialize(HttpMethod value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.name());
    }
}