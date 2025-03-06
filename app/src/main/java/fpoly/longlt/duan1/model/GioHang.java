package fpoly.longlt.duan1.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class GioHang implements Serializable {
    int chitietsp_id;
    private int user_id;
    private int status;
    private int cart_id;
    private int sp_id;
    private int quantity;
    private int is_selected;
    private int price;
    private int total_price;
    private String imgPath;
    private String color;
    private String size;
    private String name;

//    protected GioHang(Parcel in) {
//        chitietsp_id = in.readInt();
//        user_id = in.readInt();
//        cart_id = in.readInt();
//        quantity = in.readInt();
//        is_selected = in.readInt();
//        price = in.readInt();
//        imgPath = in.readString();
//        color = in.readString();
//        size = in.readString();
//        name = in.readString();
//        status = in.readInt();
//        total_price = in.readInt();
//    }
//
//    public static final Creator<GioHang> CREATOR = new Creator<GioHang>() {
//        @Override
//        public GioHang createFromParcel(Parcel in) {
//            return new GioHang(in);
//        }
//
//        @Override
//        public GioHang[] newArray(int size) {
//            return new GioHang[size];
//        }
//    };

    public int getChitietsp_id() {
        return chitietsp_id;
    }

    public void setChitietsp_id(int chitietsp_id) {
        this.chitietsp_id = chitietsp_id;
    }



    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public int getIs_selected() {
        return is_selected;
    }

    public void setIs_selected(int is_selected) {
        this.is_selected = is_selected;
    }




    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getImgPath() {
        return imgPath;
    }


    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GioHang() {
    }


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getSp_id() {
        return sp_id;
    }

    public void setSp_id(int sp_id) {
        this.sp_id = sp_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//
////
////    @Override
////    public void writeToParcel(@NonNull Parcel dest, int flags) {
////        dest.writeInt(cart_id);
////        dest.writeInt(user_id);
////        dest.writeInt(chitietsp_id);
////        dest.writeInt(quantity);
////        dest.writeInt(price);
////        dest.writeString(imgPath);
////        dest.writeString(color);
////        dest.writeString(size);
////        dest.writeString(name);
////        dest.writeInt(is_selected);
////        dest.writeInt(status);
////        dest.writeInt(total_price);
////    }
}
