package cl.anpetrus.prueba4.views.main.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cl.anpetrus.prueba4.R;
import cl.anpetrus.prueba4.adapters.CharactersAdapter;
import cl.anpetrus.prueba4.listeners.ActionFragmentListener;
import cl.anpetrus.prueba4.models.Character;
import cl.anpetrus.prueba4.models.Wrapper;
import cl.anpetrus.prueba4.models.WrapperData;
import cl.anpetrus.prueba4.network.GetCharacters;
import cl.anpetrus.prueba4.utils.CharactersQuery;
import cl.anpetrus.prueba4.views.main.data.CharacterActivity;

public class CharactersFragment extends Fragment implements ActionFragmentListener {

    private static CharactersFragment fragment;
    private CharactersAdapter charactersAdapter;
    private RecyclerView recyclerView;
    private boolean pendingRequest = false;
    private boolean firstEjecution = true;
    private int totalElements = 0;
    private GridLayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;


    public CharactersFragment() {
        // Required empty public constructor
    }

    public static CharactersFragment newInstance() {
        fragment = new CharactersFragment();
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

        totalElements = 0;
        firstEjecution = true;

        swipeRefreshLayout = view.findViewById(R.id.reloadSrl);

        recyclerView = view.findViewById(R.id.charactersRv);
        recyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, GridSpacingItemDecoration.dpToPx(getResources(), 1), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        getData(0, 30);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = mLayoutManager.findLastVisibleItemPosition();
                int total = mLayoutManager.getItemCount();
                Log.d("SCROLL", "position: " + position + " total: " + total);
                if (totalElements > total) {
                    if (total - 15 < position) {
                        if (!pendingRequest) {
                            getData(total, 30);
                        }

                    }
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                charactersAdapter.clear();
                getData(0, 30);
            }
        });
    }

    private void getData(int start, int end) {
        CharactersQuery spider = CharactersQuery
                .Builder
                .create()
                .withOffset(start)
                .withLimit(end)
                .build();
        new BackgroundCharacters().execute(spider);
    }

    @Override
    public void clicked(Object object) {
        swipeRefreshLayout.setRefreshing(false);
        Intent intent = new Intent(getContext(), CharacterActivity.class);
        intent.putExtra(CharacterActivity.KEY_CHARACTER, (Character) object);
        startActivity(intent);
    }

    private class BackgroundCharacters extends GetCharacters {

        @Override
        protected void onPreExecute() {
            swipeRefreshLayout.setRefreshing(true);
            if (!firstEjecution)
                pendingRequest = true;

        }

        @Override
        protected void onPostExecute(Wrapper<Character> wrapper) {
            if (wrapper != null) {
                WrapperData<Character> characterWrapperData = wrapper.getData();
                totalElements = wrapper.getData().getTotal();
                if (firstEjecution) {
                    charactersAdapter = new CharactersAdapter(getContext(), characterWrapperData.getResults(), fragment);
                    recyclerView.setAdapter(charactersAdapter);
                    firstEjecution = false;
                } else {
                    charactersAdapter.update(characterWrapperData);
                    pendingRequest = false;
                }
            }
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
