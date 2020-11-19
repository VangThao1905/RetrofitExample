package vangthao.app.demoretrofit2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vangthao.app.demoretrofit2.Retrofit2.APIUtils;
import vangthao.app.demoretrofit2.Retrofit2.DataClient;

public class DangKyActivity extends AppCompatActivity {

    ImageView imgViewHinhDangKy;
    EditText edtTaiKhoan, edtMatKhau;
    Button btnHuy, btnXacNhan;
    int REQUEST_CODE_IMAGE = 1;
    String realPath = "";
    String taiKhoan, matKhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        AnhXa();

        imgViewHinhDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_IMAGE);
            }
        });

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taiKhoan = edtTaiKhoan.getText().toString();
                matKhau = edtMatKhau.getText().toString();

                if (taiKhoan.length() > 0 && matKhau.length() > 0) {
                    File file = new File(realPath);
                    String file_Path = file.getAbsolutePath();
                    String[] mangTenFile = file_Path.split("\\.");

                    file_Path = mangTenFile[0] + System.currentTimeMillis() + "." + mangTenFile[1];

                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file_Path, requestBody);

                    DataClient dataClient = APIUtils.getData();
                    retrofit2.Call<String> callback = dataClient.uploadPhoto(body);

                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response != null) {
                                String message = response.body();

                                if (message.length() > 0) {
                                    DataClient insertData = APIUtils.getData();
                                    Call<String> callback = insertData.insertData(taiKhoan, matKhau, APIUtils.BaseUrl + "image/" + message);

                                    callback.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            String result = response.body();

                                            if(result.equals("Success!")){
                                                Toast.makeText(DangKyActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {

                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.d("AAA", t.getMessage());
                            Toast.makeText(DangKyActivity.this, "That bai", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(DangKyActivity.this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public String getRealPathFromURI(Uri contentUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            realPath = getRealPathFromURI(uri);
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgViewHinhDangKy.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void AnhXa() {
        imgViewHinhDangKy = findViewById(R.id.imgViewHinhDangKy);
        edtTaiKhoan = findViewById(R.id.edtTaiKhoanDangky);
        edtMatKhau = findViewById(R.id.edtMatKhauDangky);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        btnHuy = findViewById(R.id.btnHuy);

    }
}