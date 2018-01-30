package com.linkmindpro.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageHelper {

    public static Bitmap getBitmapFromGalleryIntent(Context context, Intent data) {
        if (data != null) {
            try {
                return MediaStore.Images.Media.getBitmap(context.getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static Bitmap getBitmapFromPath(String path) {
        File file = new File(path);
        if (file.exists()) return BitmapFactory.decodeFile(file.getAbsolutePath());

        return null;
    }

    public static byte[] convertImageToByteArray(String imagePath,
                                                 int reqWidth, int reqHeight) {
        byte[] byte_img_data = null;

        try {
            // BitmapFactory.Options options = new BitmapFactory.Options();
            // options.inSampleSize = 4;
            // Bitmap bitmap = BitmapFactory.decodeFile(imagePath,options);
            // Bitmap correctBmp = getCorrectBitmap(bitmap, imagePath);
            Bitmap correctBmp = decodeSampledBitmapFromFile(imagePath,
                    reqWidth, reqHeight);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            correctBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte_img_data = baos.toByteArray();
        } catch (Exception e) {
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
                // img.setImageBitmap(bitmap);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                byte_img_data = baos.toByteArray();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return byte_img_data;
    }

    public static Bitmap decodeSampledBitmapFromFile(String pic_Path,
                                                     int reqWidth, int reqHeight) {
        try {
            File f = new File(pic_Path);
            /*
             * ExifInterface exif = new ExifInterface(f.getPath());
			 *
			 * int orientation =
			 * exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
			 * ExifInterface.ORIENTATION_NORMAL);
			 *
			 * int angle = 0;
			 *
			 * if (orientation == ExifInterface.ORIENTATION_ROTATE_90) { angle =
			 * 90; } else if (orientation ==
			 * ExifInterface.ORIENTATION_ROTATE_180) { angle = 180; } else if
			 * (orientation == ExifInterface.ORIENTATION_ROTATE_270) { angle =
			 * 270; }
			 *
			 * Matrix mat = new Matrix();
			 */
            Matrix mat = getOrientatationFromFile(f.getPath());// postRotate(angle);
            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(pic_Path, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth,
                    reqHeight);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            // return BitmapFactory.decodeFile(pic_Path, options);
            Bitmap bmp = BitmapFactory.decodeStream(new FileInputStream(f),
                    null, options);
            Bitmap correctBmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(),
                    bmp.getHeight(), mat, true);
            return correctBmp;
        } catch (Exception e) {
            return null;
        }
    }

    static Matrix getOrientatationFromFile(String pic_Path) {
        Matrix mat = new Matrix();
        try {

            ExifInterface exif = new ExifInterface(pic_Path);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            int angle = 0;

            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                angle = 90;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                angle = 180;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                angle = 270;
            }

            mat.postRotate(angle);
            return mat;
        } catch (Exception e) {
            return mat;
        }
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
