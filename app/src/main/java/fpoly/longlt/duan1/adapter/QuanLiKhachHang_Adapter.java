package fpoly.longlt.duan1.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpoly.longlt.duan1.R;
import fpoly.longlt.duan1.dao.UserDAO;
import fpoly.longlt.duan1.model.KhachHang;
import fpoly.longlt.duan1.model.User;

public class QuanLiKhachHang_Adapter extends BaseAdapter {
    ArrayList<User> lst = new ArrayList<>();
    Context context;
    UserDAO userDAO;
    CheckBox chk_hidden_khachhang;
    ImageView img_quanlikh;
    TextView tv_name_kh, tv_sdt, tv_address;
    public QuanLiKhachHang_Adapter(ArrayList<User> lst, Context context) {
        this.lst = lst;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (lst != null){
            return lst.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(context,R.layout.list_item_quanli_khachhang,null);
        chk_hidden_khachhang = convertView.findViewById(R.id.chk_hidden_kh);
        img_quanlikh = convertView.findViewById(R.id.img_avata_quanlikh);
        tv_name_kh = convertView.findViewById(R.id.tv_ten_kh);
        tv_address = convertView.findViewById(R.id.tv_address_kh);
        tv_sdt = convertView.findViewById(R.id.tv_sdt_kh);

        userDAO = new UserDAO(context);
        lst = userDAO.getAllUser();
        User user = lst.get(position);
        tv_sdt.setText("SĐT khách hàng: "+user.getPhoneNumber());
        if (user.getNameUser() == null){
            tv_name_kh.setText("Tên khách hàng: ");
        }
        else{
            tv_name_kh.setText("Tên khách hàng: "+user.getNameUser());
        }
        if (user.getAddress() == null){
            tv_address.setText("Địa chỉ khách hàng: ");
        } else {
            tv_address.setText("Địa chỉ khách hàng: "+user.getAddress());
        }
        if (user.getStatus() == 1){
            chk_hidden_khachhang.setChecked(true);
            tv_name_kh.setPaintFlags(tv_name_kh.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            tv_sdt.setPaintFlags(tv_sdt.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            tv_address.setPaintFlags(tv_address.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }else {
            chk_hidden_khachhang.setChecked(false);
            tv_name_kh.setPaintFlags(tv_name_kh.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tv_sdt.setPaintFlags(tv_sdt.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tv_address.setPaintFlags(tv_address.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        chk_hidden_khachhang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                userDAO = new UserDAO(context);
                boolean check = userDAO.updateStatus(user.getId_user(), isChecked);
                if (check){
                    Toast.makeText(context, "Thay đổi trạng thái khách hàng thành công", Toast.LENGTH_SHORT).show();
                    lst.clear();
                    lst = userDAO.getAllUser();
                    notifyDataSetChanged();
                }
            }
        });
        String imageName = user.getImageAvatar();  // Đây là tên ảnh bạn lưu trong cơ sở dữ liệu, ví dụ: "product_image"
        // Lấy ID tài nguyên từ tên ảnh trong drawable
        int imageResId = convertView.getContext().getResources().getIdentifier(imageName, "drawable", convertView.getContext().getPackageName());
        // Kiểm tra nếu tài nguyên ảnh tồn tại
        if (imageResId != 0) {
            Log.d("anh", "getView: "+imageResId);
            Log.d("anh", "getView: "+imageName);
            img_quanlikh.setImageResource(imageResId);  // Set ảnh từ drawable vào ImageView
        } else {
            // Nếu không tìm thấy ảnh, có thể set ảnh mặc định
            img_quanlikh.setImageResource(R.drawable.imgkh);  // Placeholder image
        }

        return convertView;
    }

}
