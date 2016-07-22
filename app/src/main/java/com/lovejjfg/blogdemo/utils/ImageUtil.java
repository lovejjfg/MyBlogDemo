package com.lovejjfg.blogdemo.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 
 * 基础图片工具类
 * 
 * 
 */
public class ImageUtil {
    private Context mContext;
    private static ImageUtil _instance        = null;
    public static int          COMPRESS_QUALITY = 70;  // 图片质量

    public synchronized static ImageUtil getInstance(Context context) {

        if (_instance == null) {
            _instance = new ImageUtil(context.getApplicationContext());
        }
        return _instance;
    }

    private ImageUtil(Context context) {
        mContext = context;
    }

//
//    /**
//     * 指定最大宽度和最大高度下载入图片
//     * @param filePathOrUrl
//     * @param maxWidth
//     * @param maxHeight
//     * @return
//     * @throws ClientProtocolException
//     * @throws IOException
//     * @throws HttpRequestException
//     */
//    public Bitmap loadImageWithBounds(String filePathOrUrl, int maxWidth, int maxHeight) throws ClientProtocolException, IOException, HttpRequestException {
//        Bitmap ret = null;
//        if (filePathOrUrl.startsWith("http")) {
//
//            // 是否有缓存
//            String fileName = generateDiskCacheKey(filePathOrUrl, 0, 0, false);
//            File file = null;
//            file = new File(mContext.getCacheDir(), fileName);// 保存文件
//
//            if (!file.exists()) {
//                HttpUtil.download(filePathOrUrl, file);
//                ret = decodeFileWithBounds(file.getAbsolutePath(), maxWidth, maxHeight);
//            } else {
//                String imagePath = mContext.getCacheDir() + "/" + fileName;
//                ret = decodeFileWithBounds(imagePath, maxWidth, maxHeight);
//            }
//
//        } else {
//            ret = decodeFileWithBounds(filePathOrUrl, maxWidth, maxHeight);
//        }
//
//        return ret;
//    }

    private Bitmap decodeFileWithBounds(String filePath, int maxWidth, int maxHeight) {
        Bitmap ret = null;
        if (maxWidth == 0 && maxHeight == 0) {// 都不限制？
            ret = BitmapFactory.decodeFile(filePath);
        } else {
            if (maxWidth == 0) {
                maxWidth = -1;
            }
            if (maxHeight == 0) {
                maxHeight = -1;
            }

            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, opts);
            if (opts.outWidth == -1) {// error
                return null;
            }
            int width = opts.outWidth;// 图片宽
            int height = opts.outHeight;// 图片高
            if (maxWidth >= width && maxHeight >= height) {// 略缩图比原图还大？！！
                ret = BitmapFactory.decodeFile(filePath);
            } else {
                // 计算到maxWidth的压缩比
                float inSampleSizeWidthFloat = (float) width / (float) maxWidth;
                int inSampleSizeWidth = Math.round(inSampleSizeWidthFloat);
                // 计算到maxHeight的压缩比
                float inSampleSizeHeightFloat = (float) height / (float) maxHeight;
                int inSampleSizeHeight = Math.round(inSampleSizeHeightFloat);

                int inSampleSize = Math.max(inSampleSizeWidth, inSampleSizeHeight);

                opts.inJustDecodeBounds = false;
                opts.inSampleSize = inSampleSize;
                ret = BitmapFactory.decodeFile(filePath, opts);
            }
        }
        return ret;
    }

