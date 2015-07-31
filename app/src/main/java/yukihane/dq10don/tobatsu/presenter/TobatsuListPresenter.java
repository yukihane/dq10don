package yukihane.dq10don.tobatsu.presenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import yukihane.dq10don.account.TobatsuList;
import yukihane.dq10don.base.presenter.BasePresenter;
import yukihane.dq10don.base.presenter.CharacterDto;
import yukihane.dq10don.db.DbHelperFactory;
import yukihane.dq10don.tobatsu.model.TobatsuListService;
import yukihane.dq10don.tobatsu.model.TobatsuListServiceFactory;

/**
 * Created by yuki on 15/07/15.
 */
public class TobatsuListPresenter extends BasePresenter<TobatsuList, TobatsuListService> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TobatsuListPresenter.class);

    public TobatsuListPresenter(BasePresenter.View<TobatsuList> view, TobatsuListServiceFactory serviceFactory,
                                DbHelperFactory dbHFactory, CharacterDto character) {
        super(view, serviceFactory, dbHFactory, character);
    }
}
