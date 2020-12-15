package dadm.scaffold.counter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import dadm.scaffold.BaseFragment;
import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;


public class SelectShipFragment extends BaseFragment {



    public SelectShipFragment() {
        // Required empty public constructor
    }

    @Override
    public  boolean onBackPressed(){
        ((ScaffoldActivity)getActivity()).navigateToFragment(new MainMenuFragment(),"");
        return true;
    }

    private FragmentManager getSupportFragmentManager() {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_ship, container, false);


    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button blueShip = (Button)getView().findViewById(R.id.blueShip);
        Button blueAnotherShip = (Button)getView().findViewById(R.id.blueAnotherShip);
        blueShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScaffoldActivity)getActivity()).navigateToFragment(new GameFragment(),"0");
            }
        });
        blueAnotherShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScaffoldActivity)getActivity()).navigateToFragment(new GameFragment(),"1");
            }
        });

    }
}