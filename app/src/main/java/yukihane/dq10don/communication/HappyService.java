package yukihane.dq10don.communication;

import retrofit.http.GET;
import yukihane.dq10don.account.Tobatsu;

/**
 * Created by yuki on 15/07/07.
 */
public interface HappyService {
    @GET("/tobatsu/tobatsulist")
    Tobatsu getTobatsu();
}