//    /**
//     * 加载图片 按比例缩放保证图片宽和高不大于width和height
//     *
//     * @param filePathOrUrl
//     *            网络图片url或者本地路径
//     * @param width
//     *            宽
//     * @param height
//     *            高
//     * @throws IOException
//     * @throws ClientProtocolException
//     */
//    public Bitmap loadImage(String filePathOrUrl, int width, int height) throws ClientProtocolException, IOException,
//            HttpRequestException {
//        return loadImage(filePathOrUrl, width, height, false);
//    }

    final static int Nomal          = 0;
    final static int InSample       = 1;
    final static int InSampleAndCut = 2;


    /**
     * 重要：本方法中使用了sdk level8的特性。使用本方法，需要保证你的app中android:minSdkVersion大于等于8，否则在低版本中会异常 获得按比例的略缩图，图片为指定大小（先缩放再裁剪）
     * 
     * @param filePathOrUrl
     * @param cutWidth
     * @param cutHeight
     * @return
     * @throws IOException
     */
    @TargetApi(8)
    private Bitmap getInSampleAndCutBitmap(String filePathOrUrl, int cutWidth, int cutHeight)
            throws IOException {


        Bitmap ret = null;
//        if (filePathOrUrl.startsWith("http")) {// 线上图片
//
//            if (cutWidth == 0 && cutHeight == 0) {// 都不限制？
//                ret = loadImage(filePathOrUrl);
//            } else {
//                Bitmap bitmap = loadImage(filePathOrUrl);
//                ret = ThumbnailUtils.extractThumbnail(bitmap, cutWidth, cutHeight);
//
//                if (bitmap != null && !bitmap.isRecycled()) {
//                    bitmap.recycle();
//                    bitmap = null;
//                }
//            }
//        } else {// 本地图片

            if (cutWidth == 0 && cutHeight == 0) {// 都不限制？
                ret = BitmapFactory.decodeFile(filePathOrUrl);
            } else {
                Bitmap bitmap = BitmapFactory.decodeFile(filePathOrUrl);
                ret = ThumbnailUtils.extractThumbnail(bitmap, cutWidth, cutHeight);

                if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                    bitmap = null;
                }
            }
//        }

        return ret;
    }

//    /**
//     * 获取网络或本地的图片的略缩图
//     *
//     * @param filePathOrUrl
//     * @param maxWidth
//     * @param maxHeight
//     * @param cachePath
//     *            指定缓存图片的路径 路径结尾不要带"/"
//     * @return
//     * @throws IOException
//     * @throws ClientProtocolException
//     */
//    private Bitmap getInSampleBitmapUseFilePathOrUrl(String filePathOrUrl, int maxWidth, int maxHeight)
//            throws ClientProtocolException, IOException {
//        Bitmap ret = null;
//
//        if (filePathOrUrl.startsWith("http")) {
//            ret = getRemotePicInSampleBitmap(filePathOrUrl, maxWidth, maxHeight);
//        } else {
//            ret = getInSampleBitmap(filePathOrUrl, maxWidth, maxHeight);
//        }
//
//        return ret;
//    }

    // http://stackoverflow.com/questions/2641726/decoding-bitmaps-in-android-with-the-right-size
    /**
     * 取得图片的略缩图<br>
     * <br>
     * 缓存算法：<br>
     * 以字符串maxWidth + maxHeight + filePatch拼接字符串Md5为缓存文件名<br>
     * 缓存在mContext.getCacheDir()目录中,下次取直接可取得缓存图片。<br>
     * 
     * @param filePath
     * @param maxWidth
     *            0表示不限
     * @param maxHeight
     *            0表示不限
     * @return
     * @throws FileNotFoundException
     */
    private Bitmap getInSampleBitmap(String filePath, int maxWidth, int maxHeight) throws FileNotFoundException {

        Bitmap ret = null;

        if (maxWidth == 0 && maxHeight == 0) {// 都不限制？
            ret = BitmapFactory.decodeFile(filePath);
        } else {
            if (maxWidth == 0) {
                maxWidth = -1;
            }
            if (maxHeight == 0) {
                maxHeight = -1;
            }

            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, opts);
            if (opts.outWidth == -1) {// error
                return null;
            }
            int width = opts.outWidth;// 图片宽
            int height = opts.outHeight;// 图片高
            if (maxWidth >= width && maxHeight >= height) {// 略缩图比原图还大？！！
                ret = BitmapFactory.decodeFile(filePath);
            } else {
                // 计算到maxWidth的压缩比
                float inSampleSizeWidthFloat = (float) width / (float) maxWidth;
                int inSampleSizeWidth = Math.round(inSampleSizeWidthFloat);
                // 计算到maxHeight的压缩比
                float inSampleSizeHeightFloat = (float) height / (float) maxHeight;
                int inSampleSizeHeight = Math.round(inSampleSizeHeightFloat);

                int inSampleSize = Math.max(inSampleSizeWidth, inSampleSizeHeight);

                opts.inJustDecodeBounds = false;
                opts.inSampleSize = inSampleSize;
                ret = BitmapFactory.decodeFile(filePath, opts);
            }
        }

        return ret;
    }

