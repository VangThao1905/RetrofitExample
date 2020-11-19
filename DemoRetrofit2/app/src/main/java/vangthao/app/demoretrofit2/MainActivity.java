package vangthao.app.demoretrofit2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vangthao.app.demoretrofit2.Retrofit2.APIUtils;
import vangthao.app.demoretrofit2.Retrofit2.DataClient;

public class MainActivity extends AppCompatActivity {

    Button btnDangKy, btnDangNhap;
    EditText edtTaiKhoan, edtMatKhau;
    String taiKhoan, matKhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DangKyActivity.class);
                startActivity(intent);
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taiKhoan = edtTaiKhoan.getText().toString();
                matKhau = edtMatKhau.getText().toString();

                if (taiKhoan.length() > 0 && matKhau.length() > 0) {
                    DataClient dataClient = APIUtils.getData();
                    Call<List<SinhVien>> callback = dataClient.loginData(taiKhoan, matKhau);

                    callback.enqueue(new Callback<List<SinhVien>>() {
                        @Override
                        public void onResponse(Call<List<SinhVien>> call, Response<List<SinhVien>> response) {
                            ArrayList<SinhVien> mangSinhVien = (ArrayList<SinhVien>) response.body();
                            if (mangSinhVien.size() > 0) {
                                Intent intent = new Intent(MainActivity.this,ThongTinNguoiDungActivity.class);
                                intent.putExtra("mangsinhvien",mangSinhVien);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<SinhVien>> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "Tài khoản không tồn tại!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void AnhXa() {
        btnDangKy = findViewById(R.id.btnDangKy);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        edtTaiKhoan = findViewById(R.id.edtTaiKhoan);
        edtMatKhau = findViewById(R.id.edtMatKhau);
    }
}