package com.mdmobile.cyclops.ui.main.users;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mdmobile.cyclops.R;
import com.mdmobile.cyclops.adapters.UserListAdapter;
import com.mdmobile.cyclops.provider.McContract;
import com.mdmobile.cyclops.security.ServerNotFound;
import com.mdmobile.cyclops.ui.BasicFragment;
import com.mdmobile.cyclops.ui.logIn.LoginActivity;
import com.mdmobile.cyclops.util.RecyclerEmptyView;
import com.mdmobile.cyclops.util.ServerUtility;


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
        try {
            Uri uri = McContract.buildUriWithServerName(McContract.UserInfo.CONTENT_URI, ServerUtility.getActiveServer().getServerName());
            return new CursorLoader(getContext(), uri,
                    new String[]{McContract.USER_TABLE_NAME + "." + McContract.UserInfo.DISPLAYED_NAME, McContract.UserInfo.IS_LOCKED},
                    null, null, McContract.UserInfo.DISPLAYED_NAME + " asc");
        }catch (ServerNotFound e){
            e.printStackTrace();
            LoginActivity.Companion.launchActivity();
            return null;
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Apply style to fragment
        final Context contextThemeWrapper = new android.view.ContextThemeWrapper(getActivity(), R.style.AppTheme_MainActivity_Fragment);
        //Clone inflater using the contextTHemeWrapper
        inflater = inflater.cloneInContext(contextThemeWrapper);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_users, container, false);
        RecyclerEmptyView recycler = rootView.findViewById(R.id.users_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setEmptyView(rootView.findViewById(R.id.user_list_empty_view));

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
