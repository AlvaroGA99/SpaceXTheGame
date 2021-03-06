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


public class EndGameFragment extends BaseFragment  {






    public EndGameFragment() {

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
        return inflater.inflate(R.layout.fragment_end_game, container, false);


    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = (TextView)getView().findViewById(R.id.finalText);
        Bundle bundle  = this.getArguments();
        Button boton1 = (Button)getView().findViewById(R.id.menuButton);
        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScaffoldActivity)getActivity()).navigateToFragment(new MainMenuFragment(),"");            }
        });
        Button boton2 = (Button)getView().findViewById(R.id.playagainButton);
        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScaffoldActivity)getActivity()).navigateToFragment(new GameFragment(),"");
            }
        });
        textView.setText(bundle.getString("data"));


    }
}