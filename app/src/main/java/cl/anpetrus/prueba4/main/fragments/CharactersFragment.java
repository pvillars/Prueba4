package cl.anpetrus.prueba4.main.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cl.anpetrus.prueba4.CharactersQuery;
import cl.anpetrus.prueba4.R;
import cl.anpetrus.prueba4.adapters.CharactersAdapter;
import cl.anpetrus.prueba4.models.Character;
import cl.anpetrus.prueba4.models.Wrapper;
import cl.anpetrus.prueba4.models.WrapperData;
import cl.anpetrus.prueba4.network.GetCharacters;

/**
 * A simple {@link Fragment} subclass.
 */
public class CharactersFragment extends Fragment {

    private CharactersAdapter charactersAdapter;
    private RecyclerView recyclerView;


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

        recyclerView = view.findViewById(R.id.charactersRv);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        CharactersQuery spider = CharactersQuery
                .Builder
                .create()
                .withOffset(0)
                .withLimit(10)
                .build();
        new BackgroundUF().execute(spider);


    }

    private class BackgroundUF extends GetCharacters {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(Wrapper<Character> wrapper) {
            if(wrapper!=null){
                //TextView textView = (TextView) findViewById(R.id.ufTv);
                //textView.setText(String.valueOf(wrapper.getSerie()[0].getValor()));

                WrapperData<Character> characterWrapperData = wrapper.getData();


                charactersAdapter = new CharactersAdapter(getContext(),characterWrapperData.getResults());
                recyclerView.setAdapter(charactersAdapter);
            }
        }
    }
}
