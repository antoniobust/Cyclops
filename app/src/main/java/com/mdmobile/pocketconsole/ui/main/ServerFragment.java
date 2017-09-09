package com.mdmobile.pocketconsole.ui.main;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.mdmobile.pocketconsole.R;
import com.mdmobile.pocketconsole.adapters.ServerDetailsAdapter;
import com.mdmobile.pocketconsole.apiManager.ApiRequestManager;
import com.mdmobile.pocketconsole.provider.McContract;


public class ServerFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    ServerDetailsAdapter detailsAdapter;
    GridView dsInfoGrid, msInfoGrid;

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

        dsInfoGrid = (GridView) rootView.findViewById(R.id.ds_grid_view);
        msInfoGrid = (GridView) rootView.findViewById(R.id.ms_grid_view);

        getLoaderManager().initLoader(50, null, this);
        getLoaderManager().initLoader(51, null, this);

        ApiRequestManager.getInstance().getServerInfo();

        return rootView;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == 50) {
            return new CursorLoader(getActivity().getApplicationContext(), McContract.DeploymentServer.CONTENT_URI,
                    null, null, null, null);
        } else if (id == 51) {
            return new CursorLoader(getActivity().getApplicationContext(), McContract.ManagementServer.CONTENT_URI,
                    null, null, null, null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == 50) {
            dsInfoGrid.setAdapter(new ServerDetailsAdapter(getContext(), data, 0));
        } else {
            msInfoGrid.setAdapter(new ServerDetailsAdapter(getContext(), data, 0));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
