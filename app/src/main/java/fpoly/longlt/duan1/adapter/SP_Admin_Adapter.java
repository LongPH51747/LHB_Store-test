package fpoly.longlt.duan1.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

import fpoly.longlt.duan1.R;
import fpoly.longlt.duan1.dao.SanPhamDAO;
import fpoly.longlt.duan1.model.SanPham;

public class SP_Admin_Adapter extends RecyclerView.Adapter<SP_Admin_Adapter.SP_Admin_Holder> {
    ArrayList<SanPham> lst = new ArrayList<>();
    Context context;
    SanPhamDAO sanPhamDAO;
    SanPham sanPham;

    public SP_Admin_Adapter(ArrayList<SanPham> lst, Context context) {
        this.lst = lst;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        if (lst != null) {
            return lst.size();
        }
        return 0;
    }
    @Override
    public void onBindViewHolder(@NonNull SP_Admin_Holder holder, @SuppressLint("RecyclerView") int position) {
        holder.tv_name_sp_admin.setText(lst.get(position).getTenSp());
        holder.tv_price_sp_admin.setText(lst.get(position).getPrice() + "");
//        String imgURI = lst.get(position).getImg();
//        if (imgURI != null && !imgURI.isEmpty()){
//            try {
//                Uri uri = Uri.parse(imgURI);
//                holder.img_sp_admin.setImageURI(uri);
//            } catch (Exception e){
//                Log.e("Error", "Invalid URI: " + imgURI, e);
//                holder.img_sp_admin.setImageResource(R.drawable.img_2);
//            }
//        }else {
//            holder.img_sp_admin.setImageResource(R.drawable.img_2);  // Đặt ảnh mặc định nếu không có URI
//        }
        if (lst.size() > 0) {
            try {
                String imgPath = lst.get(position).getImg();  // Lấy đường dẫn tệp từ SQLite
                File imgFile = new  File(imgPath);  // Tạo đối tượng File từ đường dẫn
                if(imgFile.exists()) {
                    Uri uri = Uri.fromFile(imgFile);  // Chuyển đường dẫn thành URI
                    holder.img_sp_admin.setImageURI(uri);  // Đặt URI vào ImageView
                }
            } catch (Exception e){
                holder.img_sp_admin.setImageResource(R.drawable.img_2);
            }
        }

        if (lst.get(position).getStatus() == 1){
            holder.chk_status_sp_admin.setChecked(true);
            holder.tv_name_sp_admin.setPaintFlags(holder.tv_name_sp_admin.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.tv_price_sp_admin.setPaintFlags(holder.tv_price_sp_admin.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
        else {
            holder.chk_status_sp_admin.setChecked(false);
            holder.tv_name_sp_admin.setPaintFlags(holder.tv_name_sp_admin.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tv_price_sp_admin.setPaintFlags(holder.tv_price_sp_admin.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        holder.chk_status_sp_admin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sanPhamDAO = new SanPhamDAO(context);
                int id = lst.get(position).getSpId();
                boolean check = sanPhamDAO.updateStatusSP(id, isChecked);
                if (check){
                    Toast.makeText(context, "đã thay đổi trạng thái thành "+(isChecked?"còn hàng":"hết hàng"), Toast.LENGTH_SHORT).show();
                    lst.clear();
                    lst = sanPhamDAO.getAllSP();
                    if (lst.get(position).getStatus() == 1){
                        holder.chk_status_sp_admin.setChecked(true);
                        holder.tv_name_sp_admin.setPaintFlags(holder.tv_name_sp_admin.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        holder.tv_price_sp_admin.setPaintFlags(holder.tv_price_sp_admin.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    }
                    else {
                        holder.chk_status_sp_admin.setChecked(false);
                        holder.tv_name_sp_admin.setPaintFlags(holder.tv_name_sp_admin.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        holder.tv_price_sp_admin.setPaintFlags(holder.tv_price_sp_admin.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    }
                }
                Log.d("check", lst.get(position).getStatus()+"");
//                if (holder.chk_status_sp_admin.isChecked()){
//                    lst.get(position).setStatus(1);
//                }
//                else {
//                    lst.get(position).setStatus(0);
//                }
            }
        });

    }

    @NonNull
    @Override
    public SP_Admin_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SP_Admin_Holder(View.inflate(context, R.layout.list_item_sp_admin, null));
    }
    public static final class SP_Admin_Holder extends RecyclerView.ViewHolder {
        ImageView img_sp_admin;
        TextView tv_name_sp_admin, tv_price_sp_admin;
        CheckBox chk_status_sp_admin;
        public SP_Admin_Holder(@NonNull View itemView) {
            super(itemView);
            img_sp_admin = itemView.findViewById(R.id.img_sp_admin);
            tv_name_sp_admin = itemView.findViewById(R.id.tv_name_sp_admin);
            tv_price_sp_admin = itemView.findViewById(R.id.tv_price_sp_admin);
            chk_status_sp_admin = itemView.findViewById(R.id.chk_hidden_sp);
        }
    }
}
