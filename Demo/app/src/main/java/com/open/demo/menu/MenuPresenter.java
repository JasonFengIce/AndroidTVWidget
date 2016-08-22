package com.open.demo.menu;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hailongqiu on 2016/8/22.
 */
public abstract class MenuPresenter extends Presenter {

    RecyclerView.LayoutManager mLayoutManger;

    public static class MernuViewHolder extends Presenter.ViewHolder {
        ContainerViewHolder mContainerViewHolder;

        public MernuViewHolder(View view) {
            super(view);
        }
    }

    static class ContainerViewHolder extends ViewHolder {
        final MernuViewHolder mMenuViewHolder;

        public ContainerViewHolder(TreeContainerView containerView, MernuViewHolder subMenuViewHolder) {
            super(containerView);
            // 添加子菜单.
            containerView.addSubMenuView(subMenuViewHolder.view);
            // 添加菜单item.
//            containerView.addItemView(null);
            mMenuViewHolder = subMenuViewHolder;
            mMenuViewHolder.mContainerViewHolder = this;
        }

    }

    public MenuPresenter(Context context) {
        this(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    public MenuPresenter(RecyclerView.LayoutManager layoutManager) {
        this.mLayoutManger = layoutManager;
    }

    public void setLayoutManger(RecyclerView.LayoutManager layoutManger) {
        this.mLayoutManger = layoutManger;
    }

    public RecyclerView.LayoutManager getLayoutManger() {
        return this.mLayoutManger;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        MernuViewHolder vh = createMenuViewHolder(parent); // 创建 sub menu holder.
        ViewHolder result;
        //
        TreeContainerView containerView = new TreeContainerView(parent.getContext());
        //
        result = new ContainerViewHolder(containerView, vh); // 创建带子菜单还有菜单item的Holder.
        //
        return result;
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        onBindRowViewHolder(getRowViewHolder(viewHolder), item);
    }

    public final ViewHolder getRowViewHolder(Presenter.ViewHolder holder) {
        if (holder instanceof ContainerViewHolder) {
            return ((ContainerViewHolder) holder).mMenuViewHolder;
        } else {
            return (ViewHolder) holder;
        }
    }

    protected void onBindRowViewHolder(ViewHolder vh, Object item) {
    }

    protected abstract MernuViewHolder createMenuViewHolder(ViewGroup parent);

}
