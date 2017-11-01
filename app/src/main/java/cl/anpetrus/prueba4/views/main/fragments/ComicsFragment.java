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
import cl.anpetrus.prueba4.adapters.ComicsAdapter;
import cl.anpetrus.prueba4.listeners.ActionFragmentListener;
import cl.anpetrus.prueba4.models.Comic;
import cl.anpetrus.prueba4.models.Wrapper;
import cl.anpetrus.prueba4.models.WrapperData;
import cl.anpetrus.prueba4.network.GetComics;
import cl.anpetrus.prueba4.utils.CharactersQuery;
import cl.anpetrus.prueba4.views.main.data.ComicActivity;

public class ComicsFragment extends Fragment implements ActionFragmentListener {

    private static ComicsFragment fragment;
    private ComicsAdapter comicsAdapter;
    private RecyclerView recyclerView;
    private GridLayoutManager mLayoutManager;
    private boolean pendingRequest = false;
    private boolean firstEjecution = true;
    private int totalElements = 0;
    private SwipeRefreshLayout swipeRefreshLayout;

    public ComicsFragment() {
        // Required empty public constructor
    }

    public static ComicsFragment newInstance() {
        fragment = new ComicsFragment();
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

        swipeRefreshLayout = view.findViewById(R.id.reloadSrl);

        mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, GridSpacingItemDecoration.dpToPx(getResources(), 4), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        getData(0, 10);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = mLayoutManager.findLastVisibleItemPosition();
                int total = mLayoutManager.getItemCount();
                Log.d("SCROLL", "position: " + position + " total: " + total);
                if (totalElements > total) {
                    if (total - 4 < position) {
                        if (!pendingRequest) {
                            Log.d("SCROLL", "LLAMNDO!!");
                            getData(total, 10);
                        }
                    }
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                comicsAdapter.clear();
                getData(0, 10);
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
        new BackgroundComics().execute(spider);
    }

    @Override
    public void clicked(Object object) {
        swipeRefreshLayout.setRefreshing(false);
        Intent intent = new Intent(getContext(), ComicActivity.class);
        intent.putExtra(ComicActivity.KEY_COMIC,(Comic) object);
        startActivity(intent);

    }

    private class BackgroundComics extends GetComics {

        @Override
        protected void onPreExecute() {
            swipeRefreshLayout.setRefreshing(true);
            if (!firstEjecution)
                pendingRequest = true;
        }

        @Override
        protected void onPostExecute(Wrapper<Comic> wrapper) {
            if (wrapper != null) {
                WrapperData<Comic> comicWrapperData = wrapper.getData();
                totalElements = wrapper.getData().getTotal();
                if (firstEjecution) {
                    comicsAdapter = new ComicsAdapter(getContext(), comicWrapperData.getResults(),fragment);
                    recyclerView.setAdapter(comicsAdapter);
                    firstEjecution = false;
                } else {
                    comicsAdapter.update(comicWrapperData);
                    pendingRequest = false;
                }
            }
            swipeRefreshLayout.setRefreshing(false);
        }
    }

}
