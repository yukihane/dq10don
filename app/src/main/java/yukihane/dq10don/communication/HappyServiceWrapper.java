package yukihane.dq10don.communication;

import retrofit.RetrofitError;
import retrofit.http.Path;
import yukihane.dq10don.communication.dto.CharaSelectDto;
import yukihane.dq10don.communication.dto.tobatsu.TobatsuDto;
import yukihane.dq10don.exception.HappyServiceException;

/**
 * Created by yuki on 15/07/22.
 */
public class HappyServiceWrapper implements HappyService {

    private final HappyService service;

    HappyServiceWrapper(HappyService service) {
        this.service = service;
    }

    @Override
    public CharaSelectDto characterSelect(@Path("webPcNo") long webPcNo) throws HappyServiceException {
        try {
            CharaSelectDto res = service.characterSelect(webPcNo);
            if (res.getResultCode() != 0) {
                throw new HappyServiceException(res.getResultCode());
            }
            return res;
        } catch (RetrofitError e) {
            throw new HappyServiceException("characterSelect error webPcNo: " + webPcNo, e);
        }
    }

    @Override
    public TobatsuDto getTobatsuList() throws HappyServiceException {
        try {
            TobatsuDto res = service.getTobatsuList();
            if (res.getResultCode() != 0) {
                throw new HappyServiceException(res.getResultCode());
            }
            return res;
        } catch (RetrofitError e) {
            throw new HappyServiceException("getTobatsuList error", e);
        }
    }
}
