package fpoly.longlt.duan1.adapter;

import static fpoly.longlt.duan1.screen.LoginScreen.id_userHere;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fpoly.longlt.duan1.R;
import fpoly.longlt.duan1.dao.CartDAO;
import fpoly.longlt.duan1.fragment.CartFragment;
import fpoly.longlt.duan1.model.GioHang;
import fpoly.longlt.duan1.model.SanPham;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    Context mContext;
//    ArrayList<SanPham> arrayList;
    ArrayList<GioHang> lst;
//    ArrayList<GioHang> selectedItems;
    CartDAO cartDAO;
    CartFragment cartFragment;
    CartAdapter adapter;
    OnSelectedChangeListener onSelectedChangeListener;
    OnCartUpdateListener listener; // Interface listener
    public interface OnSelectedChangeListener {
        void onSelectionChanged(List<GioHang> selectedItems);
    }

    public CartAdapter(Context mContext, ArrayList<GioHang> lst, OnCartUpdateListener listener) {
        this.mContext = mContext;
        this.lst = lst;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cart_item, null);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
//        SanPham sanPham = arrayList.get(position);
        GioHang gioHang = lst.get(position);

//        Log.d("TAG", "onBindViewHolder: "+gioHang.getName()+": "+gioHang.getQuantity());
        holder.tvNameCart.setText(gioHang.getName());
        holder.tvquantity.setText(gioHang.getQuantity()+"");
        holder.tvPriceCart.setText(gioHang.getPrice() + " VND");
        holder.tvColorCart.setText(gioHang.getColor());
        holder.tvSizeCart.setText(gioHang.getSize());


       String imgPath = lst.get(position).getImgPath();
        if (imgPath != null && !imgPath.isEmpty()) {
            File imgFile = new File(imgPath);
            if (imgFile.exists()) {
                Uri uri = Uri.fromFile(imgFile);
                holder.imgProduct.setImageURI(uri);
            } else {
                holder.imgProduct.setImageResource(R.drawable.img_2); // Ảnh mặc định
            }
        } else {
            holder.imgProduct.setImageResource(R.drawable.img_3); // Ảnh mặc định
        }
        holder.cb_selected.setChecked(gioHang.getIs_selected()==1);
//        holder.cb_selected.setOnCheckedChangeListener(null); // Xóa lắng nghe cũ
//        holder.cb_selected.setChecked(selectedItems.contains(lst));

        holder.cb_selected.setOnCheckedChangeListener((buttonView, isChecked) -> {
            gioHang.setIs_selected(isChecked?1:0);
            cartDAO = new CartDAO(mContext);
            cartDAO.updateItemSelection(lst.get(position).getCart_id(), isChecked);
//            if (isChecked){
//                lst.add(gioHang);
//            }else {
//                lst.remove(gioHang);
//            }
            updateTotalPrice();
//            onSelectedChangeListener.onSelectionChanged(lst);
        });


        // Button cộng
        // Button cộng
        holder.btn_cong.setOnClickListener(v -> {
//            cartDAO = new CartDAO(mContext);
//            GioHang gioHang1 = lst.get(position);
//            cartDAO.addToCart(id_userHere,gioHang1.getChitietsp_id(),1);
            int newQuantity = gioHang.getQuantity() + 1;
            Log.d("cong", "onBindViewHolder: "+newQuantity);
            // Cập nhật cơ sở dữ liệu
            gioHang.setQuantity(newQuantity);
            cartDAO = new CartDAO(mContext);
            boolean isUpdated = cartDAO.updateQuantity(newQuantity,lst.get(position).getCart_id());

            if (isUpdated) {
                holder.tvquantity.setText(String.valueOf(newQuantity)); // Cập nhật giao diện
                updateTotalPrice();
//                Toast.makeText(mContext, "Cập nhật thành công. Số lượng mới: " + newQuantity, Toast.LENGTH_SHORT).show();
//                updateTotalPrice();  // Cập nhật tổng tiền
            } else {
                Toast.makeText(mContext, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btn_tru.setOnClickListener(v -> {

            if (gioHang.getQuantity() > 1) {
                int newQuantity = lst.get(position).getQuantity() - 1;

                // Cập nhật cơ sở dữ liệu
                gioHang.setQuantity(newQuantity);
                cartDAO = new CartDAO(mContext);
                boolean isUpdated = cartDAO.updateQuantity(newQuantity, lst.get(position).getCart_id());

                if (isUpdated) {
                    holder.tvquantity.setText(String.valueOf(newQuantity)); // Cập nhật giao diện
                    updateTotalPrice();
//                    Toast.makeText(mContext, "Cập nhật thành công. Số lượng mới: " + newQuantity, Toast.LENGTH_SHORT).show();
//                    updateTotalPrice();  // Cập nhật tổng tiền
                } else {
                    Toast.makeText(mContext, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, "Số lượng không thể nhỏ hơn 1", Toast.LENGTH_SHORT).show();
            }
        });



        // Icon xóa sản phẩm
        holder.imgDelete.setOnClickListener(v -> {
            cartDAO = new CartDAO(mContext);
            boolean check = cartDAO.deleteProduct(lst.get(position).getCart_id());  // Xóa sản phẩm khỏi DB
            if (check){
                lst.remove(position);  // Xóa sản phẩm khỏi danh sách
                notifyDataSetChanged();  // Thông báo RecyclerView cập nhật
//                updateTotalPrice();  // Cập nhật tổng tiền
                Toast.makeText(mContext, "xóa thành công", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(mContext, "xóa thất bại", Toast.LENGTH_SHORT).show();
            }
        });

//        updateTotalPrice();  // Cập nhật tổng tiền khi item được hiển thị
    }

    @Override
    public int getItemCount() {
        return lst != null ? lst.size() : 0;
    }

    // Cập nhật tổng tiền
    public void updateTotalPrice() {
        int total = 0;
        for (GioHang gioHang : lst) {
            if (gioHang.getIs_selected()==1) {
                total += gioHang.getPrice() * gioHang.getQuantity();
            }
        }
        if (listener != null) {
            listener.updateTotalPrice(total);  // Gọi listener để cập nhật tổng tiền trong Fragment
        }
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        TextView tvNameCart, tvPriceCart, tvColorCart, tvSizeCart, tvquantity, btn_cong, btn_tru;
        Button btnBuyNow;
        ImageView imgDelete, imgProduct;
        CheckBox cb_selected;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameCart = itemView.findViewById(R.id.tv_product_name);
            tvPriceCart = itemView.findViewById(R.id.tv_product_price);
            tvColorCart = itemView.findViewById(R.id.tv_product_color);
            tvSizeCart = itemView.findViewById(R.id.tv_product_size);
            btn_cong = itemView.findViewById(R.id.btn_congCart);
            btn_tru = itemView.findViewById(R.id.btn_truCart);
//            btnBuyNow = itemView.findViewById(R.id.btn_buy_now);
            imgDelete = itemView.findViewById(R.id.img_remove);
            imgProduct = itemView.findViewById(R.id.img_product);
            tvquantity = itemView.findViewById(R.id.tv_quantity);
            cb_selected = itemView.findViewById(R.id.cb_selected);
        }
    }
//    public ArrayList<GioHang> getSelectedItems() {
//        return new ArrayList<>(lst);
//    }

    // Interface để Fragment nhận thông báo
    public interface OnCartUpdateListener {
        void updateTotalPrice(int total);
    }
}
