package yukihane.dq10don.communication;

import retrofit.http.GET;
import retrofit.http.Path;
import yukihane.dq10don.communication.dto.CharaSelectDto;
import yukihane.dq10don.communication.dto.tobatsu.TobatsuDto;
import yukihane.dq10don.exception.HappyServiceException;

/**
 * Created by yuki on 15/07/07.
 */
public interface HappyService {
    @GET("/login/characterselect/{webPcNo}/")
    CharaSelectDto characterSelect(@Path("webPcNo") long webPcNo) throws HappyServiceException;

    @GET("/tobatsu/tobatsulist")
    TobatsuDto getTobatsuList() throws HappyServiceException;
}
