package yukihane.dq10don.communication_game;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import yukihane.dq10don.communication_game.dto.farm.info.GameInfoDto;
import yukihane.dq10don.communication_game.dto.farm.mowgrass.MowGrassDto;
import yukihane.dq10don.communication_game.dto.farm.openalltresurebox.OpenAllTresureBoxDto;
import yukihane.dq10don.communication_game.dto.login.GameLoginDto;
import yukihane.dq10don.communication_game.dto.time.ServerTimeDto;
import yukihane.dq10don.exception.HappyServiceException;

/**
 * Created by yuki on 2015/08/15.
 */
public interface RetrofitGameService {

    @FormUrlEncoded
    @POST("/member/login")
    GameLoginDto login(@Field("data") String data) throws HappyServiceException;

    @FormUrlEncoded
    @POST("/farm/getinfo")
    GameInfoDto getInfo(@Field("data") String data) throws HappyServiceException;

    @FormUrlEncoded
    @POST("/common/getservertime")
    ServerTimeDto getServerTime(@Field("data") String data) throws HappyServiceException;


    @FormUrlEncoded
    @POST("/farm/mowgrass")
    MowGrassDto mowGrass(@Field("data") String data) throws HappyServiceException;

    @FormUrlEncoded
    @POST("/farm/openalltresurebox")
    OpenAllTresureBoxDto openAllTresureBox(@Field("data") String data) throws HappyServiceException;
}
