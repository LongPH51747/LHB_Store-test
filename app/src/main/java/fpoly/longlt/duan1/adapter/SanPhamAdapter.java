package fpoly.longlt.duan1.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

import fpoly.longlt.duan1.dao.SanPhamDAO;
import fpoly.longlt.duan1.R;
import fpoly.longlt.duan1.model.ChiTietSP;
import fpoly.longlt.duan1.model.SanPham;
import fpoly.longlt.duan1.screen.ProductDetailScreen;


public class    SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.SanPhamViewHolder> {
    Context context;
    SanPhamDAO sanPhamDAO;
    ArrayList<SanPham> arrayList;



    public SanPhamAdapter(Context context, ArrayList<SanPham> arrayList, SanPhamDAO sanPhamDAO) {
        this.context = context;
        this.arrayList = arrayList;
        this.sanPhamDAO = sanPhamDAO;
    }

    @NonNull
    @Override
    public SanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_sp_kh, parent, false);
        return new SanPhamViewHolder(view);
    }
    public void updataData(ArrayList<SanPham> newList){
        this.arrayList = newList;
        notifyDataSetChanged();

    }
    @Override
    public void onBindViewHolder(@NonNull SanPhamViewHolder holder, int position) {
        SanPham sanPham = arrayList.get(position);
        holder.tvNameSP.setText(sanPham.getTenSp());
        holder.tvPriceSP.setText("đ " + sanPham.getPrice());
//        if (holder.imgSP == null){
//            Log.d("bug","imgSP is null at position: " + position);
//        }
//        else {
//            Log.d("bug","imgSP is not null at position: " + position);
//        }

        if (arrayList.size() > 0) {
            Log.d("anh", "ảnh: "+arrayList.get(position).getImg());
            try {
                String imgPath = arrayList.get(position).getImg();  // Lấy đường dẫn tệp từ SQLite
                File imgFile = new  File(imgPath);  // Tạo đối tượng File từ đường dẫn
                if(imgFile.exists()) {
                    Uri uri = Uri.fromFile(imgFile);  // Chuyển đường dẫn thành URI
                    holder.imgSP.setImageURI(uri);  // Đặt URI vào ImageView
                }
            } catch (Exception e){
                holder.imgSP.setImageResource(R.drawable.img_2);
            }
        }


        holder.itemView.setOnClickListener(v -> {
            SanPham sanPham1 = arrayList.get(position);
            Bundle bundle = new Bundle();
            Intent intent = new Intent();
            bundle.putInt("id", sanPham1.getSpId());
            intent.putExtras(bundle);
            intent.setClass(context, ProductDetailScreen.class);
            context.startActivity(intent);
        });



        String imageName = arrayList.get(position).getImg();  // Đây là tên ảnh bạn lưu trong cơ sở dữ liệu, ví dụ: "product_image"
        // Lấy ID tài nguyên từ tên ảnh trong drawable
        int imageResId = holder.itemView.getContext().getResources().getIdentifier(imageName, "drawable", holder.itemView.getContext().getPackageName());
        // Kiểm tra nếu tài nguyên ảnh tồn tại
        if (imageResId != 0) {
            holder.imgSP.setImageResource(imageResId);  // Set ảnh từ drawable vào ImageView
        } else {
            // Nếu không tìm thấy ảnh, có thể set ảnh mặc định
//            holder.imgSP.setImageResource(R.drawable.img_2);  // Placeholder image
        }
        holder.tv_hethang.setVisibility(View.GONE);
        holder.overlay.setVisibility(View.GONE);
        if (sanPham.getStatus() == 0) {
            holder.tv_hethang.setVisibility(View.VISIBLE);
            Log.d("OVERLAY_DEBUG", "Item position: " + position + ", Visibility: " + holder.overlay.getVisibility());
        }
//        else {
//            holder.tv_hethang.setVisibility(View.VISIBLE);
//            holder.overlay.setVisibility(View.VISIBLE);
//        }
//        if (sanPham.getStatus() != 1){
//            holder.tv_hethang.setVisibility(View.VISIBLE);
//            holder.overlay.setVisibility(View.VISIBLE);
//            holder.overlay.setFocusable(false);
//            holder.overlay.setEnabled(false);
//        }

    }

    @Override
    public int getItemCount() {
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    public static class SanPhamViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSP;
        TextView tvNameSP, tvPriceSP;

        View overlay;
        CardView tv_hethang;

        public SanPhamViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSP = itemView.findViewById(R.id.img_sp_kh);
            tvNameSP = itemView.findViewById(R.id.tv_name_sp_kh);
            tvPriceSP = itemView.findViewById(R.id.tv_price_sp_kh);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemView.getContext().startActivity(new Intent(itemView.getContext(), ProductDetailScreen.class));
                }
            });
            overlay = itemView.findViewById(R.id.overlay);
            tv_hethang = itemView.findViewById(R.id.tv_hethang);

        }
    }
    //nothing
}
