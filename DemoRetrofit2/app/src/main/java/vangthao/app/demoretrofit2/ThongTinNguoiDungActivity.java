package vangthao.app.demoretrofit2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vangthao.app.demoretrofit2.Retrofit2.APIUtils;
import vangthao.app.demoretrofit2.Retrofit2.DataClient;

public class ThongTinNguoiDungActivity extends AppCompatActivity {

    ImageView imgHinhNguoiDung;
    TextView txtTaiKhoan, txtMatKhau;
    Button btnXoaNguoiDung;
    ArrayList<SinhVien> sinhVienArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_nguoi_dung);

        AnhXa();
        init();
        btnXoaNguoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameFolder = sinhVienArrayList.get(0).getHinhanh();
                nameFolder = nameFolder.substring(nameFolder.lastIndexOf("/"));

                DataClient dataClient = APIUtils.getData();
                Call<String> callback = dataClient.deleteData(sinhVienArrayList.get(0).getId(), nameFolder);
                callback.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String ketQua = response.body();
                        if(ketQua.equals("OK")){
                            Toast.makeText(ThongTinNguoiDungActivity.this, "Xoá người dùng thành công!", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(ThongTinNguoiDungActivity.this, "Lỗi xoá người dùng!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void init() {
        Intent intent = getIntent();
        sinhVienArrayList = intent.getParcelableArrayListExtra("mangsinhvien");

        txtTaiKhoan.setText("Tài khoản: " + sinhVienArrayList.get(0).getTaikhoan());
        txtMatKhau.setText("Mật khẩu:" + sinhVienArrayList.get(0).getPassword());
        Picasso.get().load(sinhVienArrayList.get(0).getHinhanh()).into(imgHinhNguoiDung);
    }

    private void AnhXa() {
        imgHinhNguoiDung = findViewById(R.id.imgViewHinh);
        txtTaiKhoan = findViewById(R.id.txtTaiKhoan);
        txtMatKhau = findViewById(R.id.txtMatKhau);
        btnXoaNguoiDung = findViewById(R.id.btnXoaNguoiDung);
    }
}