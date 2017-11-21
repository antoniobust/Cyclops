package com.mdmobile.pocketconsole.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Utility class that provided methods for setting an empty view when recycler is empty
 */

public abstract class RecyclerEmptyView extends RecyclerView {

    private View emptyView;

    private final AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            checkIfEmpty();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
        }
    };

    // -- Constructor
    public RecyclerEmptyView(Context context) {
        super(context);
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }
    }

    public void setEmptyView(View view) {
        this.emptyView = view;
        checkIfEmpty();
    }

    private void checkIfEmpty() {
        if (emptyView != null) {
            emptyView.setVisibility(getAdapter().getItemCount() > 0 ? VISIBLE : GONE);
        }
    }

}
