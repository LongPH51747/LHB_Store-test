package fpoly.longlt.duan1.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fpoly.longlt.duan1.R;
import fpoly.longlt.duan1.dao.DonHangDao;
import fpoly.longlt.duan1.dao.QuanLiDonHangDao;
import fpoly.longlt.duan1.model.DonHang;
import fpoly.longlt.duan1.model.DonHangChiTiet;
import fpoly.longlt.duan1.screen.OrderDetailScreen;

public class QuanLiDonHang_Adapter extends BaseAdapter {
    ArrayList<DonHang> lst = new ArrayList<>();
    Context context;
    DonHang donHang = new DonHang();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    ImageView img_anh;
    QuanLiDonHangDao dao;
    TextView tv_ma_dh, tv_ten_kh, tv_ngay_dat, tv_tong_tien, tv_status, tv_soluong;
    SimpleDateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");
    Button btn_xacnhan, btn_huy, btn_dagiaohang, btn_chitiet;

    public QuanLiDonHang_Adapter(ArrayList<DonHang> lst, Context context) {
        this.lst = lst;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (lst != null) {
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
        convertView = View.inflate(context, R.layout.list_item_quanlidonhang_admin,null);

        img_anh = convertView.findViewById(R.id.img_sp_donhang_admin);
        tv_ma_dh = convertView.findViewById(R.id.tv_ma_don_hang_admin);
        tv_ten_kh = convertView.findViewById(R.id.tv_ten_khach_hang_admin);
        tv_ngay_dat = convertView.findViewById(R.id.tv_ngay_dat_hang_admin);
        tv_tong_tien = convertView.findViewById(R.id.tv_tong_gia_tri_admin);
        tv_status = convertView.findViewById(R.id.tv_status_donhang_admin);
        tv_soluong = convertView.findViewById(R.id.tv_soluong_don_hang_admin);
        btn_xacnhan = convertView.findViewById(R.id.btn_xac_nhan_donhang_adin);
        btn_dagiaohang = convertView.findViewById(R.id.btn_giaohang_thanhcong);
        btn_huy = convertView.findViewById(R.id.btn_huy_donhang_admin);
        btn_chitiet = convertView.findViewById(R.id.btn_chi_tiet_donhang_admin);

        dao = new QuanLiDonHangDao(context);
        donHang = lst.get(position);
        tv_soluong.setText("Số lượng sản phẩm: "+dao.getQuantity(donHang.getOd_id()));
        tv_ma_dh.setText("Mã đơn:\n FTMC" + donHang.getOd_id()+"BMC");
        try {
            Date date = sdf.parse(sdf.format(donHang.getOd_date()));
            String odDate = targetFormat.format(date);
            tv_ngay_dat.setText("Ngày đặt: "+odDate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        tv_tong_tien.setText("Tổng tiền: "+(donHang.getTotal_price()-20000));
        tv_ten_kh.setText("Tên khách hàng: "+dao.getNameKH(donHang.getUser_id()));

        btn_dagiaohang.setVisibility(View.GONE);
        if (donHang.getStatus()==0){
            btn_xacnhan.setVisibility(View.VISIBLE);
            btn_dagiaohang.setVisibility(View.GONE);
            tv_status.setText("Đang xử lý");
        }
        else if (donHang.getStatus()==1){
            btn_xacnhan.setVisibility(View.GONE);
            btn_dagiaohang.setVisibility(View.VISIBLE);
            tv_status.setText("Đã xác nhận");
        }
        else if (donHang.getStatus()==2){
            btn_dagiaohang.setVisibility(View.GONE);
            btn_xacnhan.setVisibility(View.GONE);
            btn_huy.setVisibility(View.GONE);
            tv_status.setText("Đã giao");
        }
        Log.d("bill", "số lượng: "+dao.getQuantity(donHang.getOd_id()));
        Log.d("bill", "statussss: "+donHang.getStatus());
        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao.updateStatusDonHang(lst.get(position).getOd_id(), 1);
                lst.clear();
                lst = dao.getAllDonHang();
                notifyDataSetChanged();
            }
        });
        btn_dagiaohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao.updateStatusDonHang(lst.get(position).getOd_id(), 2);
                lst.clear();
                lst = dao.getAllDonHang();
                notifyDataSetChanged();
            }
        });

        btn_chitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<DonHangChiTiet> donHangChiTiets = dao.getDonHangChiTiet(lst.get(position).getOd_id());
                Log.d("TAG", "donhangchitietadmin: "+donHangChiTiets.size());
                Bundle bundle = new Bundle();
                Intent intent = new Intent(context, OrderDetailScreen.class);
//                bundle.putSerializable("data", donHangChiTiets);
                bundle.putInt("tongtien", donHang.getTotal_price());
                bundle.putInt("id_user", donHang.getUser_id());
                bundle.putInt("id", lst.get(position).getOd_id());
                bundle.putString("ma_bills", tv_ma_dh.getText().toString());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
