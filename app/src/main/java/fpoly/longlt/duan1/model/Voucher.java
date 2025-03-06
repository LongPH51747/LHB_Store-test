package fpoly.longlt.duan1.model;

public class Voucher {
    int id_vc;
    int discount_price;
    int status;
    String start_date;
    String end_date;
    String code;
    int dieukien;

    public int getDieukien() {
        return dieukien;
    }

    public void setDieukien(int dieukien) {
        this.dieukien = dieukien;
    }

    public Voucher(int id_vc, int discount_price, int status, String start_date, String end_date, String code, int dieukien) {
        this.id_vc = id_vc;
        this.discount_price = discount_price;
        this.status = status;
        this.start_date = start_date;
        this.end_date = end_date;
        this.code = code;
        this.dieukien = dieukien;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public int getId_vc() {
        return id_vc;
    }

    public void setId_vc(int id_vc) {
        this.id_vc = id_vc;
    }

    public int getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(int discount_price) {
        this.discount_price = discount_price;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Voucher() {
    }

}
