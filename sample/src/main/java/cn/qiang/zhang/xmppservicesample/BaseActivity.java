package cn.qiang.zhang.xmppservicesample;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;

import butterknife.ButterKnife;
import cn.qiang.zhang.xmppservicesample.loaders.ILoader;
import cn.qiang.zhang.xmppservicesample.loaders.LoaderImp;
import cn.qiang.zhang.xmppservicesample.networks.OkHttp;
import cn.qiang.zhang.xmppservicesample.utils.logger.Log;
import cn.qiang.zhang.xmppservicesample.utils.permission.Permission;

/**
 * 活动基类
 * <p>
 * Created by mrZQ on 2016/10/12.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    /** 进度对话框 */
    protected ProgressDialog dialog;
    /** 图片加载器 */
    protected ILoader loader;

    ///////////////////////////////////////////////////////////////////////////
    // 抽象方法
    ///////////////////////////////////////////////////////////////////////////

    protected abstract void initData();

    @LayoutRes
    protected abstract int getLayout();

    ///////////////////////////////////////////////////////////////////////////
    // 便捷方法
    ///////////////////////////////////////////////////////////////////////////

    /** 便捷显示Toast方法：短时延 */
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /** 便捷显示Toast方法：长时延 */
    public void showLong(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    /** 显示耗时操作的进度对话框 */
    public void showLoading(String title) {
        // 只有标题不同，是为了说明不同的操作
        if (dialog != null) {
            dialog.setTitle(title);
            if (!dialog.isShowing()) {
                dialog.show();
            }
        }
    }

    /** 隐藏进度对话框 */
    public void hideLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /** 返回消息构造器 */
    public AlertDialog.Builder message(String title, String message) {
        return message(title, message, null, null, null);
    }

    public AlertDialog.Builder message(String title, String message,
                                       DialogInterface.OnClickListener positive,
                                       DialogInterface.OnClickListener negative,
                                       DialogInterface.OnClickListener neutral) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message);
        if (positive != null) {
            builder.setPositiveButton("确定", positive);
        }
        if (negative != null) {
            builder.setPositiveButton("取消", negative);
        }
        if (neutral != null) {
            builder.setPositiveButton("应用", neutral);
        }
        return builder;
    }

    /** 返回自定义消息内容构造器 */
    public AlertDialog.Builder message(String title, View view) {
        return new AlertDialog.Builder(this)
                .setTitle(title)
                .setView(view);
    }

    /** 在用户再次拒接授权且勾选不再询问时，将不会弹出系统对话框，这里给出必需权限的声明 */
    public void showNeedPermission(String title, String message) {
        message(title, message,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + getPackageName()));
                        startActivity(intent);
                    }
                },
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        showLong(getString(R.string.app_can_not_run));
                        Log.wtf("user denied permission even after our sincerely statements");
                    }
                },
                null).show();
    }

    /**
     * 请求权限，调用静态方法，发起请求
     * @param requestCode 请求代码，请妥善保管code，回调将找寻它并触发方法
     * @param permissions 权限列表，可以是分组权限也可以是多个不同分组权限
     */
    public void requestPermission(int requestCode, String... permissions) {
        Permission.needPermission(this, requestCode, permissions);
//        Permission.with(this)
//                .addRequestCode(requestCode)
//                .permissions(permissions)
//                .request();
    }

    ///////////////////////////////////////////////////////////////////////////
    // 覆盖回调
    ///////////////////////////////////////////////////////////////////////////

    protected void initView() {
        setContentView(getLayout());
        ButterKnife.bind(this);
    }

    protected void initVariables() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setMessage("请稍等..");
        }
        if (loader == null) {
            loader = new LoaderImp();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // 生命周期
    ///////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, savedInstanceState == null ? this.getLocalClassName() : savedInstanceState.toString());
        initVariables();
        initView();
        initData();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d(TAG, intent == null ? this.getLocalClassName() : intent.toString());
        super.onNewIntent(intent);
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, this.getLocalClassName());
        super.onRestart();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, this.getLocalClassName());
        super.onStart();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, savedInstanceState == null ? this.getLocalClassName() : savedInstanceState.toString());
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        // 页面启动时，恢复图片加载请求
        loader.resumeTag(this);
        Log.d(TAG, this.getLocalClassName());
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.d(TAG, "requestCode " + requestCode + ", permissions " + Arrays.toString(permissions)
                + ", grantResults " + Arrays.toString(grantResults));
        Permission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onPause() {
        // 页面停止时，暂停图片加载请求
        loader.pauseTag(this);
        Log.d(TAG, this.getLocalClassName());
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, outState == null ? this.getLocalClassName() : outState.toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        Log.d(TAG, this.getLocalClassName());
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // 取消页面级别的图片加载请求
        loader.cancelTag(this);
        // 取消页面级别的网络请求
        OkHttp.cancelTag(this);
        Log.d(TAG, this.getLocalClassName());
        super.onDestroy();
    }

}
