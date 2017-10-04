package cl.anpetrus.prueba4.main.fragments;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
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
   // private LinearLayoutManager linearLayoutManager;
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean pendingRequest = false;
    private boolean firstEjecution = true;
    private int totalElements = 0;

    public EventsFragment() {
        // Required empty public constructor
    }

    public static EventsFragment newInstance() {
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

        //linearLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecorationL(2, dpToPxL(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

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
                    eventsAdapter = new EventsAdapter(getContext(), eventWrapperData.getResults());
                    recyclerView.setAdapter(eventsAdapter);
                    firstEjecution = false;
                } else {
                    eventsAdapter.update(eventWrapperData);
                    pendingRequest = false;
                }
            }
        }
    }

    private class GridSpacingItemDecorationL extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecorationL(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }


        /**
         * Converting dp to pixel
         */


    }
    int dpToPxL( int dp) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics()));
    }

    }
