package cn.qiang.zhang.xmppservicesample.utils;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.qiang.zhang.xmppservicesample.utils.logger.Log;


/**
 * 文件工具类
 * <p>
 * 对文件进行创建、拷贝、删除等操作，拥有判断SD卡是否存在等公共方法。
 * <p>
 * Created by mrZQ on 2016/10/14.
 */
public final class FileUtil {
    private static final String TAG = "FileUtil";

    private FileUtil() {
        //no instance
    }

    /**
     * 检测SD卡是否正常使用
     * @return true 表示SD卡存在且有读写权限；false 表示SD卡不存在或无法读写
     */
    public static boolean checkoutSDCard() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /**
     * 优先选择外置SD卡缓存目录，如果SD卡不存在，则获取系统存储空间的缓存目录
     * <p>
     * 通常而言，不会返回null，除非API方法错误，或者某些定制系统出错
     * @return 缓存目录路径
     */
    public static File getCacheDir(Application application, String appointPath) {
        File dir = null;
        if (checkoutSDCard()) {
            dir = application.getExternalCacheDir();
        }
        if (dir == null) {
            dir = application.getCacheDir();
        }
        if (dir != null) {
            dir = new File(dir.toString(), appointPath);
        }
        return dir;
    }

//    public static boolean isCopyLua() {
//        File file = new File(Environment.getExternalStorageDirectory() + File.separator + MapManager.LUA_NAME);
//        Log.d("lua: " + file.getAbsolutePath() + "," + file.exists());
//        return file.exists();
//    }

    public static void delete(File dir) {
        try {
            if (dir != null && dir.exists()) {
                String dirString = dir.getName();
                if (dir.isDirectory()) {
                    String[] dirList = dir.list();
                    printDirFileList(dirString, dirList);
                    for (String dirOrFile : dirList) {
                        delete(new File(dirOrFile));
                    }
                    if (dir.exists() && dir.delete()) {
                        Log.d(TAG, "delete dir " + dirString + " successful");
                    }
                } else {
                    if (dir.delete()) {
                        Log.d(TAG, "delete file " + dirString + " successful");
                    }
                }
            }
        } catch (RuntimeException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public static void removeDir(Context context, String target) {
        try {
            ThrowUtil.nonNull(target, "target cannot is Empty");
            ThrowUtil.nonNull(context, "context cannot is null");
            String dir = Environment.getExternalStorageDirectory().getPath() +
                    File.separator + target;
            File dieFile = new File(dir);
            delete(dieFile);
        } catch (RuntimeException e) {
            Log.e(TAG, e.getMessage());
        }

    }

    /**
     * 将assets下指定目录中所有文件copy到SDCard的指定目录中
     * @param context 所在页面上下文
     * @param toDir   SD卡的目录名
     * @param fromDir Asset目录下的目录名
     */
    public static void copyDirToSDCardFromAsserts(Context context, String toDir, String fromDir) {
        try {
            ThrowUtil.nonNull(fromDir, "driName cannot is Empty");
            ThrowUtil.nonNull(context, "context cannot is null");
            AssetManager assetManager = context.getAssets();
            String[] fileList = assetManager.list(fromDir);
            // 如果SD卡目录存在，并且Asset中有目录或文件存在
            if (fileList != null && fileList.length > 0) {
                printDirFileList(fromDir, fileList); // 在日志中显示dirName2中的所有文件名
                // 得到SD卡中需要使用或创建的目录
                String dir = Environment.getExternalStorageDirectory().getPath() +
                        File.separator + toDir;
                // 创建文件夹
                createFilesDir(new File(dir));
                // 创建一次读取的文件大小
                byte[] buffer = new byte[1024];
//                MainActivity mainActivity = null;
//                if (context instanceof MainActivity) {
//                    mainActivity = (MainActivity) context;
//                }
                // 遍历文件列表拷贝所有文件
                for (String fromFile : fileList) {
//                    if (mainActivity != null) {
//                        // 这样是最省事的，无需判断文件大小和已传输大小，每一次都算作5个进度
//                        int currentProgress = mainActivity.getCurrentProgress();
//                        // 到80的进度，就不需要再增加了，最大100，超出可能有未知的异常，剩余的进度留给加载地图
//                        if (currentProgress < 80) {
//                            mainActivity.sendProgress(currentProgress + 5);
//                        }
//                    }
                    // 尝试根据来源在指定目录下创建文件
                    File file = createNewFile(new File(dir, fromFile));
                    // 创建失败，跳过此次拷贝
                    if (file == null) {
                        Log.e("createNewFile failed, this name: " + fromFile);
                        continue;
                    }
                    // 打开文件输出流
                    FileOutputStream outputStream = new FileOutputStream(file);
                    // 得到文件输入流
                    InputStream inputStream = assetManager.open(fromDir + File.separator +
                                                                        fromFile);
                    int len;
                    // 读取整个文件输出到流中
                    while ((len = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, len);
                    }
                    // 清空流使拷贝立即完成
                    outputStream.flush();
                    // 关闭输入流
                    inputStream.close();
                    // 关闭输出流
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

    }

    /**
     * 输出指定名字和列表中的所有事物
     * @param dirName  指定名字
     * @param fileList 列表
     */
    private static void printDirFileList(String dirName, String[] fileList) {
        Log.d(dirName + "中有以下内容：");
        int i = 0;
        for (String str : fileList) {
            i++;
            Log.d("[" + i + "]: " + str);
        }
    }

    /**
     * 根据传入的File对象创建目录
     * @param file 需要创建的目录对象
     * @return 已创建好的目录
     */
    public synchronized static File createFilesDir(File file) {
        if (!file.isDirectory()) {
            if (!file.mkdirs()) {
                if (file.isDirectory()) {
                    // spurious failure; probably racing with another process for this app
                    return file;
                }
                Log.w("Unable to createError files sub dir " + file.getPath());
                return null;
            }
        }
        return file;
    }

    /**
     * 根据传入的File对象创建新文件
     * @param file 需要创建的文件对象
     * @return 已创建好的文件
     * @throws IOException 文件流异常
     */
    public synchronized static File createNewFile(File file) throws IOException {
        if (!file.exists()) {
            if (!file.createNewFile()) {
                if (file.exists()) {
                    // spurious failure; probably racing with another process for this app
                    return file;
                }
                Log.w("Unable to createError files " + file.getPath());
                return null;
            }
        }
        return file;
    }
}
