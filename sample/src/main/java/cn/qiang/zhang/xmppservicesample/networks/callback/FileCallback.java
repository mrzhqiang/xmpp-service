package cn.qiang.zhang.xmppservicesample.networks.callback;

import android.app.Activity;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;

import cn.qiang.zhang.xmppservicesample.networks.OkHttp;
import cn.qiang.zhang.xmppservicesample.utils.FileUtil;
import okhttp3.Response;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * 文件下载上传回调
 * <p>
 * Created by mrZQ on 2016/11/23.
 */
public abstract class FileCallback extends BaseCallback<File> {

    private static final String TAG = "FileCallback";
    private static final String DOWNLOAD_DIR = File.separator + "download" + File.separator;

    private String fileDir;
    private String fileName;

    protected FileCallback(Activity activity, String fileName) {
        this(activity, null, fileName);
    }

    private FileCallback(Activity activity, String fileDir, String fileName) {
        super(activity);
        if (!TextUtils.isEmpty(fileDir)) {
            this.fileDir = fileDir;
        } else {
            if (FileUtil.checkoutSDCard()) {
                this.fileDir = Environment.getExternalStorageDirectory() + DOWNLOAD_DIR;
            } else {
                // 文件不建议存在根目录下
                this.fileDir = Environment.getRootDirectory() + DOWNLOAD_DIR;
            }
        }
        this.fileName = fileName;
    }

    @Override
    public File convert(Response response) throws Exception {
        if (TextUtils.isEmpty(fileName)) {
            fileName = OkHttp.getNetFileName(response);
        }
        File dir = new File(fileDir);
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                Log.d(TAG, "createError:" + fileDir);
            }
        }
        File file = new File(dir, fileName);
        if (file.exists()) {
            if (file.delete()) {
                Log.d(TAG, "delete:" + fileName);
            }
        }
        Log.d(TAG, "download file:" + file.getAbsolutePath());
        // 拿到网络响应中的缓冲源
        BufferedSource source = response.body().source();
        // 通过file构建一个缓冲池
        BufferedSink bufferedSink = Okio.buffer(Okio.sink(file));
        // 往缓冲池中写入所有的缓冲源
        bufferedSink.writeAll(source);
        bufferedSink.flush();
        response.close();
        return file;
    }

}
