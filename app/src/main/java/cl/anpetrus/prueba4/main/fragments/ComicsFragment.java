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
import cl.anpetrus.prueba4.adapters.ComicsAdapter;
import cl.anpetrus.prueba4.models.Comic;
import cl.anpetrus.prueba4.models.Wrapper;
import cl.anpetrus.prueba4.models.WrapperData;
import cl.anpetrus.prueba4.network.GetComics;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComicsFragment extends Fragment {

    private ComicsAdapter comicsAdapter;
    private RecyclerView recyclerView;

    public ComicsFragment() {
        // Required empty public constructor
    }

    public static ComicsFragment newInstance(){
        ComicsFragment fragment = new ComicsFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comics, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.comicsRv);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        CharactersQuery spider = CharactersQuery
                .Builder
                .create()
                .withOffset(0)
                .withLimit(10)
                .build();
        new BackgroundComics().execute(spider);


    }

    private class BackgroundComics extends GetComics {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(Wrapper<Comic> wrapper) {
            if(wrapper!=null){
                WrapperData<Comic> comicWrapperData = wrapper.getData();

                comicsAdapter = new ComicsAdapter(getContext(),comicWrapperData.getResults());
                recyclerView.setAdapter(comicsAdapter);
            }
        }
    }

}
