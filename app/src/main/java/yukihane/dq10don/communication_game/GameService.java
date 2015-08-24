package yukihane.dq10don.communication_game;

import yukihane.dq10don.communication_game.dto.farm.info.GameInfoDto;
import yukihane.dq10don.communication_game.dto.login.GameLoginDto;
import yukihane.dq10don.communication_game.dto.time.ServerTimeDto;
import yukihane.dq10don.exception.HappyServiceException;

public interface GameService {

    GameLoginDto login() throws HappyServiceException;

    GameInfoDto getInfo() throws HappyServiceException;

    ServerTimeDto getServerTime() throws HappyServiceException;

}
