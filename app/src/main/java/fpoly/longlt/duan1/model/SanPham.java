package fpoly.longlt.duan1.model;

public class SanPham {
    private int spId;
    private String tenSp;
    private String img;
    private int status;
    private int price;
    private String description;
    ///
    private String colors;
    private String size;
    private int soLuong;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

// Constructor, Getter và Setter

    public SanPham() {
    }

    public SanPham(int spId, String tenSp, String img, int status, int price, String description, String colors, String size, int soLuong) {
        this.spId = spId;
        this.tenSp = tenSp;
        this.img = img;
        this.status = status;
        this.price = price;
        this.description = description;
        this.colors = colors;
        this.size = size;
        this.soLuong = soLuong;
    }

    public int getSpId() {
        return spId;
    }

    public void setSpId(int spId) {
        this.spId = spId;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
