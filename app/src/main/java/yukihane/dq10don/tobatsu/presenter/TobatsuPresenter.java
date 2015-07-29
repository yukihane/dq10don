package yukihane.dq10don.tobatsu.presenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import yukihane.dq10don.account.TobatsuList;
import yukihane.dq10don.base.presenter.BasePresenter;
import yukihane.dq10don.base.presenter.CharacterDto;
import yukihane.dq10don.db.DbHelper;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.tobatsu.model.TobatsuService;
import yukihane.dq10don.tobatsu.model.TobatsuServiceFactory;

/**
 * Created by yuki on 15/07/15.
 */
public class TobatsuPresenter extends BasePresenter<TobatsuList, TobatsuService> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TobatsuPresenter.class);

    public TobatsuPresenter(BasePresenter.View<TobatsuList> view, TobatsuServiceFactory serviceFactory,
                            DbHelperFactory dbHFactory, CharacterDto character) {
        super(view, serviceFactory, dbHFactory, character);
    }
}
