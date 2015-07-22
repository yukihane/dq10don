package yukihane.dq10don.communication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.JacksonConverter;

/**
 * Created by yuki on 15/07/07.
 */
public class HappyServiceFactory {

    private static final String ENDPOINT = "https://happy.dqx.jp/capi";
    private static final String HEADER_SESSION = "X-Smile-3DS-SESSIONID";

    private HappyServiceFactory() {
    }

    public static HappyService getService(final String sessionId) {
        if (sessionId == null || sessionId.isEmpty()) {
            throw new IllegalArgumentException("Need session ID.");
        }

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .setConverter(new JacksonConverter(new ObjectMapper()))
                .setClient(new OkClient(new OkHttpClient()))
                .setRequestInterceptor(request -> request.addHeader(HEADER_SESSION, sessionId));

        RestAdapter adapter = builder.build();

        HappyService service = adapter.create(HappyService.class);
        return new HappyServiceWrapper(service);
    }
}
