package fpoly.longlt.duan1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import fpoly.longlt.duan1.R;

public class ThongKeAdapter extends BaseAdapter {
    private Context context;
    private List<HashMap<String, Object>> statisticsList;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    public ThongKeAdapter(Context context, List<HashMap<String, Object>> statisticsList) {
        this.context = context;
        this.statisticsList = statisticsList;
    }

    @Override
    public int getCount() {
        return statisticsList!=null?statisticsList.size():0;
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
        convertView = LayoutInflater.from(context).inflate(R.layout.list_item_thongke, null);
        TextView tv_soluong, tv_month, tv_daonhthu;
        tv_soluong = convertView.findViewById(R.id.tv_soluong_banra);
        tv_month = convertView.findViewById(R.id.tv_thang);
        tv_daonhthu = convertView.findViewById(R.id.tv_doanhthu);

        HashMap<String, Object> data = statisticsList.get(position);
        tv_soluong.setText("Số lượng bán ra: "+data.get("total_quantity"));
        tv_month.setText("Tháng: "+data.get("month_year"));
        tv_daonhthu.setText("Doanh thu: " + String.format("%,d", (int) data.get("total_revenue")));

        return convertView;
    }
}
