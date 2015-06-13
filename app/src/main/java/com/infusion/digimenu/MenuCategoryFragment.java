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
public class MenuCategoryFragment extends Fragment {

    private static final String KEY_CATEGORY = "com.infusion.digimenu.MenuCategoryFragment.KEY_CATEGORY";

    private MenuCategory mMenuCategory;

    public MenuCategoryFragment() {
        super();
    }

    public static MenuCategoryFragment createNewInstance(MenuCategory menuCategory) {
        MenuCategoryFragment result = new MenuCategoryFragment();
        result.mMenuCategory = menuCategory;

        return result;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //If we have saved data, restored them
        if (null != savedInstanceState) {
            mMenuCategory = (MenuCategory) savedInstanceState.getSerializable(KEY_CATEGORY);
        }

        if (mMenuCategory == null) {
            // no menu category provided - guard
            return null;
        }

        View result = inflater.inflate(R.layout.fragment_menu, container, false);

        // adapt each menu item to a row in the list view
        ListView menuListView = (ListView) result.findViewById(R.id.menuItemListView);
        menuListView.setAdapter(new MenuItemArrayAdapter(getActivity(), mMenuCategory.items));
        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // determine which item the user clicked - navigate to a details activity
                Bundle bundle = new Bundle();
                bundle.putSerializable(MenuItemActivity.BUNDLE_MENU_ITEM, mMenuCategory.items[position]);

                Intent intent = new Intent(getActivity(), MenuItemActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);
                //TODO: (8) Add a custom animation for the activity transition.
                // Make the new activity (MenuItemActivity) slide in from the bottom while existing activity
                // slides out to the top.
                // Hint - check for a method in the Activity to override the activity transitions
                // Hint - Look for custom animation XML files in /res/anim folder
            }
        });

        return result;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //Need to persist local data here. When orientation changes, this data has to be restored
        outState.putSerializable(KEY_CATEGORY, mMenuCategory);
        super.onSaveInstanceState(outState);
    }

    public class MenuItemArrayAdapter extends ArrayAdapter<MenuItem> {
        public MenuItemArrayAdapter(Context context, MenuItem[] objects) {
            super(context, R.layout.listview_menu_item, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View result = convertView;
            if (result == null) {
                // no view to recycle - create a new one
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                result = inflater.inflate(R.layout.listview_menu_item, parent, false);
            }

            MenuItem menuItem = mMenuCategory.items[position];

            // apply item name
            TextView nameTextView = (TextView) result.findViewById(R.id.nameTextView);
            nameTextView.setText(menuItem.name);

            // apply price
            TextView priceTextView = (TextView) result.findViewById(R.id.priceTextView);
            priceTextView.setText(NumberFormat.getCurrencyInstance().format(menuItem.price));

            // adjust spicy icon
            View spicyImageView = result.findViewById(R.id.spicyImageView);
            spicyImageView.setVisibility(menuItem.isSpicy ? View.VISIBLE : View.GONE);

            return result;
        }
    }
}