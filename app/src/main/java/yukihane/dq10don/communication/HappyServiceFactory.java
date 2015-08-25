package yukihane.dq10don.communication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.JacksonConverter;

import static yukihane.dq10don.communication.Constants.HAPPYSERVICE_ENDPOINT;
import static yukihane.dq10don.communication.Constants.HEADER_SESSION;

/**
 * Created by yuki on 15/07/07.
 */
public class HappyServiceFactory {


    private HappyServiceFactory() {
    }

    public static HappyService getService(final String sessionId) {
        if (sessionId == null || sessionId.isEmpty()) {
            throw new IllegalArgumentException("Need session ID.");
        }

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(HAPPYSERVICE_ENDPOINT)
                .setConverter(new JacksonConverter(new ObjectMapper()))
                .setClient(new OkClient(new OkHttpClient()))
                .setRequestInterceptor(request -> request.addHeader(HEADER_SESSION, sessionId));

        RestAdapter adapter = builder.build();

        HappyService service = adapter.create(HappyService.class);
        return new HappyServiceWrapper(service);
    }
}
