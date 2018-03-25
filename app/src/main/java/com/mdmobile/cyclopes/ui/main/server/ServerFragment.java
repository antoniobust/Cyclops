package com.mdmobile.cyclopes.ui.main.server;

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

import com.mdmobile.cyclopes.R;
import com.mdmobile.cyclopes.adapters.DsInfoAdapter;
import com.mdmobile.cyclopes.adapters.MsInfoAdapter;
import com.mdmobile.cyclopes.adapters.ServerListAdapter;
import com.mdmobile.cyclopes.provider.McContract;


public class ServerFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    DsInfoAdapter dsInfoAdapter;
    MsInfoAdapter msInfoAdapter;
    RecyclerView dsInfoRecycler, msInfoRecycler;

    public ServerFragment() {
        // Required empty public constructor
    }


    public static ServerFragment newInstance() {
        return new ServerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Context contextThemeWrapper = new android.view.ContextThemeWrapper(getActivity(), R.style.AppTheme_MainActivity_Fragment);
        inflater = inflater.cloneInContext(contextThemeWrapper);
        View rootView = inflater.inflate(R.layout.fragment_server, container, false);

        dsInfoRecycler = rootView.findViewById(R.id.ds_recycler);
        msInfoRecycler = rootView.findViewById(R.id.ms_recycler);

        dsInfoRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        msInfoRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        getLoaderManager().initLoader(50, null, this);
        getLoaderManager().initLoader(51, null, this);

        dsInfoAdapter = new DsInfoAdapter(getContext(), null, (ServerListAdapter.onClick) getActivity());
        msInfoAdapter = new MsInfoAdapter(getContext(), null, (ServerListAdapter.onClick) getActivity());

        dsInfoRecycler.setAdapter(dsInfoAdapter);
        msInfoRecycler.setAdapter(msInfoAdapter);

        return rootView;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == 50) {
            return new CursorLoader(getContext(), McContract.DsInfo.CONTENT_URI,
                    null, null, null, null);
        } else if (id == 51) {
            return new CursorLoader(getContext(), McContract.MsInfo.CONTENT_URI,
                    null, null, null, null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == 50) {
            dsInfoAdapter.swapCursor(data);
        } else {
            msInfoAdapter.swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (loader.getId() == 50) {
            dsInfoAdapter.swapCursor(null);
        } else {
            msInfoAdapter.swapCursor(null);
        }
    }
}
