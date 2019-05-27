package com.directory.abe.SearchEntryFeature.Presentation;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import com.directory.abe.AnalyticsApp;
import com.directory.abe.C0246R;
import com.directory.abe.DetailEntryFeature.Presentation.VendorDetailsView;
import com.directory.abe.GSONModels.EntryModel;
import com.directory.abe.Models.Rating;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.google.android.gms.analytics.Tracker;
import java.io.IOError;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchEntryKeywordLFrag extends ListFragment implements ISearchEntryLFrag, OnItemClickListener, Runnable {
    private static final String TAG = SearchEntryKeywordLFrag.class.getSimpleName();
    private Activity activity;
    private Tracker appTracker;
    private Context context;
    private EntryModel entryModel;
    private ArrayList<EntryModel> indiObjs;
    private ListView list;
    private HashMap<Integer, List<Rating>> multimap;
    private ArrayList<EntryModel> objs;
    private List<Rating> ratings;
    private int userId;
    private VendorsAdapter vAdapter;
    private View view;

    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            if (this.view == null) {
                this.view = inflater.inflate(C0246R.layout.frag_category_list_layout_key, container, false);
            } else {
                ((ViewGroup) this.view.getParent()).removeView(this.view);
            }
            return this.view;
        } catch (Exception e) {
            e.printStackTrace();
            return this.view;
        }
    }

    public void onDestroyView() {
        this.view = null;
        super.onDestroyView();
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        this.activity = getActivity();
        setRetainInstance(true);
        this.appTracker = ((AnalyticsApp) this.activity.getApplication()).getDefaultTracker();
        super.onActivityCreated(savedInstanceState);
    }

    public void onResume() {
        super.onResume();
        try {
            this.appTracker.setScreenName("Screen~" + TAG);
            this.appTracker.send(new ScreenViewBuilder().build());
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void navigateToDetailsWithBundle(EntryModel entryModel) {
        this.entryModel = entryModel;
        Bundle extras = new Bundle();
        Intent goToDetailView = new Intent(this.activity, VendorDetailsView.class);
        extras.putSerializable("vendorListing", entryModel);
        extras.putSerializable("ratings", this.objs);
        goToDetailView.putExtras(extras);
        this.activity.startActivity(goToDetailView);
    }

    public void showUIMessages(String s) {
        Toast.makeText(this.context, s, 1).show();
    }

    public void removeDuplicateListings(ArrayList<EntryModel> arrayList, ArrayList<EntryModel> arrayList2) {
    }

    public void removeDuplicateListings(ArrayList<EntryModel> objs) {
        try {
            this.objs = objs;
            if (objs != null) {
                new Thread(this).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void run() {
        try {
            String listingId = "0";
            this.multimap = new HashMap();
            List<Rating> ratings = new ArrayList();
            this.indiObjs = new ArrayList();
            int i = 0;
            while (i < this.objs.size()) {
                if (!(this.objs.get(i) == null || String.valueOf(((EntryModel) this.objs.get(i)).getListingId()).equals(listingId))) {
                    listingId = String.valueOf(((EntryModel) this.objs.get(i)).getListingId());
                    this.indiObjs.add(this.objs.get(i));
                }
                i++;
            }
            updateListAfterFilter(this.indiObjs);
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        navigateToDetailsWithBundle((EntryModel) parent.getItemAtPosition(position));
    }

    public void updateListAfterFilter(final ArrayList<EntryModel> models) {
        final ArrayList<EntryModel> emptyModels = new ArrayList();
        EntryModel entryModel = new EntryModel();
        entryModel.setVendorPostcode("no matches yet...");
        emptyModels.add(entryModel);
        this.activity.runOnUiThread(new Runnable() {
            public void run() {
                if (models != null) {
                    try {
                        if (models.size() > 0) {
                            SearchEntryKeywordLFrag.this.update(models);
                            SearchEntryKeywordLFrag.this.list.setOnItemClickListener(SearchEntryKeywordLFrag.this);
                            return;
                        }
                        SearchEntryKeywordLFrag.this.update(emptyModels);
                        SearchEntryKeywordLFrag.this.list.setOnItemClickListener(null);
                        return;
                    } catch (IOError io) {
                        io.printStackTrace();
                        io.getMessage();
                        SearchEntryKeywordLFrag.this.showUIMessages("Abe isn't feeling so good right now");
                        return;
                    } catch (NullPointerException ne) {
                        ne.printStackTrace();
                        ne.getMessage();
                        SearchEntryKeywordLFrag.this.showUIMessages("Abe isn't feeling so good right now");
                        return;
                    } catch (ActivityNotFoundException an) {
                        an.printStackTrace();
                        an.getMessage();
                        SearchEntryKeywordLFrag.this.showUIMessages("Abe isn't feeling so good right now");
                        return;
                    }
                }
                SearchEntryKeywordLFrag.this.update(emptyModels);
                SearchEntryKeywordLFrag.this.list.setOnItemClickListener(null);
            }
        });
    }

    public void update(ArrayList<EntryModel> m) {
        this.list = (ListView) this.view.findViewById(16908298);
        this.vAdapter = new VendorsAdapter(this.activity.getBaseContext(), C0246R.layout.item_search_category_layout, m);
        this.list.setAdapter(this.vAdapter);
        this.vAdapter.notifyDataSetChanged();
    }
}
