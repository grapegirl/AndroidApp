package com.kiki.act.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.kiki.act.FileUploadActivity;
import com.kiki.act.HTTPUrlConnectionActivity;
import com.kiki.act.DrawerLayoutAct;
import com.kiki.act.SocketTestMainActivity;
import com.kiki.act.WifiMainActivity;
import com.kiki.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class UIFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.main_tab1) Button mButton01;

    private Unbinder unbinder;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_ui, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    @OnClick({R.id.menu1, R.id.menu2, R.id.menu3, R.id.menu4, R.id.menu5,
    R.id.main_tab1, R.id.main_tab2, R.id.main_tab3, R.id.main_tab4, R.id.main_tab5,
    R.id.main_tab6, R.id.main_tab7, R.id.main_tab8, R.id.main_tab9, R.id.main_tab10,
    R.id.button_third})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_third:
                NavHostFragment.findNavController(UIFragment.this)
                        .navigate(R.id.action_UIFragment_to_FirstFragment);
                break;
            case R.id.main_tab1:
                Toast.makeText(getContext(), "메인1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_tab2:
                Toast.makeText(getContext(), "메인2", Toast.LENGTH_SHORT).show();

                break;
            case R.id.main_tab3:
                Toast.makeText(getContext(), "메인3", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_tab4:
                Toast.makeText(getContext(), "메인4", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_tab5:
                Toast.makeText(getContext(), "메인5", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu1:
                Intent intent = new Intent(getContext(), DrawerLayoutAct.class);
                getContext().startActivity(intent);
                break;
            case R.id.menu2:
                intent = new Intent(getContext(), FileUploadActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.menu3:
                intent = new Intent(getContext(), HTTPUrlConnectionActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.menu4:
                intent = new Intent(getContext(), SocketTestMainActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.menu5:
                intent = new Intent(getContext(), WifiMainActivity.class);
                getContext().startActivity(intent);
                break;

        }
    }
}
