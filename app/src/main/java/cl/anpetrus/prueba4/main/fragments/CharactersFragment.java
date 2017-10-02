package cl.anpetrus.prueba4.main.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cl.anpetrus.prueba4.R;
import cl.anpetrus.prueba4.adapters.CharactersAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CharactersFragment extends Fragment {

    private CharactersAdapter charactersAdapter;


    public CharactersFragment() {
        // Required empty public constructor
    }

    public static CharactersFragment newInstance(){
        CharactersFragment fragment = new CharactersFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_characters, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.charactersRv);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        charactersAdapter = new CharactersAdapter(getContext());
        recyclerView.setAdapter(charactersAdapter);

    }
}
