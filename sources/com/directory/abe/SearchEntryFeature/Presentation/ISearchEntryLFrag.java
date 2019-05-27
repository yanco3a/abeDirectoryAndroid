package com.directory.abe.SearchEntryFeature.Presentation;

import com.directory.abe.GSONModels.EntryModel;
import java.util.ArrayList;

public interface ISearchEntryLFrag {
    void navigateToDetailsWithBundle(EntryModel entryModel);

    void removeDuplicateListings(ArrayList<EntryModel> arrayList);

    void removeDuplicateListings(ArrayList<EntryModel> arrayList, ArrayList<EntryModel> arrayList2);
}
