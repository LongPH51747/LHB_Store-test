package fpoly.longlt.duan1.adapter;

import static fpoly.longlt.duan1.screen.ProductDetailScreen.sp_id;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

import fpoly.longlt.duan1.R;
import fpoly.longlt.duan1.dao.SanPhamChiTietDAO;
import fpoly.longlt.duan1.dao.SanPhamDAO;
import fpoly.longlt.duan1.model.ChiTietSP;
import fpoly.longlt.duan1.model.GioHang;
import fpoly.longlt.duan1.model.SanPham;

public class KiemLaiAdapter extends RecyclerView.Adapter<KiemLaiAdapter.KiemLaiViewHolder> {
    Context context;
    ArrayList<ChiTietSP> list = new ArrayList<>();
    ArrayList<GioHang> listGioHang = new ArrayList<>();
    String nameString;
    public static int priceAmount, priceOneProduct;
    SanPhamChiTietDAO sanPhamChiTietDAO;

    public KiemLaiAdapter(Context context, ArrayList<ChiTietSP> list, ArrayList<GioHang> listGioHang) {
        this.context = context;
        this.list = list;
        this.listGioHang = listGioHang;
        Log.d("Adapter", "ListGioHang size: " + (listGioHang != null ? listGioHang.size() : "null"));

    }

//    public KiemLaiAdapter(ArrayList<GioHang> listGioHang, Context context) {
//        this.context = context;
//        this.listGioHang = listGioHang;
//    }

    @NonNull
    @Override
    public KiemLaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prepare_order, parent, false);
        return new KiemLaiViewHolder(view);
    }
    @Override
    public int getItemCount() {
        Log.d("TAG", "getItemCount() list size: " + (list != null ? list.size() : "list is null"));
        Log.d("TAG", "getItemCount() listGioHang size: " + (listGioHang != null ? listGioHang.size() : "listGioHang is null"));
        if(list.size() != 0){
            return list.size();
        }
        else if (listGioHang!=null){
            return listGioHang.size();
        }
        return 0;
    }
    @Override
    public void onBindViewHolder(@NonNull KiemLaiViewHolder holder, int position) {
        Log.d("TAG", "ListGioHang is null: " + (listGioHang == null));
        Log.d("TAG", "ListGioHang size: " + (listGioHang != null ? listGioHang.size() : "null"));
        if (listGioHang!=null && !listGioHang.isEmpty()){
            GioHang gioHang = listGioHang.get(position);
            Log.d("TAG", "Binding GioHang at position: " + position);
            Log.d("TAG", "Số lượng sản phẩm trong listGioHang: " + listGioHang.size());
            holder.name.setText("Name Product: "+ gioHang.getName());
            holder.colorAndSize.setText("Màu sắc: " + gioHang.getColor()+" | Size: "+ gioHang.getSize());
            holder.number.setText("x" + gioHang.getQuantity());
            holder.price.setText("Price: " + gioHang.getPrice()+"đ");
            Log.d("giohangadapter", "onBindViewHolder: "+gioHang.getName()+gioHang.getColor()+gioHang.getSize()+gioHang.getPrice()+gioHang.getQuantity()+gioHang.getImgPath());
            try {
                String imgPath = gioHang.getImgPath();  // Lấy đường dẫn tệp từ SQLite
                File imgFile = new File(imgPath);  // Tạo đối tượng File từ đường dẫn
                if (imgFile.exists()) {
                    Uri uri = Uri.fromFile(imgFile);  // Chuyển đường dẫn thành URI
                    holder.imgPrepare.setImageURI(uri);
                }else {
                    holder.imgPrepare.setImageResource(R.drawable.img_2);
                }
            }catch (Exception e){
                holder.imgPrepare.setImageResource(R.drawable.img_2);
            }
        }
        else if (list!=null && !list.isEmpty()){
            Log.e("size", "onBindViewHolder: "+list.size());
            ChiTietSP chiTietSP = list.get(position);
            SanPhamDAO dao = new SanPhamDAO(context);
            SanPham sanPham = dao.getSP(chiTietSP.getSp_id());
            sanPhamChiTietDAO = new SanPhamChiTietDAO(context);
            priceOneProduct = sanPhamChiTietDAO.getPriceByID(chiTietSP.getSp_id());
            nameString = sanPhamChiTietDAO.getNameProductByID_SP(chiTietSP.getSp_id());
            Log.e("gia1sp", "onBindViewHolder: " + priceOneProduct );
            //        nameString = chiTietSP.getNameSpChiTiet();
            //        priceAmount = priceOneProduct * chiTietSP.getSoluong();
            holder.name.setText("Name Product: "+ nameString);
            holder.colorAndSize.setText("Màu sắc: " + chiTietSP.getColor()+" | Size: "+ chiTietSP.getSize());
            holder.number.setText("x" + chiTietSP.getSoluong());
            holder.price.setText("Price: " + priceOneProduct +"đ");
            try {
                String imgPath = sanPham.getImg();  // Lấy đường dẫn tệp từ SQLite
                File imgFile = new File(imgPath);  // Tạo đối tượng File từ đường dẫn
                if (imgFile.exists()) {
                    Uri uri = Uri.fromFile(imgFile);  // Chuyển đường dẫn thành URI
                    holder.imgPrepare.setImageURI(uri);  // Đặt URI vào ImageView
                }
                else {
                    holder.imgPrepare.setImageResource(R.drawable.img_2);
                }
            } catch (Exception e) {
                holder.imgPrepare.setImageResource(R.drawable.img_2);
            }
        }
    }



    public static class KiemLaiViewHolder extends RecyclerView.ViewHolder {
        TextView name, colorAndSize, price, number;
        ImageView imgPrepare;
        public KiemLaiViewHolder(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.tvProductName);
            colorAndSize = view.findViewById(R.id.tvProductDetailsColorAndSize);
            price = view.findViewById(R.id.tvProductPrice);
            number = view.findViewById(R.id.tvProductQuantity);
            imgPrepare = view.findViewById(R.id.ivProductImage);
        }
    }
}
