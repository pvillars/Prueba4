package cl.anpetrus.prueba4.views.main.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import cl.anpetrus.prueba4.CharactersQuery;
import cl.anpetrus.prueba4.R;
import cl.anpetrus.prueba4.adapters.EventsAdapter;
import cl.anpetrus.prueba4.listeners.ActionFragmentListener;
import cl.anpetrus.prueba4.models.Event;
import cl.anpetrus.prueba4.models.Wrapper;
import cl.anpetrus.prueba4.models.WrapperData;
import cl.anpetrus.prueba4.network.GetEvents;
import cl.anpetrus.prueba4.views.main.data.EventActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment implements ActionFragmentListener{

    private EventsAdapter eventsAdapter;
    private RecyclerView recyclerView;
    // private LinearLayoutManager linearLayoutManager;
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean pendingRequest = false;
    private boolean firstEjecution = true;
    private int totalElements = 0;

    private static EventsFragment fragment;

    public EventsFragment() {
        // Required empty public constructor
    }

    public static EventsFragment newInstance() {
        fragment = new EventsFragment();
        return fragment;
    }

    public static EventsFragment getInstance() {
        if(fragment == null){
            fragment = new EventsFragment();
        }

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getContext(), "Resume", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        totalElements = 0;
        firstEjecution = true;

        recyclerView = view.findViewById(R.id.eventsRv);
        recyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, GridSpacingItemDecoration.dpToPx(getResources(),4), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Toast.makeText(getContext(), "Created", Toast.LENGTH_SHORT).show();
        CharactersQuery spider = CharactersQuery
                .Builder
                .create()
                .withOffset(0)
                .withLimit(10)
                .build();
        new BackgroundEvents().execute(spider);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //int position = linearLayoutManager.findLastVisibleItemPosition();
                int position = dy;//mLayoutManager.getPosition(recyclerView);
                int total = mLayoutManager.getItemCount();
                Log.d("SCROLL", "position: " + position + " total: " + total);
                if (totalElements > total) {
                    if (total - 4 < position) {
                        if (!pendingRequest) {
                            Log.d("SCROLL", "LLAMNDO!!");
                            CharactersQuery spider = CharactersQuery
                                    .Builder
                                    .create()
                                    .withOffset(total)
                                    .withLimit(10)
                                    .build();
                            new BackgroundEvents().execute(spider);
                        }

                    }
                }
            }
        });
    }

    @Override
    public void clicked(Event event) {
        Intent intent = new Intent(getContext(), EventActivity.class);
        intent.putExtra(EventActivity.KEY_EVENT,event);
        startActivity(intent);

    }

    private class BackgroundEvents extends GetEvents {

        @Override
        protected void onPreExecute() {
            if (!firstEjecution)
                pendingRequest = true;
        }

        @Override
        protected void onPostExecute(Wrapper<Event> wrapper) {
            if (wrapper != null) {
                WrapperData<Event> eventWrapperData = wrapper.getData();
                totalElements = wrapper.getData().getTotal();
                if (firstEjecution) {
                    eventsAdapter = new EventsAdapter(getContext(), eventWrapperData.getResults(), fragment);
                    recyclerView.setAdapter(eventsAdapter);
                    firstEjecution = false;
                } else {
                    eventsAdapter.update(eventWrapperData);
                    pendingRequest = false;
                }
            }
        }
    }

}
