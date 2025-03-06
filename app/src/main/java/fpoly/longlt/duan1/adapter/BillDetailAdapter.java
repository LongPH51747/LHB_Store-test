package fpoly.longlt.duan1.adapter;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import fpoly.longlt.duan1.R;
import fpoly.longlt.duan1.dao.BillsDAO;
import fpoly.longlt.duan1.dao.QuanLiDonHangDao;
import fpoly.longlt.duan1.model.DonHang;
import fpoly.longlt.duan1.model.DonHangChiTiet;

public class BillDetailAdapter extends BaseAdapter {
    ArrayList<DonHangChiTiet> list = new ArrayList<>();
    Context context;
    String madonhang;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    int total;

    public BillDetailAdapter(ArrayList<DonHangChiTiet> list, Context context, String madonhang, int total) {
        this.list = list;
        this.context = context;
        this.madonhang = madonhang;
        this.total = total;
    }

    public BillDetailAdapter(ArrayList<DonHangChiTiet> list, Context context, String madonhang) {
        this.list = list;
        this.context = context;
        this.madonhang = madonhang;
    }

    @Override
    public int getCount() {
        return list!=null?list.size():0;
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
        convertView = View.inflate(context, R.layout.list_item_orderdetail, null);
        TextView tv_madon = convertView.findViewById(R.id.tvMaOrderTD);
        TextView tv_oderdate = convertView.findViewById(R.id.tvOrderDateDT);
        TextView tv_soluong = convertView.findViewById(R.id.tvOrderQuantity);
        TextView tv_gia = convertView.findViewById(R.id.tvOrderPriceDT);
        TextView tv_thanhtien = convertView.findViewById(R.id.tvTotalAmountDT);
        TextView tv_namesp_oddetail_screen = convertView.findViewById(R.id.tv_namesp_oddetail_screen);
        ImageView img_product = convertView.findViewById(R.id.imgOrderProductDT);
        TextView tv_ship = convertView.findViewById(R.id.tvShippingFeeDT);

//        QuanLiDonHangDao daok = new QuanLiDonHangDao(context);
//        list = daok.getDonHangChiTiet(od_id);
        if (list.size()>1){
            DonHangChiTiet donHangChiTiet = list.get(position);
            tv_madon.setText(madonhang);
            tv_oderdate.setText("Ngày đặt: \n"+sdf.format(donHangChiTiet.getOdDate()));
            tv_soluong.setText("Số lượng: "+donHangChiTiet.getQuantity());
            tv_gia.setText("Giá: "+donHangChiTiet.getPrice()+" VND");
            tv_thanhtien.setVisibility(View.GONE);
            tv_ship.setText("Tổng tiền: "+donHangChiTiet.getQuantity()*donHangChiTiet.getPrice()+" VND");
            tv_namesp_oddetail_screen.setText("Tên sản phẩm: \n"+donHangChiTiet.getName());
            try {
                String imgPath = list.get(position).getImg();  // Lấy đường dẫn tệp từ SQLite
                File imgFile = new  File(imgPath);  // Tạo đối tượng File từ đường dẫn
                if(imgFile.exists()) {
                    Uri uri = Uri.fromFile(imgFile);
                    img_product.setImageURI(uri);// Chuyển đường dẫn thành URI
                }
                else {
                    img_product.setImageResource(R.drawable.img_2);
                }
            } catch (Exception e){
                img_product.setImageResource(R.drawable.img_2);
            }
        }else {
            DonHangChiTiet donHangChiTiet = list.get(position);
            tv_oderdate.setText("Ngày đặt: "+sdf.format(donHangChiTiet.getOdDate()));
            tv_soluong.setText("Số lượng: "+donHangChiTiet.getQuantity());
            tv_gia.setText("Giá: "+donHangChiTiet.getPrice());
            tv_thanhtien.setText("Tổng tiền: "+total+" VND");
            tv_ship.setText("Phí vận chuyển: "+20000);
            tv_madon.setText(madonhang);
            try {
                String imgPath = list.get(position).getImg();  // Lấy đường dẫn tệp từ SQLite
                File imgFile = new  File(imgPath);  // Tạo đối tượng File từ đường dẫn
                if(imgFile.exists()) {
                    Uri uri = Uri.fromFile(imgFile);
                    img_product.setImageURI(uri);// Chuyển đường dẫn thành URI
                }
                else {
                    img_product.setImageResource(R.drawable.img_2);
                }
            } catch (Exception e){
                img_product.setImageResource(R.drawable.img_2);
            }
        }

        return convertView;
    }
}
