package yukihane.dq10don.communication_game;

import retrofit.http.POST;
import yukihane.dq10don.communication_game.dto.farm.info.GameInfoDto;
import yukihane.dq10don.communication_game.dto.game.MowGrassDto;
import yukihane.dq10don.communication_game.dto.game.OpenAllTresureBoxDto;
import yukihane.dq10don.communication_game.dto.time.ServerTimeDto;
import yukihane.dq10don.communication_game.dto.login.GameLoginDto;

/**
 * Created by yuki on 2015/08/15.
 */
public interface GameService {

    @POST("/member/login?data={force:true}&")
    GameLoginDto login();

    @POST("/farm/getinfo")
    GameInfoDto getInfo();

    @POST("/common/getservertime")
    ServerTimeDto getServerTime();

    @POST("/farm/openalltresurebox")
    OpenAllTresureBoxDto openAllTresureBox();

    @POST("/farm/mowgrass")
    MowGrassDto mowGrass();
}
