package es.unizar.eina.T202_camping.ui;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import es.unizar.eina.T202_camping.R;

class ReservaViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
   private final TextView mReservaItemView;



    private ReservaViewHolder(View itemView) {
        super(itemView);
        mReservaItemView = itemView.findViewById(R.id.textView);

        itemView.setOnCreateContextMenuListener(this);
    }

    public void bind(String text) {
        mReservaItemView.setText(text);
    }

    static ReservaViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new ReservaViewHolder(view);
    }


    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        //super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, Reservapad.DELETE_ID, Menu.NONE, R.string.menu_delete);
        menu.add(Menu.NONE, Reservapad.EDIT_ID, Menu.NONE, R.string.menu_edit);
        menu.add(Menu.NONE, Reservapad.SEND_ID, Menu.NONE, R.string.menu_send);
    }
}
