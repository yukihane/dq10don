package yukihane.dq10don.communication;

import retrofit.http.GET;
import retrofit.http.Path;
import yukihane.dq10don.account.Tobatsu;
import yukihane.dq10don.communication.dto.CharaSelectDto;

/**
 * Created by yuki on 15/07/07.
 */
public interface HappyService {
    @GET("/login/characterselect/{webPcNo}/")
    CharaSelectDto characterSelect(@Path("webPcNo") long webPcNo);

    @GET("/tobatsu/tobatsulist")
    Tobatsu getTobatsu();
}