//    /**
//     * 取得图片的略缩图
//     *
//     * @param url
//     *            图片地址
//     * @param maxWidth
//     * @param maxHeight
//     * @return
//     * @throws IOException
//     */
//    private Bitmap getRemotePicInSampleBitmap(String url, int maxWidth, int maxHeight) throws
//            IOException {
//
//        // 下载图片
////        Bitmap temp = loadImage(url);
//        Bitmap temp = loadImageWithBounds(url, maxWidth, maxHeight);
//        if (temp != null && !temp.isRecycled()) {
//            temp.recycle();
//        }
//
//        // 缩放下载后的图片
//        return getInSampleBitmap(diskCachedFilePath(url), maxWidth, maxHeight);
//    }

//    /**
//     * 获取已经磁盘缓存图片的路径
//     *
//     * @param url
//     * @return
//     */
//    public String diskCachedFilePath(String url) {
//        return diskCachedFilePath(url, 0, 0);
//    }

//    /**
//     * 获取已经磁盘缓存图片的路径
//     *
//     * @param url
//     * @param width
//     * @param height
//     * @return
//     */
//    public String diskCachedFilePath(String url, int width, int height) {
//        return diskCachedFilePath(url, width, height, false);
//    }

//    /**
//     * 获取已经磁盘缓存图片的路径
//     *
//     * @param width
//     * @param height
//     * @param isNeedCut
//     * @return
//     */
//    public String diskCachedFilePath(String url, int width, int height, boolean isNeedCut) {
//
//        String bitmapFileNameMd5 = generateDiskCacheKey(url, width, height, isNeedCut);
//        if (bitmapFileNameMd5 == null) {
//            return "";
//        }
//
//        File file = new File(mContext.getCacheDir(), bitmapFileNameMd5);
//        if (file.exists() && file.length() > 0) {
//            return file.getAbsolutePath();
//        } else {
//            return "";
//        }
//
//    }

    /**
     * 判断图片是否已经磁盘缓存
     * 
     * @param url
     *            图片的url
     * @return
     */
//    public boolean isDiskCached(String url) {
//        String bitmapFileNameMd5 = generateDiskCacheKey(url, 0, 0, false);
//        if (bitmapFileNameMd5 == null) {
//            return false;
//        }
//
//        File file = new File(mContext.getCacheDir(), bitmapFileNameMd5);
//        if (file.exists() && file.length() > 0) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    /**
     * 将图片写入文件
     * 
     * @param bitmap
     * @param imageType
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    public boolean writeToFile(Bitmap bitmap, CompressFormat imageType, File file) throws FileNotFoundException {
        return writeToFile(bitmap, imageType, -1, file);
    }

    /**
     * 将图片写入文件
     * 
     * @param bitmap
     * @param imageType
     * @param compressquality
     *            压缩质量
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    public boolean writeToFile(Bitmap bitmap, CompressFormat imageType, int compressquality, File file)
            throws FileNotFoundException {
        boolean ret = false;

        if (bitmap == null || file == null) {
            return false;
        }

        FileOutputStream fileOutputStreamTemp = null;
        try {

            fileOutputStreamTemp = new FileOutputStream(file);

            if (imageType == CompressFormat.PNG) {
                // 判断是否png 避免透明背景被弄成黑色
                ret = bitmap.compress(CompressFormat.PNG, 100, fileOutputStreamTemp);
            } else {
                if (compressquality != -1) {
                    ret = bitmap.compress(CompressFormat.JPEG, compressquality, fileOutputStreamTemp);
                } else {
                    ret = bitmap.compress(CompressFormat.JPEG, COMPRESS_QUALITY, fileOutputStreamTemp);
                }

            }
            if (!bitmap.isRecycled()) {// 图片存成文件后，直接回收掉. 防止out of memory
                bitmap.recycle();
            }
        } finally {
            if (fileOutputStreamTemp != null) {
                try {
                    fileOutputStreamTemp.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }

        return ret;
    }

    /**
     * 判断图片类型
     * 
     * @param b
     * @return
     */
    public CompressFormat getImageType(byte[] b) {
        if (b[1] == (byte) 'P' && b[2] == (byte) 'N' && b[3] == (byte) 'G') {
            return CompressFormat.PNG;
        } else if (b[6] == (byte) 'J' && b[7] == (byte) 'F' && b[8] == (byte) 'I' && b[9] == (byte) 'F') {
            return CompressFormat.JPEG;
        } else {
            return CompressFormat.JPEG;
        }
    }

    /**
     * 通过url或文件名来判断图片类型
     * 
     * @param url
     * @return
     */
    public static CompressFormat getImageType(String url) {
        String name = url.substring(url.lastIndexOf(".") + 1);
        if (name.equalsIgnoreCase("png")) {
            return CompressFormat.PNG;
        } else if (name.equalsIgnoreCase("jpg")) {
            return CompressFormat.JPEG;
        } else {
            return CompressFormat.JPEG;
        }
    }

    /**
     * 生成disk缓存的key 算法：key = 参数md5后字符串.filePathOrUrl的后缀名
     * 
     * @param filePathOrUrl
     * @param width
     * @param height
     * @param isNeedCut
     * @return
     */
