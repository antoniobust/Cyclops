package com.mdmobile.pocketconsole.ui.main.users;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.adapters.UserListAdapter;
import com.mdmobile.pocketconsole.provider.McContract;


public class UsersFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    UserListAdapter adapter;

    public UsersFragment() {
        // Required empty public constructor
    }

    public static UsersFragment newInstance() {

        return new UsersFragment();
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

        getLoaderManager().initLoader(100, null, this);

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(), McContract.UserInfo.CONTENT_URI, null, null, null, McContract.UserInfo.DISPLAYED_NAME + " asc");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
