package org.loofer.asynctaskfunctional.util;

import android.os.AsyncTask;

import org.loofer.asynctaskfunctional.callback.IDataCallBack;

/**
 * ============================================================
 * 版权： xx有限公司 版权所有（c）2016
 * <p>
 * 作者：Loofer
 * 版本：1.0
 * 创建日期 ：2016/10/31 21:54.
 * 描述：
 * <p>
 * 注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * Modified Date Modify Content:
 * <p>
 * ==========================================================
 */

public class AsyncTaskUtils {
    public static <T> void doAsync(final IDataCallBack<T> callBack) {
        new AsyncTask<Void, Void, T>() {

            protected void onPreExecute() {
                callBack.onTaskBefore();
            }

            @Override
            protected T doInBackground(Void... params) {
                // TODO
                return callBack.onTasking(params);
            }

            protected void onPostExecute(T result) {
                callBack.onTaskAfter(result);
            }

        }.execute();
    }

}
