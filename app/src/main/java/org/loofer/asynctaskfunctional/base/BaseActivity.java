package org.loofer.asynctaskfunctional.base;

import android.support.v7.app.AppCompatActivity;

import org.loofer.asynctaskfunctional.callback.IDataCallBack;
import org.loofer.asynctaskfunctional.util.AsyncTaskUtils;

/**
 * ============================================================
 * 版权： xx有限公司 版权所有（c）2016
 * <p>
 * 作者：Loofer
 * 版本：1.0
 * 创建日期 ：2016/10/31 21:53.
 * 描述：
 * <p>
 * 注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * Modified Date Modify Content:
 * <p>
 * ==========================================================
 */

public class BaseActivity extends AppCompatActivity {

    public <T> void doAsync(IDataCallBack<T> callBack) {
        AsyncTaskUtils.doAsync(callBack);
    }

}
