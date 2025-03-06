package fpoly.longlt.duan1.model;

public class ThongKe {
    String date;
    int totalMoney;
    int soLuongBanRa;

    public int getSoLuongBanRa() {
        return soLuongBanRa;
    }

    public void setSoLuongBanRa(int soLuongBanRa) {
        this.soLuongBanRa = soLuongBanRa;
    }

    public ThongKe(String date, int totalMoney) {
        this.date = date;
        this.totalMoney = totalMoney;
    }

    public ThongKe() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }
}
