package shp.template;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import shp.template.Database.Common.ClsmUserLogin;
import shp.template.Database.Repo.RepomUserLogin;
import shp.template.CustomView.Utils.ClsTouchImageView;
import com.kalbe.mobiledevknlibs.PickImageAndFile.PickImage;

import java.sql.SQLException;

public class ActivityImageViewer extends AppCompatActivity {

    private ClsTouchImageView imageView;
    private String ZOOM_IMAGE = "zoom image";
    private String ZOOM_IMAGE_INFO ="zoom image info program";
    private String ZOOM_PROFILE = "photo profil";
    ClsmUserLogin dtLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN); 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        imageView = (ClsTouchImageView) findViewById(R.id.img_viewer);

//        Silahkan di modif dibagian ini, code dibawah ini adalah salah satu contohnya

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString(ZOOM_IMAGE)!=null){
                /*tAkuisisiDetailRepo detailRepo = new tAkuisisiDetailRepo(getApplicationContext());
                tAkuisisiDetail data = null;
                try {
                    data = (tAkuisisiDetail) detailRepo.findByDetailId(bundle.getString(ZOOM_IMAGE));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Bitmap bitmap = new PickImage().decodeByteArrayReturnBitmap(data.getTxtImg());
                if (bitmap!=null)
                imageView.setImageBitmap(bitmap);*/
            }else if (bundle.getInt(ZOOM_IMAGE_INFO)!=0){
                /*mFileAttachment attach = null;
                try {
                    attach = (mFileAttachment) new mFileAttachmentRepo(getApplicationContext()).findById(bundle.getInt(ZOOM_IMAGE_INFO));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                byte[] arrayImage = new BLMain().arrayDecryptFile(attach.getBlobFile());
                Bitmap bitmap = new PickImage().decodeByteArrayReturnBitmap(arrayImage);
                if (bitmap!=null)
                imageView.setImageBitmap(bitmap);*/
            }else if (bundle.getString(ZOOM_PROFILE)!=null){
                try {
                    dtLogin = (ClsmUserLogin) new RepomUserLogin(getApplicationContext()).findByTxtId(bundle.getString(ZOOM_PROFILE));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Bitmap bitmap = new PickImage().decodeByteArrayReturnBitmap(dtLogin.getBlobImg());
                if (bitmap!=null)
                    imageView.setImageBitmap(bitmap);
            }

        }
    }

    private static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();

            return bmRotated;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void onBackPressed() {
        Intent parentIntent = NavUtils.getParentActivityIntent(this);
        parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(parentIntent);
        finish();
    }
}
