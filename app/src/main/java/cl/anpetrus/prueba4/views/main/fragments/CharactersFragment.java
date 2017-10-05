package cl.anpetrus.prueba4.views.main.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private boolean pendingRequest = false;
    private boolean firstEjecution = true;
    private int totalElements = 0;


    public CharactersFragment() {
        // Required empty public constructor
    }

    public static CharactersFragment newInstance() {
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

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        CharactersQuery spider = CharactersQuery
                .Builder
                .create()
                .withOffset(0)
                .withLimit(10)
                .build();
        new BackgroundCharacters().execute(spider);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = linearLayoutManager.findLastVisibleItemPosition();
                int total = linearLayoutManager.getItemCount();
                Log.d("SCROLL", "position: " + position + " total: " + total);
                if (totalElements > total) {
                    if (total - 4 < position) {
                        if (!pendingRequest) {
                            CharactersQuery spider = CharactersQuery
                                    .Builder
                                    .create()
                                    .withOffset(total)
                                    .withLimit(10)
                                    .build();
                            new BackgroundCharacters().execute(spider);
                        }

                    }
                }
            }
        });


    }

    private class BackgroundCharacters extends GetCharacters {

        @Override
        protected void onPreExecute() {
            if (!firstEjecution)
                pendingRequest = true;

        }

        @Override
        protected void onPostExecute(Wrapper<Character> wrapper) {
            if (wrapper != null) {
                WrapperData<Character> characterWrapperData = wrapper.getData();
                totalElements = wrapper.getData().getTotal();
                if (firstEjecution) {
                    charactersAdapter = new CharactersAdapter(getContext(), characterWrapperData.getResults());
                    recyclerView.setAdapter(charactersAdapter);
                    firstEjecution = false;
                } else {
                    charactersAdapter.update(characterWrapperData);
                    pendingRequest = false;
                }


            }
        }
    }
}
