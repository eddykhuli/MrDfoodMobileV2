package lagecy.live.desh.com.mrdfoodmobilev2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MANDELACOMP7 on 2018/01/22.
 */

public class MenuCustomAdapter  extends BaseAdapter{

    private List<Product> listProducts;
    private Context context;
    public ArrayList<Double> globalTotal =  new ArrayList<>();

    public MenuCustomAdapter(Context context,List<Product> listProducts) {
        this.context = context;
        this.listProducts = listProducts;
    }

    @Override
    public int getCount() {
        return listProducts.size();
    }

    @Override
    public Product getItem(int position) {
        return listProducts.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View row;
        final MenuHolder listViewHolder;
        if(view == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.menu_list_row,viewGroup,false);
            listViewHolder = new MenuHolder();
            listViewHolder.tvProductName = row.findViewById(R.id.tvProductName);
            listViewHolder.tvDescription = row.findViewById(R.id.tvDescription);
            listViewHolder.tvPrice = row.findViewById(R.id.tvPrice);
            listViewHolder.btnPlus = row.findViewById(R.id.ib_addnew);
            listViewHolder.tvQuantity = row.findViewById(R.id.tvQuantity);
            listViewHolder.btnMinus = row.findViewById(R.id.ib_remove);
            listViewHolder.tvItemTot= row.findViewById(R.id.tvItemTot);
            row.setTag(listViewHolder);
        }
        else
        {
            row= view;
            listViewHolder= (MenuHolder) row.getTag();
        }
        final Product products = getItem(i);
        products.setQuantity(1);

        listViewHolder.tvProductName.setText(products.getProductName());
        listViewHolder.tvDescription.setText(products.getDescription());
        listViewHolder.tvPrice.setText("R"+products.getPrice());
        listViewHolder.tvQuantity.setText(products.getQuantity()+"");
        listViewHolder.tvItemTot.setText("R"+products.getPrice() * products.getQuantity());
        listViewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                updateQuantity(i,listViewHolder.tvQuantity,1);
                 double itemTot = products.getPrice()* products.getQuantity();
                double roundOff = Math.round(itemTot * 100.00) / 100.00;

                SessionSingleton.getInstance().itemTot.add(itemTot);
                String str;
                str = String.format("%.2f",itemTot);
                listViewHolder.tvItemTot.setText(str);

            }
        });
        listViewHolder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                updateQuantity(i,listViewHolder.tvQuantity,-1);
                double itemTot = products.getPrice()* products.getQuantity();
                SessionSingleton.getInstance().itemTot.add(itemTot);
                String str;

                str = String.format("%.2f",itemTot);
                listViewHolder.tvItemTot.setText(str);

            }
        });

        return row;
    }

    private void updateQuantity(int position, EditText edTextQuantity, int value) {

        Product products = getItem(position);

        int quantity;
        if(value > 0)
        {
            quantity = Integer.parseInt( edTextQuantity.getText().toString())+ 1;
            products.setQuantity(quantity);
        }
        else
        {
            if(products.getQuantity() > 0)
            {
                quantity = Integer.parseInt( edTextQuantity.getText().toString()) - 1;
                //listViewHolder.tvItemTot.setText("R"+);
                products.setQuantity(quantity);
            }

        }
        edTextQuantity.setText(""+products.getQuantity());
    }

}
