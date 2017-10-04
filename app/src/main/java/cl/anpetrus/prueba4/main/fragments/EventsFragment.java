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
import cl.anpetrus.prueba4.adapters.EventsAdapter;
import cl.anpetrus.prueba4.models.Event;
import cl.anpetrus.prueba4.models.Wrapper;
import cl.anpetrus.prueba4.models.WrapperData;
import cl.anpetrus.prueba4.network.GetEvents;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {

    private EventsAdapter eventsAdapter;
    private RecyclerView recyclerView;

    public EventsFragment() {
        // Required empty public constructor
    }

    public static EventsFragment newInstance(){
        EventsFragment fragment = new EventsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.eventsRv);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        CharactersQuery spider = CharactersQuery
                .Builder
                .create()
                .withOffset(0)
                .withLimit(10)
                .build();
        new BackgroundEvents().execute(spider);


    }

    private class BackgroundEvents extends GetEvents {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(Wrapper<Event> wrapper) {
            if(wrapper!=null){
                WrapperData<Event> eventWrapperData = wrapper.getData();

                eventsAdapter = new EventsAdapter(getContext(),eventWrapperData.getResults());
                recyclerView.setAdapter(eventsAdapter);
            }
        }
    }


}