//    public static String generateDiskCacheKey(String filePathOrUrl, int width, int height, boolean isNeedCut) {
//        String preName = EncryptUtils
//                .Md5(String.format("%s%s%s%s", String.valueOf(filePathOrUrl), width, height, isNeedCut));
//
//        // 扩展名位置(特殊处理动态图片地址：http://t0.gstatic.com/images?q=tbn:ANd9GcT85rK6eFEyTw8PT2IaLHGC3ZmFS5yeaTmQ7Adqi0s0yDnv1yMCuw)
//        // mock数据时url返回不包含"/"时会异常
//        int lastIndex = filePathOrUrl.lastIndexOf("/") > 0 ? filePathOrUrl.lastIndexOf("/") : 0;
//        String fileNameArea = filePathOrUrl.substring(lastIndex, filePathOrUrl.length());
//        int index = fileNameArea.lastIndexOf('.');
//        if (index == -1) {
//            return preName;
//        }
//
//        StringBuilder sbUrlKey = new StringBuilder("");
//        sbUrlKey.append(preName);
//        sbUrlKey.append(fileNameArea.substring(index));
//        return sbUrlKey.toString();
//
//    }

    /**
     * 压缩Bitmap至指定大小
     * 
     * @param bmp
     * @param destWidth
     * @param destHeight
     * @param isSameScaleRate
     *            是否保持一致的压缩比, true:缩放后将大于指定width和height的裁剪掉 false:仅按比例缩放保证图片宽和高不大于width和height
     * @param isRecyle
     * @return
     */
    public static Bitmap getInSampleBitmap(Bitmap bmp, int destWidth, int destHeight, boolean isSameScaleRate,
                                           boolean isRecyle) {
        if (bmp == null) {
            return null;
        }
        final int width = bmp.getWidth();
        final int height = bmp.getHeight();
        // 压缩后的图片比原图大, 则返回原图
        if (width < destWidth && height < destHeight) {
            return bmp;
        }

        if (destWidth == 0) {
            destWidth = -1;
        }

        if (destHeight == 0) {
            destHeight = -1;
        }
        Bitmap b = null;
        if (isSameScaleRate) {
            float widthScale = width * 1.0f / destWidth;
            float heightScale = height * 1.0f / destHeight;
            float scale = widthScale > heightScale ? widthScale : heightScale;
            b = Bitmap.createScaledBitmap(bmp, (int) (width / scale), (int) (height / scale), true);
        } else {
            b = Bitmap.createScaledBitmap(bmp, destWidth, destHeight, true);
        }
        if (isRecyle && !bmp.isRecycled()) {
            bmp.recycle();
            bmp = null;
        }
        return b;
    }

    /**
     * 计算压缩比例大小
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 通过资源得到压缩到指定宽高的图片
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                  int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 通过文件路径得到压缩到指定宽高的图片
     * @param path
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }


    /**
     * 将图片压缩到指定大小
     * @param image
     * @param imageSize 指定的图片大小，单位kb
     * @return
     */
    public ByteArrayOutputStream compressImage(Bitmap image, int imageSize) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        // 大于1M先压百分之五十
        if (baos.toByteArray().length / 1024 > 1024) {
            baos.reset();
            image.compress(CompressFormat.JPEG, 50, baos);
            options = 50;
        }
        int length = baos.toByteArray().length / 1024;
        while ( length > imageSize) {  //循环判断如果压缩后图片是否大于传入的参数,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            length = baos.toByteArray().length / 1024;
        }
        image.recycle();
        image = null;
        return baos;
//        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
//        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
//        return bitmap;
    }

    public BitmapFactory.Options getBitmapWidthHeight(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        return options;
    }
}
