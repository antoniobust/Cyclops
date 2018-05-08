package com.mdmobile.cyclops.ui.main.users;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mdmobile.cyclops.R;
import com.mdmobile.cyclops.adapters.UserListAdapter;
import com.mdmobile.cyclops.provider.McContract;
import com.mdmobile.cyclops.ui.BasicFragment;
import com.mdmobile.cyclops.utils.ServerUtility;


public class UsersFragment extends BasicFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    UserListAdapter adapter;

    public UsersFragment() {
        // Required empty public constructor
    }

    public static UsersFragment newInstance() {

        return new UsersFragment();
    }

    // -- Interface methods
    @Override
    public void changeServerContent() {
        initializeLoader();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = McContract.buildUriWithServerName(McContract.UserInfo.CONTENT_URI, ServerUtility.getActiveServer().getServerName());
        return new CursorLoader(getContext(), uri,
                new String[]{McContract.USER_TABLE_NAME + "." + McContract.UserInfo.DISPLAYED_NAME, McContract.UserInfo.IS_LOCKED},
                null, null, McContract.UserInfo.DISPLAYED_NAME + " asc");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Apply style to fragment
        final Context contextThemeWrapper = new android.view.ContextThemeWrapper(getActivity(), R.style.AppTheme_MainActivity_Fragment);
        //Clone inflater using the contextTHemeWrapper
        inflater = inflater.cloneInContext(contextThemeWrapper);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_users, container, false);
        RecyclerView recycler = rootView.findViewById(R.id.users_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        adapter = new UserListAdapter(null);
        recycler.setAdapter(adapter);

        initializeLoader();
        return rootView;
    }

    private void initializeLoader() {
        if (getLoaderManager().getLoader(100) == null) {
            getLoaderManager().initLoader(100, null, this);
        } else {
            getLoaderManager().restartLoader(100, null, this);
        }

    }
}
