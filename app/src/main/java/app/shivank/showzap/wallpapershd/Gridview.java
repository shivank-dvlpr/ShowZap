package app.shivank.showzap.wallpapershd;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Gridview extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener, AbsListView.OnScrollListener {


    DatabaseReference databaseReference;
    Model model;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    StorageReference ref;
    GridAdapter gridAdapter;
    public static String value;
    BottomNavigationView bottomNavigationView;
    GridView gridView;
    View view;
    ArrayList<String> list;
    FloatingActionButton fabDown, fabUp;
    int scrollDetect;
    View navigatin_drawer_header;
    ImageView navHeaderImage;
    Bundle savedState;
    String favData;
    ArrayList<String> fav = new ArrayList<>();
    LinearLayout downloadWallLayout;
    LinearLayout setWallLayout;

    public Gridview() {
        // Required empty public constructor
    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_gridview, container, false);

        final LayoutInflater layoutInflater = getLayoutInflater();
        final View v = layoutInflater.inflate(R.layout.nav_header, null);
        navHeaderImage = v.findViewById(R.id.nav_header_image);


        navigatin_drawer_header = MainActivity.navigationView.getHeaderView(0);
        navHeaderImage = navigatin_drawer_header.findViewById(R.id.nav_header_image);
        navHeaderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext(), R.style.CustomDialog);
                alert.setTitle("\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "  " + "About");
                alert.setMessage("\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "  " + "ShowZap\n" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t"
                        + "\t" + "\t" + "\t" + "\t" + "\t" + "  " + " " + "V." + BuildConfig.VERSION_NAME +
                        "\n" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "Develop by Shivank Yadav");
                alert.setCancelable(true).show();
            }
        });

        gridView = view.findViewById(R.id.gridView);
        gridView.setOnScrollListener(this);

        bottomNavigationView = view.findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        fabDown = view.findViewById(R.id.fab_scroll_down);
        fabDown.setOnClickListener(this);
        fabDown.setVisibility(View.GONE);

        fabUp = view.findViewById(R.id.fab_scroll_up);
        fabUp.setOnClickListener(this);
        fabUp.setVisibility(View.GONE);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.keepSynced(true);

        return ReturnView();

    }


    @SuppressLint("RestrictedApi")
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {


    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    /*    int b= gridView.getBottom();
        int t = gridView.getTop();

        if (t == 0){
            fab.setVisibility(View.GONE);
            fabUp.setVisibility(View.GONE);
        }*/

        // Scroll Down
        if (scrollDetect < firstVisibleItem) {
            fabDown.setVisibility(View.VISIBLE);
            fabUp.setVisibility(View.GONE);
        }

        // Scroll Up
        if (scrollDetect > firstVisibleItem) {
            fabDown.setVisibility(View.GONE);
            fabUp.setVisibility(View.VISIBLE);
        }
        scrollDetect = firstVisibleItem;

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.fab_scroll_down) {
            gridView.smoothScrollToPosition(View.FOCUS_DOWN);
            fabDown.setVisibility(View.GONE);
            fabUp.setVisibility(View.GONE);
        }
        if (v.getId() == R.id.fab_scroll_up) {
            gridView.smoothScrollToPosition(0);
            fabUp.setVisibility(View.GONE);
            fabDown.setVisibility(View.GONE);
        }

    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.bottom_nav_home:
                MainActivity.DRAWER_VALUES = 0;
                fabDown.setVisibility(View.INVISIBLE);
                fabUp.setVisibility(View.INVISIBLE);
                try {
                    (MainActivity.navigationView.getCheckedItem()).setChecked(false);
                } catch (NullPointerException e) {
                    Log.i("NAVIGATION_VIEW", e.getLocalizedMessage() + "");
                }
                bottomNavigationView.getMenu().setGroupCheckable(0, true, true); // Done Bottom Navigation Bar
                ReturnView();
                break;

            case R.id.bottom_nav_trend:
                MainActivity.DRAWER_VALUES = 12;
                fabDown.setVisibility(View.INVISIBLE);
                fabUp.setVisibility(View.INVISIBLE);
                try {
                    (MainActivity.navigationView.getCheckedItem()).setChecked(false);
                } catch (NullPointerException e) {
                    Log.i("NAVIGATION_VIEW", e.getLocalizedMessage() + "");
                }
                bottomNavigationView.getMenu().setGroupCheckable(0, true, true);
                ReturnView();
                break;

            case R.id.bottom_nav_fav:

                //FancyToast.makeText(getContext(), "In Development!", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
                MainActivity.DRAWER_VALUES = 20;
                bottomNavigationView.getMenu().setGroupCheckable(0, true, true);
                ReturnView();
                break;

        }

        return true;
    }


    private View ReturnView() {

        if (MainActivity.DRAWER_VALUES == 0) { // Opening Activity

            // Toolbar Title
            MainActivity.toolbar.setTitle("ShowZap");
            changeUIColors("#000000", "#000000", "#FFFFFF", "#FFFFFF"
                    , "#000000", "#000000", R.drawable.ic_up_white, R.drawable.ic_down_white, "#000000");

            firebaseStorage = FirebaseStorage.getInstance();

            storageReference = firebaseStorage.getReference();

            ref = storageReference;

            databaseReference = FirebaseDatabase.getInstance().getReference();

            // GridAdapter gridAdapter = new GridAdapter(getActivity(), icon);

            list = new ArrayList<String>();

            databaseReference.child("HomeActivity").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    value = dataSnapshot.getValue(String.class);
                    list.add(value);

                    gridAdapter = new GridAdapter(getContext(), list);
                    gridAdapter.notifyDataSetChanged();
                    gridView.setAdapter(gridAdapter);


                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(getActivity(), ImageShow.class);

                    for (String i : list) {
                        model = new Model(list.get(position));
                        intent.putExtra("KEY", model.strings);
                    }
                    startActivity(intent);
                }
            });

        } else if (MainActivity.DRAWER_VALUES == 1) {

            MainActivity.navigationView.getCheckedItem().setChecked(true); // Done

            bottomNavigationView.getMenu().setGroupCheckable(0, false, true); // Done

            //MainActivity mainActivity = new MainActivity();
            MainActivity.toolbar.setTitle("Abstract");

            changeUIColors("#34515e", "#8eacbb", "#000000", "#000000"
                    , "#8eacbb", "#8eacbb", R.drawable.ic_up, R.drawable.ic_down, "#34515e");

            firebaseStorage = FirebaseStorage.getInstance();

            storageReference = firebaseStorage.getReference();

            ref = storageReference;

            databaseReference = FirebaseDatabase.getInstance().getReference();

            // GridAdapter gridAdapter = new GridAdapter(getActivity(), icon);

            list = new ArrayList<String>();

            databaseReference.child("Categories").child("Abstract").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    value = dataSnapshot.getValue(String.class);
                    list.add(value);

                    gridAdapter = new GridAdapter(getContext(), list);
                    gridAdapter.notifyDataSetChanged();
                    gridView.setAdapter(gridAdapter);


                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(getActivity(), ImageShow.class);

                    for (String i : list) {
                        model = new Model(list.get(position));
                        intent.putExtra("KEY", model.strings);
                    }
                    startActivity(intent);
                }
            });


        } else if (MainActivity.DRAWER_VALUES == 2) {

            MainActivity.navigationView.getCheckedItem().setChecked(true); // Done

            bottomNavigationView.getMenu().setGroupCheckable(0, false, true); // Done

            //MainActivity mainActivity = new MainActivity();

            MainActivity.toolbar.setTitle("Animals");
            changeUIColors("#005cb2", "#6ab7ff", "#000000", "#000000"
                    , "#6ab7ff", "#6ab7ff", R.drawable.ic_up, R.drawable.ic_down, "#005cb2");

            firebaseStorage = FirebaseStorage.getInstance();

            storageReference = firebaseStorage.getReference();

            ref = storageReference;

            databaseReference = FirebaseDatabase.getInstance().getReference();

            // GridAdapter gridAdapter = new GridAdapter(getActivity(), icon);

            list = new ArrayList<String>();

            databaseReference.child("Categories").child("Animals").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    value = dataSnapshot.getValue(String.class);
                    list.add(value);

                    gridAdapter = new GridAdapter(getContext(), list);
                    gridAdapter.notifyDataSetChanged();
                    gridView.setAdapter(gridAdapter);


                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(getActivity(), ImageShow.class);

                    for (String i : list) {
                        model = new Model(list.get(position));
                        intent.putExtra("KEY", model.strings);
                    }
                    startActivity(intent);
                }
            });

        } else if (MainActivity.DRAWER_VALUES == 3) {
            MainActivity.navigationView.getCheckedItem().setChecked(true); // Done

            bottomNavigationView.getMenu().setGroupCheckable(0, false, true); // Done

            //MainActivity mainActivity = new MainActivity();
            MainActivity.toolbar.setTitle("Automotive");
            changeUIColors("#4f9a94", "#b2fef7", "#000000", "#000000"
                    , "#b2fef7", "#b2fef7", R.drawable.ic_up, R.drawable.ic_down, "#4f9a94");

            firebaseStorage = FirebaseStorage.getInstance();

            storageReference = firebaseStorage.getReference();

            ref = storageReference;

            databaseReference = FirebaseDatabase.getInstance().getReference();

            // GridAdapter gridAdapter = new GridAdapter(getActivity(), icon);

            list = new ArrayList<String>();

            databaseReference.child("Categories").child("Automotive").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    value = dataSnapshot.getValue(String.class);
                    list.add(value);

                    gridAdapter = new GridAdapter(getContext(), list);
                    gridAdapter.notifyDataSetChanged();
                    gridView.setAdapter(gridAdapter);

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(getActivity(), ImageShow.class);

                    for (String i : list) {
                        model = new Model(list.get(position));
                        intent.putExtra("KEY", model.strings);
                    }
                    startActivity(intent);
                }
            });

        } else if (MainActivity.DRAWER_VALUES == 4) {
            MainActivity.navigationView.getCheckedItem().setChecked(true); // Done

            bottomNavigationView.getMenu().setGroupCheckable(0, false, true); // Done

            //MainActivity mainActivity = new MainActivity();
            MainActivity.toolbar.setTitle("Cities");
            //#c25e00 sb
            changeUIColors("#c77800", "#ffbd45", "#000000", "#000000"
                    , "#ffbd45", "#ffbd45", R.drawable.ic_up, R.drawable.ic_down, "#c77800");

            firebaseStorage = FirebaseStorage.getInstance();

            storageReference = firebaseStorage.getReference();

            ref = storageReference;

            databaseReference = FirebaseDatabase.getInstance().getReference();

            // GridAdapter gridAdapter = new GridAdapter(getActivity(), icon);

            list = new ArrayList<String>();

            databaseReference.child("Categories").child("Cities").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    value = dataSnapshot.getValue(String.class);
                    list.add(value);

                    gridAdapter = new GridAdapter(getContext(), list);
                    gridAdapter.notifyDataSetChanged();
                    gridView.setAdapter(gridAdapter);


                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(getActivity(), ImageShow.class);

                    for (String i : list) {
                        model = new Model(list.get(position));
                        intent.putExtra("KEY", model.strings);
                    }
                    startActivity(intent);
                }
            });
        } else if (MainActivity.DRAWER_VALUES == 5) {
            MainActivity.navigationView.getCheckedItem().setChecked(true); // Done

            bottomNavigationView.getMenu().setGroupCheckable(0, false, true); // Done

            //MainActivity mainActivity = new MainActivity();
            MainActivity.toolbar.setTitle("Games");
            changeUIColors("#4d2c91", "#b085f5", "#FFFFFF", "#FFFFFF"
                    , "#b085f5", "#b085f5", R.drawable.ic_up_white, R.drawable.ic_down_white, "#4d2c91");

            firebaseStorage = FirebaseStorage.getInstance();

            storageReference = firebaseStorage.getReference();

            ref = storageReference;

            databaseReference = FirebaseDatabase.getInstance().getReference();

            // GridAdapter gridAdapter = new GridAdapter(getActivity(), icon);

            list = new ArrayList<String>();

            databaseReference.child("Categories").child("Games").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    value = dataSnapshot.getValue(String.class);
                    list.add(value);

                    gridAdapter = new GridAdapter(getContext(), list);
                    gridAdapter.notifyDataSetChanged();
                    gridView.setAdapter(gridAdapter);


                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(getActivity(), ImageShow.class);

                    for (String i : list) {
                        model = new Model(list.get(position));
                        intent.putExtra("KEY", model.strings);
                    }
                    startActivity(intent);
                }
            });

        } else if (MainActivity.DRAWER_VALUES == 6) {

            MainActivity.navigationView.getCheckedItem().setChecked(true); // Done

            bottomNavigationView.getMenu().setGroupCheckable(0, false, true); // Done

            //MainActivity mainActivity = new MainActivity();
            MainActivity.toolbar.setTitle("Graphics");
            changeUIColors("#9a0007", "#ff6659", "#FFFFFF", "#FFFFFF"
                    , "#ff6659", "#ff6659", R.drawable.ic_up_white, R.drawable.ic_down_white, "#9a0007");

            firebaseStorage = FirebaseStorage.getInstance();

            storageReference = firebaseStorage.getReference();

            ref = storageReference;

            databaseReference = FirebaseDatabase.getInstance().getReference();

            // GridAdapter gridAdapter = new GridAdapter(getActivity(), icon);

            list = new ArrayList<String>();

            databaseReference.child("Categories").child("Graphics").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    value = dataSnapshot.getValue(String.class);
                    list.add(value);

                    gridAdapter = new GridAdapter(getContext(), list);
                    gridAdapter.notifyDataSetChanged();
                    gridView.setAdapter(gridAdapter);


                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(getActivity(), ImageShow.class);

                    for (String i : list) {
                        model = new Model(list.get(position));
                        intent.putExtra("KEY", model.strings);
                    }
                    startActivity(intent);
                }
            });

        } else if (MainActivity.DRAWER_VALUES == 7) {
            MainActivity.navigationView.getCheckedItem().setChecked(true); // Done

            bottomNavigationView.getMenu().setGroupCheckable(0, false, true); // Done

            //MainActivity mainActivity = new MainActivity();
            MainActivity.toolbar.setTitle("Minimal");
            changeUIColors("#bb002f", "#ff5983", "#FFFFFF", "#FFFFFF"
                    , "#ff5983", "#ff5983", R.drawable.ic_up_white, R.drawable.ic_down_white, "#bb002f");
            //changeUIColors("#5c007a", "#c158dc", "#000000", "#000000");

            firebaseStorage = FirebaseStorage.getInstance();

            storageReference = firebaseStorage.getReference();

            ref = storageReference;

            databaseReference = FirebaseDatabase.getInstance().getReference();

            // GridAdapter gridAdapter = new GridAdapter(getActivity(), icon);

            list = new ArrayList<String>();

            databaseReference.child("Categories").child("Minimalist").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    value = dataSnapshot.getValue(String.class);
                    list.add(value);

                    gridAdapter = new GridAdapter(getContext(), list);
                    gridAdapter.notifyDataSetChanged();
                    gridView.setAdapter(gridAdapter);

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(getActivity(), ImageShow.class);

                    for (String i : list) {
                        model = new Model(list.get(position));
                        intent.putExtra("KEY", model.strings);
                    }
                    startActivity(intent);
                }
            });

        } else if (MainActivity.DRAWER_VALUES == 8) {

            MainActivity.navigationView.getCheckedItem().setChecked(true); // Done

            bottomNavigationView.getMenu().setGroupCheckable(0, false, true); // Done

            //MainActivity mainActivity = new MainActivity();
            MainActivity.toolbar.setTitle("Movies");
            changeUIColors("#0088a3", "#62ebff", "#000000", "#000000"
                    , "#62ebff", "#62ebff", R.drawable.ic_up, R.drawable.ic_down, "#0088a3");

            firebaseStorage = FirebaseStorage.getInstance();

            storageReference = firebaseStorage.getReference();

            ref = storageReference;

            databaseReference = FirebaseDatabase.getInstance().getReference();

            // GridAdapter gridAdapter = new GridAdapter(getActivity(), icon);

            list = new ArrayList<String>();

            databaseReference.child("Categories").child("Movies").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    value = dataSnapshot.getValue(String.class);
                    list.add(value);

                    gridAdapter = new GridAdapter(getContext(), list);
                    gridAdapter.notifyDataSetChanged();
                    gridView.setAdapter(gridAdapter);

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(getActivity(), ImageShow.class);

                    for (String i : list) {
                        model = new Model(list.get(position));
                        intent.putExtra("KEY", model.strings);
                    }
                    startActivity(intent);
                }
            });

        } else if (MainActivity.DRAWER_VALUES == 9) {

            MainActivity.navigationView.getCheckedItem().setChecked(true); // Done

            bottomNavigationView.getMenu().setGroupCheckable(0, false, true); // Done

            //MainActivity mainActivity = new MainActivity();
            MainActivity.toolbar.setTitle("Nature");
            changeUIColors("#387002", "#99d066", "#000000", "#000000"
                    , "#99d066", "#99d066", R.drawable.ic_up, R.drawable.ic_down, "#387002");

            firebaseStorage = FirebaseStorage.getInstance();

            storageReference = firebaseStorage.getReference();

            ref = storageReference;

            databaseReference = FirebaseDatabase.getInstance().getReference();

            // GridAdapter gridAdapter = new GridAdapter(getActivity(), icon);

            list = new ArrayList<String>();

            databaseReference.child("Categories").child("Nature").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    value = dataSnapshot.getValue(String.class);
                    list.add(value);

                    gridAdapter = new GridAdapter(getContext(), list);
                    gridAdapter.notifyDataSetChanged();
                    gridView.setAdapter(gridAdapter);


                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(getActivity(), ImageShow.class);

                    for (String i : list) {
                        model = new Model(list.get(position));
                        intent.putExtra("KEY", model.strings);
                    }
                    startActivity(intent);
                }
            });

        } else if (MainActivity.DRAWER_VALUES == 10) {

            MainActivity.navigationView.getCheckedItem().setChecked(true); // Done

            bottomNavigationView.getMenu().setGroupCheckable(0, false, true); // Done

            //MainActivity mainActivity = new MainActivity();
            MainActivity.toolbar.setTitle("Quotes");
            changeUIColors("#af4448", "#ffa4a2", "#000000", "#000000"
                    , "#ffa4a2", "#ffa4a2", R.drawable.ic_up, R.drawable.ic_down, "#af4448");


            firebaseStorage = FirebaseStorage.getInstance();

            storageReference = firebaseStorage.getReference();

            ref = storageReference;

            databaseReference = FirebaseDatabase.getInstance().getReference();

            // GridAdapter gridAdapter = new GridAdapter(getActivity(), icon);

            list = new ArrayList<String>();

            databaseReference.child("Categories").child("Quotes").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    value = dataSnapshot.getValue(String.class);
                    list.add(value);

                    gridAdapter = new GridAdapter(getContext(), list);
                    gridAdapter.notifyDataSetChanged();
                    gridView.setAdapter(gridAdapter);


                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(getActivity(), ImageShow.class);

                    for (String i : list) {
                        model = new Model(list.get(position));
                        intent.putExtra("KEY", model.strings);
                    }
                    startActivity(intent);
                }
            });

        } else if (MainActivity.DRAWER_VALUES == 11) {

            MainActivity.navigationView.getCheckedItem().setChecked(true); // Done

            bottomNavigationView.getMenu().setGroupCheckable(0, false, true); // Done

            //MainActivity mainActivity = new MainActivity();
            MainActivity.toolbar.setTitle("Technology");
            changeUIColors("#1b1b1b", "#6d6d6d", "#FFFFFF", "#FFFFFF"
                    , "#6d6d6d", "#6d6d6d", R.drawable.ic_up_white, R.drawable.ic_down_white, "#1b1b1b");


            firebaseStorage = FirebaseStorage.getInstance();

            storageReference = firebaseStorage.getReference();

            ref = storageReference;

            databaseReference = FirebaseDatabase.getInstance().getReference();

            // GridAdapter gridAdapter = new GridAdapter(getActivity(), icon);

            list = new ArrayList<String>();

            databaseReference.child("Categories").child("Technology").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    value = dataSnapshot.getValue(String.class);
                    list.add(value);

                    gridAdapter = new GridAdapter(getContext(), list);
                    gridAdapter.notifyDataSetChanged();
                    gridView.setAdapter(gridAdapter);


                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(getActivity(), ImageShow.class);

                    for (String i : list) {
                        model = new Model(list.get(position));
                        intent.putExtra("KEY", model.strings);
                    }
                    startActivity(intent);
                }
            });

        } else if (MainActivity.DRAWER_VALUES == 12) { // Trend

            //MainActivity mainActivity = new MainActivity();
            MainActivity.toolbar.setTitle("Trend");
            changeUIColors("#000000", "#000000", "#b0ff57", "#b0ff57"
                    , "#000000", "#000000", R.drawable.ic_up_green, R.drawable.ic_down_green, "#000000");

            firebaseStorage = FirebaseStorage.getInstance();

            storageReference = firebaseStorage.getReference();

            ref = storageReference;

            databaseReference = FirebaseDatabase.getInstance().getReference();

            // GridAdapter gridAdapter = new GridAdapter(getActivity(), icon);

            list = new ArrayList<String>();

            databaseReference.child("Trend").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    value = dataSnapshot.getValue(String.class);
                    list.add(value);

                    gridAdapter = new GridAdapter(getContext(), list);
                    gridAdapter.notifyDataSetChanged();
                    gridView.setAdapter(gridAdapter);


                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(getActivity(), ImageShow.class);

                    for (String i : list) {
                        model = new Model(list.get(position));
                        intent.putExtra("KEY", model.strings);
                    }
                    startActivity(intent);
                }
            });

        } else if (MainActivity.DRAWER_VALUES == 20) {

            ImageShow imageShow = new ImageShow();

            Log.d("StoreData", getResult().toString());

            gridAdapter = new GridAdapter(getContext(), getResult());
            gridAdapter.notifyDataSetChanged();
            gridView.setAdapter(gridAdapter);

           /* ImageShow imageShow = new ImageShow();
            for (int i = 0; i < imageShow.stringArrayList.size(); i++) {
                ImageShow.favData = String.valueOf(imageShow.stringArrayList.get(i));
            }*/
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(getActivity(), ImageShow.class);

                    ImageShow imageShow = new ImageShow();
                    for (String i : imageShow.stringArrayList) {
                        model = new Model(imageShow.stringArrayList.get(position));
                        intent.putExtra("KEY", model.strings);
                    }
                    startActivity(intent);
                }
            });
        }
        return view;
    }

    private ArrayList<String> getResult() {

        Databasehandler db = new Databasehandler(getContext());


        ArrayList<String> list = new ArrayList<String>();

        Cursor c = db.allData();
        while (c.moveToNext()) {
            String data = c.getString(0);
            list.add(data);
        }
        c.close();

        db.close();
        return list;
    }


  /*  private Bundle saveState() {
        Bundle state = new Bundle();
        state.putSerializable("Fav", ImageShow.favData);
        return state;
    }*/

    private void changeUIColors(String statusBarColor, String toolbarColor, String toolbarTextColor, String hamburgerColor
            , String fabUpBgColor, String fabDownBgColor, int fabUpImgSrc, int fabDownImgSrc, String nav_header_color) {

        final LayoutInflater layoutInflater = getLayoutInflater();
        final View v = layoutInflater.inflate(R.layout.activity_download_wall, null);
        downloadWallLayout = (LinearLayout) v.findViewById(R.id.downloadWallLayout);

        final LayoutInflater layoutInflater1 = getLayoutInflater();
        final View v1 = layoutInflater.inflate(R.layout.activity_setwall, null);
        setWallLayout = (LinearLayout) v1.findViewById(R.id.set_wallpaper_layout);

        downloadWallLayout.setBackgroundColor(Color.parseColor(toolbarColor));
        setWallLayout.setBackgroundColor((Color.parseColor(toolbarColor)));


        //StatusBar Color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            MainActivity.window.setStatusBarColor(Color.parseColor(statusBarColor));
        }
        //Toolbar Color
        MainActivity.toolbar.setBackgroundColor(Color.parseColor(toolbarColor));

        //Toolbar TextColor
        MainActivity.toolbar.setTitleTextColor(Color.parseColor(toolbarTextColor));

        //Hamburger IconColor (Navigation Menu)
        MainActivity.toggle.getDrawerArrowDrawable().setColor(Color.parseColor(hamburgerColor));

        //Floating Action Button Colors
        fabUp.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(fabUpBgColor)));
        fabDown.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(fabDownBgColor)));
        fabUp.setImageResource(fabUpImgSrc);
        fabDown.setImageResource(fabDownImgSrc);

        navigatin_drawer_header.setBackgroundColor(Color.parseColor(nav_header_color));

        MainActivity.navigationView.setBackgroundColor(Color.parseColor(statusBarColor));

        setNavDrawerItemIconColors(Color.GREEN, "#FFFFFF");

    }

    public void setNavDrawerItemIconColors(int color, String dfColor) {

        int navDefaultTextColor = Color.parseColor(dfColor);
        int navDefaultIconColor = Color.parseColor(dfColor);

        ColorStateList navMenuTextList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{android.R.attr.state_enabled},
                        new int[]{android.R.attr.state_pressed},
                        new int[]{android.R.attr.state_focused},
                        new int[]{android.R.attr.state_pressed}
                },
                new int[]{
                        color,
                        navDefaultTextColor,
                        navDefaultTextColor,
                        navDefaultTextColor,
                        navDefaultTextColor
                }
        );


        ColorStateList navMenuIconList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{android.R.attr.state_enabled},
                        new int[]{android.R.attr.state_pressed},
                        new int[]{android.R.attr.state_focused},
                        new int[]{android.R.attr.state_pressed}
                },
                new int[]{
                        color,
                        navDefaultIconColor,
                        navDefaultIconColor,
                        navDefaultIconColor,
                        navDefaultIconColor,
                }
        );

        MainActivity.navigationView.setItemTextColor(navMenuTextList);
        MainActivity.navigationView.setItemIconTintList(navMenuIconList);
    }

}
