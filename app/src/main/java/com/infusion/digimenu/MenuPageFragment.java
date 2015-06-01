package com.infusion.digimenu;

/**
 * Created by ali on 2015-05-31.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.infusion.digimenu.model.MenuCategory;
import com.infusion.digimenu.model.MenuItem;

import java.text.NumberFormat;

/**
 * A placeholder fragment containing a simple view.
 */
public class MenuPageFragment extends Fragment {
    private MenuCategory mMenuCategory;

    public static MenuPageFragment newInstance(MenuCategory menuCategory) {
        MenuPageFragment result = new MenuPageFragment();
        result.mMenuCategory = menuCategory;
        return result;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.fragment_menu, container, false);

        ListView menuListView = (ListView) result.findViewById(R.id.menuItemListView);
        menuListView.setAdapter(new MenuItemArrayAdapter(getActivity(), mMenuCategory.items));
        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // determine the item clicked - navigate to detail page
                Bundle bundle = new Bundle();
                bundle.putSerializable(MenuItemActivity.BUNDLE_MENU_ITEM, mMenuCategory.items[position]);

                Intent intent = new Intent(getActivity(), MenuItemActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        return result;
    }

    public class MenuItemArrayAdapter extends ArrayAdapter<MenuItem> {
        public MenuItemArrayAdapter(Context context, MenuItem[] objects) {
            super(context, R.layout.listview_menu_item, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View result = convertView;
            if (result == null) {
                // no view to recycle - create one from scatch
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                result = inflater.inflate(R.layout.listview_menu_item, parent, false);
            }

            MenuItem menuItem = mMenuCategory.items[position];

            // apply item name
            TextView nameTextView = (TextView) result.findViewById(R.id.nameTextView);
            nameTextView.setText(menuItem.name);

            // adjust spicy icon
            View spicyImageView = result.findViewById(R.id.spicyImageView);
            spicyImageView.setVisibility(menuItem.isSpicy ? View.VISIBLE : View.GONE);

            // apply price
            TextView priceTextView = (TextView) result.findViewById(R.id.priceTextView);
            priceTextView.setText(NumberFormat.getCurrencyInstance().format(menuItem.price));

            return result;
        }
    }
}