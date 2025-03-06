package fpoly.longlt.duan1.adapter;

import static fpoly.longlt.duan1.screen.LoginScreen.id_userHere;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

//import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import fpoly.longlt.duan1.R;
import fpoly.longlt.duan1.dao.BillsDAO;
import fpoly.longlt.duan1.model.DonHang;
import fpoly.longlt.duan1.screen.OrderDetailScreen;

public class BillsAdapter extends RecyclerView.Adapter<BillsAdapter.BillsHolderView> {
    Context context;
    ArrayList<DonHang> list;
    BillsDAO billsDAO;
    Uri uri;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    public BillsAdapter(Context context, ArrayList<DonHang> list, BillsDAO billsDAO) {
        this.context = context;
        this.list = list;
        this.billsDAO = billsDAO;
    }

    @NonNull
    @Override
    public BillsHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_quanli_donhang, parent, false);
        return new BillsHolderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillsHolderView holder, @SuppressLint("RecyclerView") int position) {
        billsDAO = new BillsDAO(context.getApplicationContext());
        DonHang donHang = list.get(position);
        holder.txt_ma_bills.setText("Mã đơn hàng:\n FTMC" + donHang.getOd_id()+"BMC");
        holder.txt_date_bills.setText(sdf.format(donHang.getOd_date()));
        holder.tv_tongtien.setText("Tổng tiền: "+donHang.getTotal_price());
        if (donHang.getStatus()==1){
            holder.tv_status.setText("Đang giao");
        }else if (donHang.getStatus()==2){
            holder.tv_status.setText("Đã giao");
        }
        else if (donHang.getStatus()==-1){
            holder.tv_status.setText("Đã hủy");
        }
        else {
            holder.tv_status.setText("Chờ xác nhận");
        }
        if (list.size() > 0) {
            try {
                ArrayList<String> imgPath = billsDAO.getImgBills(list.get(position).getOd_id());  // Lấy đường dẫn tệp từ SQLite
                File imgFile = new  File(imgPath.get(0));  // Tạo đối tượng File từ đường dẫn
                if(imgFile.exists()) {
                    uri = Uri.fromFile(imgFile);
                    holder.img_sp_bills.setImageURI(uri);// Chuyển đường dẫn thành URI
//                    holder.img_sp_bills.setImageURI(uri);  // Đặt URI vào ImageView
//                    Picasso.get().load(uri).into(holder.img_sp_bills);
                }
                else {
                    holder.img_sp_bills.setImageResource(R.drawable.img_2);
                }
            } catch (Exception e){
                holder.img_sp_bills.setImageResource(R.drawable.img_2);
            }
        }
        holder.txt_show_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDetailScreen.class);
                intent.putExtra("ma_bills", holder.txt_ma_bills.getText().toString());
                intent.putExtra("date_bills", list.get(position).getOd_date());
                intent.putExtra("id", list.get(position).getOd_id());
                intent.putExtra("tongtien", list.get(position).getTotal_price());
//                intent.putExtra("gia", list.get(position));
//                intent.putExtra("imgPath_bills", uri);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        return 0;
    }

    public static class BillsHolderView extends RecyclerView.ViewHolder {
        CardView card_bill_od;
        ImageView img_sp_bills;
        TextView txt_ma_bills, txt_date_bills, txt_show_more, tv_tongtien, tv_status;
        public BillsHolderView(@NonNull View view) {
            super(view);
            tv_tongtien = view.findViewById(R.id.tv_total_price);
            tv_status = view.findViewById(R.id.tv_status);
            img_sp_bills = view.findViewById(R.id.img_sp_donhang);
            txt_ma_bills = view.findViewById(R.id.tv_ma_donhang);
            txt_date_bills = view.findViewById(R.id.txt_date_bill);
            txt_show_more = view.findViewById(R.id.txtShowMoreBills);
            card_bill_od = view.findViewById(R.id.card_bills_od);
        }
    }
}
