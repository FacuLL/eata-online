package com.EATA.EataOnline;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tab1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab1 extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button bt1;
    private ImageButton bt2;
    private View view;
    private boolean audio = false;

    private MediaPlayer mp;

    private OnFragmentInteractionListener mListener;

    public Tab1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab1.
     */
    // TODO: Rename and change types and number of parameters
    private static Tab1 newInstance(String param1, String param2) {
        Tab1 fragment = new Tab1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }




    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_tab1, container, false);
        bt1 = (Button) view.findViewById(R.id.btCerrarSesion);
        bt1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                goLoginScreen();
            }
        });

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        bt2 = (ImageButton) view.findViewById(R.id.btRadio);
        bt2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!audio) {
                    mp = new MediaPlayer();
                    try {
                        mp.setDataSource("https://tonolilo.es/am3/Meowing_Cat_Notification.mp3");
                        mp.prepare();
                        mp.start();
                        bt2.setImageResource(android.R.drawable.ic_media_pause);
                        audio = true;
                    }
                    catch(IOException e){
                        Toast t=Toast.makeText(getActivity(),"No estamos al aire en este momento", Toast.LENGTH_SHORT);
                        t.show();
                        bt2.setImageResource(android.R.drawable.ic_media_play);
                        audio = false;
                    }


                }
                else {
                    mp.stop();
                    mp = null;
                    bt2.setImageResource(android.R.drawable.ic_media_play);
                    audio = false;
                }
            }
        });

        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void logOut() {
        LoginManager.getInstance().logOut();
        goLoginScreen();
    }

    public void goLoginScreen() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}
