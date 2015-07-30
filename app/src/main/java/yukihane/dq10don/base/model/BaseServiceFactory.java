package yukihane.dq10don.base.model;

import yukihane.dq10don.db.DbHelper;

/**
 * サービスへ表示対象を要求し, キャラクターごとに結果をキャラクターごとに表示する機能のサービスファクトリのベース.
 *
 * @param <T> 表示対象とするデータの型.
 * @param <S> 表示対象を要求するサービスの型.
 */
public interface BaseServiceFactory<T, S extends BaseService<T>> {
    S getService(DbHelper dbHelper);
}
