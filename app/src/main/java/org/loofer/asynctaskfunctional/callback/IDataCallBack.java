package org.loofer.asynctaskfunctional.callback;

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

public interface IDataCallBack<T> {
    /**任务执行之前*/
    void onTaskBefore();

    /**任务执行中...*/
    T onTasking(Void... params);

    /**任务执行之后*/
    void onTaskAfter(T result);

}
