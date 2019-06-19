package app.shivank.showzap.wallpapershd;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fav_Fragment extends Fragment {

    View view;
    GridView gridView;
    GridAdapter gridAdapter;

    public Fav_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fav_fragment, container, false);

      /*  gridView = view.findViewById(R.id.gridViewFav);
        gridAdapter = new GridAdapter(getContext(), ImageShow.stringArrayList);
        gridAdapter.notifyDataSetChanged();
        gridView.setAdapter(gridAdapter);
*/
        return view;

    }

}
