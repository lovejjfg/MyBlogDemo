package com.lovejjfg.blogdemo.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.lovejjfg.blogdemo.utils.crop.Crop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 相机
 */
public class PhotoUtils implements Parcelable {
    private static final String TAG = PhotoUtils.class.getSimpleName();
    public static final int ALUBUM_REQUEST_CODE = 1;
    public static final int CAMERAL_REQUEST_CODE = 2;
    public static final int ALUBUM_FRONT_ID_CODE = 3;
    public static final int CAMERAL_FRONT_ID_CODE = 4;
    public static final int ALUBUM_BEHIND_ID_CODE = 5;
    public static final int CAMERAL_BEHIND_ID_CODE = 6;
    protected static final int DEFAULT_AVATAR_SIZE = 400;
    public String filePath = null;
    private String mFileName = null;
    private Context mContext;

    public PhotoUtils(Context context) {
        this(context, null);
    }

    public PhotoUtils(Context context, String filePath) {

        this.mContext = context;
        if (TextUtils.isEmpty(filePath)) {
            try {
                this.filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
            } catch (Exception e) {
                this.filePath = null;
            }
        } else {
            this.filePath = filePath;
        }

    }

    protected PhotoUtils(Parcel in) {
        filePath = in.readString();
        mFileName = in.readString();
    }

    public static final Creator<PhotoUtils> CREATOR = new Creator<PhotoUtils>() {
        @Override
        public PhotoUtils createFromParcel(Parcel in) {
            return new PhotoUtils(in);
        }

        @Override
        public PhotoUtils[] newArray(int size) {
            return new PhotoUtils[size];
        }
    };

    public void takePhoto() {
        takePhoto(null);
    }

    public void takePhoto(int code) {
        takePhoto(null, code);
    }

    /**
     * 照相
     *
     * @param fileName picture name
     */
    public void takePhoto(String fileName, int code) {
        if (TextUtils.isEmpty(fileName)) {
            mFileName = System.nanoTime() + ".jpg";
        } else {
            mFileName = fileName;
        }
        if (filePath != null) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // 下面这句指定调用相机拍照后的照片存储的路径
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(filePath, mFileName)));
            ((Activity) mContext).startActivityForResult(intent, code);
        } else {
            Log.e(TAG, "takePhoto: ");
        }
    }

    /**
     * 照相
     *
     * @param fileName picture name
     */
    public void takePhoto(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            mFileName = System.nanoTime() + ".jpg";
        } else {
            mFileName = fileName;
        }
        if (filePath != null) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // 下面这句指定调用相机拍照后的照片存储的路径
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(filePath, mFileName)));
            ((Activity) mContext).startActivityForResult(intent, CAMERAL_REQUEST_CODE);
        } else {
            Log.e(TAG, "takePhoto: ");
        }
    }

    /**
     * 从相册中选择图片
     */
    public void selectPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        ((Activity) mContext).startActivityForResult(intent, ALUBUM_REQUEST_CODE);
    }

    /**
     * 从相册中选择图片
     */
    public void selectPicture(int code) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        ((Activity) mContext).startActivityForResult(intent, code);
    }

    /**
     * 获得图片
     *
     * @return File
     */
    public File getPicTureFile() {
        return new File(filePath + File.separator + mFileName);
    }


    /**
     * 获得图片
     *
     * @return File
     */
    public File getPicTureFile(Intent data) {
        String path = "";
        Cursor cursor = mContext.getContentResolver().query(data.getData(), null, null, null, null);
        if (cursor != null) {
            int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            if (cursor.moveToFirst()) {
                path = cursor.getString(index);
                cursor.close();
            }
        }
        return new File(path);
    }


//    public void rotateBitmap(File sourceFile) {
//        String filePath = sourceFile.getPath();
//        File file = sourceFile;
//        Bitmap result = null;
//        Bitmap source = null;
//        int degree = getBitmapDegree(filePath);
//        if (degree != 0) {
//            ImageUtil imageHelper = ImageUtil.getInstance(mContext);
//            try {
//                int size = UIUtil.getWindowWidth(mContext);
//                source = imageHelper.loadImage(filePath, size, size);
//                // 根据旋转角度，生成旋转矩阵
//                Matrix matrix = new Matrix();
//                matrix.postRotate(degree);
//                result = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
//                file = FileUtil.saveBitmap(mContext, result, null, filePath, Bitmap.CompressFormat.JPEG);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (source != null) {
//                    source.recycle();
//                }
//
//                if (result != null) {
//                    result.recycle();
//                }
//            }
//        }
//        crop(file);
//    }

