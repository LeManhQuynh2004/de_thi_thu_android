package com.quynhlm.dev.demoonthi1.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.quynhlm.dev.demoonthi1.Api.ApiRequest;
import com.quynhlm.dev.demoonthi1.Model.Motorbike;
import com.quynhlm.dev.demoonthi1.OnItemClickListener;
import com.quynhlm.dev.demoonthi1.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MotorAdapter extends RecyclerView.Adapter<MotorAdapter.MotorViewHolder>{
    Context context;
    List<Motorbike> list;
    ApiRequest apiRequest = new ApiRequest();

    private OnItemClickListener itemClickListener;

    public void setItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }


    public MotorAdapter(Context context, List<Motorbike> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MotorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_motor,parent,false);
        return new MotorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MotorViewHolder holder, int position) {
        Motorbike motorbike = list.get(position);
        Glide
                .with(context)
                .load(motorbike.getImage_ph32353())
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.img_motor);
        holder.tv_name.setText(motorbike.getName_ph32353());
        holder.tv_price.setText(motorbike.getPrice_ph32353() + "");
        holder.tv_describe.setText(motorbike.getDescribe_ph32353());
        holder.tv_color.setText(motorbike.getColor_ph32353());
        holder.tv_delete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Bạn có chắc chắn muốn xóa bản ghi này không ?");
            builder.setTitle("Thông báo");
            builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Call<Void> deleteData = apiRequest.getApiService().deleteMotor(motorbike.get_id());
                    deleteData.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful()) {
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                list.remove(position);
                                notifyDataSetChanged();
                            }else{
                                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                Log.e("TAG", "onResponse: "+response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.e("TAG", "onResponse: "+t.getMessage());
                        }
                    });
                }
            });
            builder.setNegativeButton("Hủy",null);
            builder.show();
        });
        holder.tv_update.setOnClickListener(v -> {
            try {
                if (itemClickListener != null) {
                    itemClickListener.UpdateData(position);
                }
            } catch (Exception e) {
                Log.e("TAG", "onBindViewHolder: " + e);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MotorViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name,tv_price,tv_describe,tv_color,tv_delete,tv_update;
        ImageView img_motor;
        public MotorViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_describe = itemView.findViewById(R.id.tv_describe);
            tv_color = itemView.findViewById(R.id.tv_color);
            img_motor = itemView.findViewById(R.id.img_motor);
            tv_delete = itemView.findViewById(R.id.tv_delete);
            tv_update = itemView.findViewById(R.id.tv_update);
        }
    }
}
