package yukihane.dq10don.communication_game;

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

    @POST("/member/login")
    GameLoginDto login() throws HappyServiceException;

    @POST("/farm/getinfo")
    GameInfoDto getInfo() throws HappyServiceException;

    @POST("/common/getservertime")
    ServerTimeDto getServerTime() throws HappyServiceException;

    // TODO 未実装
//    @POST("/farm/openalltresurebox")
//    OpenAllTresureBoxDto openAllTresureBox() throws HappyServiceException;
//
//    @POST("/farm/mowgrass")
//    MowGrassDto mowGrass() throws HappyServiceException;
}
