package lagecy.live.desh.com.mrdfoodmobilev2;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by MANDELACOMP7 on 2018/02/14.
 */

public class OrdersArrayAdapter extends BaseAdapter {

    private List<OrderInfo> orders;
    private Context context;

    public OrdersArrayAdapter(Context context, List<OrderInfo> orders)
    {
        this.orders = orders;
        this.context = context;

    }
    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final OrderInfo order = orders.get(i);

        if (view == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.orders_layout, null);
        }

       final TextView tvOrderId = view.findViewById(R.id.tvOrdId);
        final TextView tvOrderDate = view.findViewById(R.id.tvOrderDate);
        final TextView tvTotal = view.findViewById(R.id.tvTotal);
        final TextView tvItemCount = view.findViewById(R.id.tvitemCount);
        final TextView tvStatus = view.findViewById(R.id.tvStatus);

        tvOrderId.setText(order.getOrderId());
        tvOrderDate.setText(order.getOrderDate());
        tvTotal.setText("R"+order.getTotal());
        tvStatus.setText(order.getStatus());
        tvItemCount.setText(" "+order.getCartItems().size());

        return view;
    }
}