//    public void rotateBitmap(File sourceFile, int x, int y) {
//        String filePath = sourceFile.getPath();
//        File file = sourceFile;
//        Bitmap result = null;
//        Bitmap source = null;
//        int degree = getBitmapDegree(filePath);
//        if (degree != 0) {
//            ImageHelper imageHelper = ImageHelper.getInstance(mContext);
//            try {
//                int size = UIUtil.getWindowWidth(mContext);
//                source = imageHelper.loadImage(filePath, size, size);
//                // 根据旋转角度，生成旋转矩阵
//                Matrix matrix = new Matrix();
//                matrix.postRotate(degree);
//                result = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
//                file = FileUtil.saveBitmap(mContext, result, null, filePath, Bitmap.CompressFormat.JPEG);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (source != null) {
//                    source.recycle();
//                }
//
//                if (result != null) {
//                    result.recycle();
//                }
//            }
//        }
//        crop(file,x,y);
//    }

    /**
     * 获取图片的旋转角度
     *
     * @param path 图片的绝对路径
     * @return 图片的旋转角度
     */
    private int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 剪裁图片
     *
     * @param file 图片file文件
     */
    public void crop(File file) {
//        String parentPath = PhotoUtils.getImageCheDir(mContext);
//        String path = System.nanoTime() + "测试.jpg";
//        String path = System.nanoTime() + ".jpg";
        String parentPath = PhotoUtils.getImageCheDir(mContext);
        String path = parentPath + System.nanoTime() + ".jpg";
//        String path =  "jj.jpg";
//        Uri outputUri = Uri.fromFile(new File(path));
        Uri outputUri = Uri.fromFile(new File(path));
        new Crop(Uri.fromFile(file)).output(outputUri).asSquare().withMaxSize(DEFAULT_AVATAR_SIZE, 240).start((Activity) mContext);
    }

    /**
     * 剪裁图片
     *
     * @param file 图片file文件
     */
    public void crop(File file, int x, int y) {
//        String parentPath = PhotoUtils.getImageCheDir(mContext);
        String path = System.nanoTime() + ".jpg";
//        Uri outputUri = Uri.fromFile(new File(path));
        Uri outputUri = Uri.fromFile(new File(mContext.getCacheDir(), path));
        new Crop(Uri.fromFile(file)).output(outputUri).withAspect(x, y).withMaxSize(DEFAULT_AVATAR_SIZE, 240).start((Activity) mContext);
    }

    /**
     * 获得裁剪后的BitMap
     */
    @SuppressWarnings("all")
    public Bitmap getCropBitMap(Intent data) {
        Uri uri = Crop.getOutput(data);
        return BitmapFactory.decodeFile(uri.getPath());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(filePath);
        dest.writeString(mFileName);
    }

    public static String getImageCheDir(Context ctx) {
        String filePath = getCacheDir(ctx);
        return String.format("%s%simg%s", filePath, File.separator, File.separator);
    }

    public static String getCacheDir(Context context) {
        File extCache = getExternalCacheDir(context);
        boolean isSdcardOk = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !isExternalStorageRemovable();
        return (isSdcardOk && null != extCache) ? extCache.getPath() : context.getCacheDir().getPath();
    }

    /**
     * Check if OS version has built-in external cache dir method.
     */
    public static boolean hasExternalCacheDir() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    /**
     * Get the external app cache directory.
     *
     * @param context The context to use
     * @return The external cache dir
     */
    @SuppressLint("NewApi")
    public static File getExternalCacheDir(Context context) {
        if (hasExternalCacheDir()) {
            return context.getExternalCacheDir();
        }

        // Before Froyo we need to construct the external cache dir ourselves
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }

    /**
     * Check if external storage is built-in or removable.
     *
     * @return True if external storage is removable (like an SD card), false otherwise.
     */
    @SuppressLint("NewApi")
    public static boolean isExternalStorageRemovable() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD || Environment.isExternalStorageRemovable();
    }

    // 图片转为文件
    public static boolean saveBitmap2file(Bitmap bmp, String filename) {
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        int quality = 100;
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return bmp.compress(format, quality, stream);
    }

}
