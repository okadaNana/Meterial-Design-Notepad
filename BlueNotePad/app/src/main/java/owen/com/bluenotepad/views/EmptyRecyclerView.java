package owen.com.bluenotepad.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * 当RecyclerView的内容为空时，向用户提示
 *
 * Created by Owen on 2015/8/24.
 */
public class EmptyRecyclerView extends RecyclerView {

    /**
     * 数据为空时，向用户展示的提示
     */
    private View emptyView;

    public EmptyRecyclerView(Context context) {
        this(context, null);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void checkIfEmpty() {
        if (emptyView != null && getAdapter() != null) {
            boolean emptyViewVisible = getAdapter().getItemCount() == 0;

            // 显示为空提示
            emptyView.setVisibility(emptyViewVisible ? View.VISIBLE : View.GONE);
            // RecyclerView本身隐藏
            this.setVisibility(emptyViewVisible ? View.GONE : View.VISIBLE);
        }
    }

    private RecyclerView.AdapterDataObserver emptyObserver = new RecyclerView.AdapterDataObserver() {

        @Override
        public void onChanged() {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkIfEmpty();
        }
    };

    @Override
    public void setAdapter(Adapter adapter) {
        RecyclerView.Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(emptyObserver);
        }

        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(emptyObserver);
        }

        checkIfEmpty();
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;

        checkIfEmpty();
    }

}
