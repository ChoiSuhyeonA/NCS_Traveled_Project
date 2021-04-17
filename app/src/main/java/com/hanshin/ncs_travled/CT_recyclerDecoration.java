package com.hanshin.ncs_travled;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class CT_recyclerDecoration extends RecyclerView.ItemDecoration {
    private final int divWidth;
    public CT_recyclerDecoration(int divWidth) {
        this.divWidth = divWidth;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.right = divWidth;
    }
}
