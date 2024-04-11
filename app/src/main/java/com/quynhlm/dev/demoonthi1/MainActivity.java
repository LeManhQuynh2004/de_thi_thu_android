package com.quynhlm.dev.demoonthi1;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.quynhlm.dev.demoonthi1.Adapter.MotorAdapter;
import com.quynhlm.dev.demoonthi1.Api.ApiRequest;
import com.quynhlm.dev.demoonthi1.Model.Motorbike;
import com.quynhlm.dev.demoonthi1.config.RealPathUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ApiRequest apiRequest;
    public static final String TAG = "Main";
    RecyclerView recyclerView;
    MotorAdapter motorAdapter;
    File imageMotor = null;
    Uri imageUri;
    EditText ed_name, ed_price, ed_describe, ed_color;
    ImageView img_upload;
    TextView tv_title_dialog;
    Button bt_add, btn_cancle;
    private List<Motorbike> list = new ArrayList<>();
    FloatingActionButton fab_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiRequest = new ApiRequest();
        recyclerView = findViewById(R.id.RecyclerView_Motor);
        fab_add = findViewById(R.id.fab_add);
        Call<List<Motorbike>> call = apiRequest.getApiService().getAllMotor();
        call.enqueue(new Callback<List<Motorbike>>() {
            @Override
            public void onResponse(Call<List<Motorbike>> call, Response<List<Motorbike>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Call thành công", Toast.LENGTH_SHORT).show();
                    list = response.body();
                    motorAdapter = new MotorAdapter(MainActivity.this, list);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerView.setAdapter(motorAdapter);

                    motorAdapter.setItemClickListener(new OnItemClickListener() {
                        @Override
                        public void UpdateData(int position) {
                            showDialogAdd(1,position);
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Call thất bại", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Motorbike>> call, Throwable t) {
                Log.e(TAG, "onResponse: " + t.getMessage());
            }
        });
        Log.e(TAG, "onCreate: "+list.size());

        fab_add.setOnClickListener(v -> {
            showDialogAdd(0,0);
        });
    }

    private void showDialogAdd(int type,int position) {
        Motorbike motorbike = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialogmotor, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        ed_name = view.findViewById(R.id.ed_name);
        ed_describe = view.findViewById(R.id.ed_describe);
        ed_price = view.findViewById(R.id.ed_price);
        ed_color = view.findViewById(R.id.ed_color);
        img_upload = view.findViewById(R.id.img_upload);
        bt_add = view.findViewById(R.id.bt_add);
        btn_cancle = view.findViewById(R.id.bt_cancle);
        tv_title_dialog = view.findViewById(R.id.tv_title_dialog);

        if(type == 1) {
            tv_title_dialog.setText("Sửa thông tin xe máy");
            ed_name.setText(motorbike.getName_ph32353());
            ed_price.setText(motorbike.getPrice_ph32353() + "");
            ed_describe.setText(motorbike.getDescribe_ph32353());
            ed_color.setText(motorbike.getColor_ph32353());
            Glide.with(MainActivity.this).load(motorbike.getImage_ph32353()).centerCrop().placeholder(R.drawable.ic_launcher_background).into(img_upload);
        }

        img_upload.setOnClickListener(v -> {
            chooseImage();
        });

        bt_add.setOnClickListener(v -> {
            String name = ed_name.getText().toString().trim();
            String price = ed_price.getText().toString().trim();
            String describe = ed_describe.getText().toString().trim();
            String color = ed_color.getText().toString().trim();

            if(name.isEmpty() || price.isEmpty() || describe.isEmpty() || color.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
                return;
            }
            if(Double.parseDouble(price) < 0) {
                Toast.makeText(this, "Giá không được nhỏ hơn không", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, RequestBody> mapRequestBody = new HashMap<>();
            mapRequestBody.put("name_ph32353", getRequestBody(name));
            mapRequestBody.put("price_ph32353", getRequestBody(price));
            mapRequestBody.put("describe_ph32353", getRequestBody(describe));
            mapRequestBody.put("color_ph32353", getRequestBody(color));

            if(imageMotor != null) {
                RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(imageUri)), imageMotor);
                MultipartBody.Part newImage = MultipartBody.Part.createFormData("image_ph32353", imageMotor.getName(), requestFile);

                if(type == 0) {
                    Call<Motorbike> createMotor = apiRequest.getApiService().createMotorbike(mapRequestBody, newImage);
                    createMotor.enqueue(new Callback<Motorbike>() {
                        @Override
                        public void onResponse(Call<Motorbike> call, Response<Motorbike> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                Motorbike motorbike = response.body();
                                list.add(motorbike);
                                alertDialog.dismiss();
                                motorAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(MainActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "onResponse: " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<Motorbike> call, Throwable t) {
                            Log.e(TAG, "onResponse: " + t.getMessage());
                        }
                    });
                }else{
                    Call<Motorbike> updateMotor = apiRequest.getApiService().updateMotorbike(motorbike.get_id(),mapRequestBody,newImage);
                    updateMotor.enqueue(new Callback<Motorbike>() {
                        @Override
                        public void onResponse(Call<Motorbike> call, Response<Motorbike> response) {
                            if(response.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                                Motorbike updateMotor = response.body();
                                list.set(position,updateMotor);
                                motorAdapter.notifyDataSetChanged();
                                alertDialog.dismiss();
                            }else{
                                Toast.makeText(MainActivity.this, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "onResponse: " + response.code());
                            }
                        }
                        @Override
                        public void onFailure(Call<Motorbike> call, Throwable t) {
                            Log.e(TAG, "onResponse: " + t.getMessage());
                        }
                    });
                }
            }else{
                Toast.makeText(this, "Vui lòng chọn hình ảnh sản phẩm", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.show();
    }

    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        imagePickerLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    if (imageUri != null) {
                        img_upload.setImageURI(imageUri);
                        String realPath = RealPathUtil.getRealPath(MainActivity.this, imageUri);
                        if (realPath != null) {
                            imageMotor = new File(realPath);
                        }
                    }
                }
            }
    );

    private RequestBody getRequestBody(String value) {
        if (value != null) {
            return RequestBody.create(MediaType.parse("multipart/form-data"), value);
        } else {
            Log.e("TAG", "getRequestBody: value null");
            return null;
        }
    }
}