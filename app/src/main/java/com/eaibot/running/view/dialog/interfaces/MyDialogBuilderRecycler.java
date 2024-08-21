package com.eaibot.running.view.dialog.interfaces;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.eaibot.running.R;
import com.eaibot.running.adapter.RecyclerViewAdapter;
import com.eaibot.running.db.dao.SlamLocationBean;
import com.eaibot.running.view.StyledDialog;
import com.eaibot.running.view.dialog.CustomBottomSheetDialog;
import com.eaibot.running.view.dialog.Tool;
import com.eaibot.running.view.dialog.adapter.SuperLvAdapter;
import com.eaibot.running.view.dialog.adapter.SuperLvHolder;
import com.eaibot.running.view.dialog.bottomsheet.BsLvHolder;
import com.eaibot.running.view.dialog.config.ConfigBean;
import com.eaibot.running.view.dialog.config.ConfigBeanRecycler;
import com.eaibot.running.view.dialog.config.DefaultConfig;
import com.eaibot.running.view.dialog.view.IosActionSheetHolderRecycler;
import com.eaibot.running.view.dialog.view.IosAlertDialogHolderRecycler;
import com.eaibot.running.view.dialog.view.IosCenterItemHolderRecycler;
import com.eaibot.running.view.dialog.view.ToolRecycler;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Collections;

/**
 * 这个是绘制showdilog的代码
 */
public  class MyDialogBuilderRecycler {

    protected static int singleChosen;
    protected ConfigBeanRecycler buildByType(ConfigBeanRecycler bean){
       ToolRecycler.fixContext(bean);

       switch (bean.type){
           case DefaultConfig.TYPE_MD_LOADING:
               ToolRecycler.newCustomDialog(bean);
               buildMdLoading(bean);
               break;
           case DefaultConfig.TYPE_MD_ALERT:
               buildMdAlert(bean);
               break;
           case DefaultConfig.TYPE_MD_SINGLE_CHOOSE:
               buildMdSingleChoose(bean);
               break;
           case DefaultConfig.TYPE_MD_MULTI_CHOOSE:
               buildMdMultiChoose(bean);
               break;
           case DefaultConfig.TYPE_IOS_HORIZONTAL:
               ToolRecycler.newCustomDialog(bean);
               buildIosAlert(bean);
               break;
           case DefaultConfig.TYPE_IOS_VERTICAL:
               ToolRecycler.newCustomDialog(bean);
               buildIosAlertVertical(bean);
               break;
           case DefaultConfig.TYPE_IOS_BOTTOM:
               ToolRecycler.newCustomDialog(bean);
               buildBottomItemDialog(bean);
               break;
           case DefaultConfig.TYPE_IOS_INPUT:
               ToolRecycler.newCustomDialog(bean);
               buildNormalInput(bean);
               break;
           case DefaultConfig.TYPE_IOS_CENTER_LIST:
               ToolRecycler.newCustomDialog(bean);
               buildIosSingleChoose(bean);
               break;
           case DefaultConfig.TYPE_CUSTOM_VIEW:
               ToolRecycler.newCustomDialog(bean);
               bean.dialog.setContentView(bean.customView);
               bean.dialog.getWindow().setGravity(bean.gravity);

               break;
           case DefaultConfig.TYPE_BOTTOM_SHEET_CUSTOM:
              buildBottomSheet(bean);


               break;
           case DefaultConfig.TYPE_BOTTOM_SHEET_LIST:
               buildBottomSheetLv(bean);


               break;
           case DefaultConfig.TYPE_BOTTOM_SHEET_GRID:
               buildBottomSheetLv(bean);

               break;

           case DefaultConfig.TYPE_LOADING:
               ToolRecycler.newCustomDialog(bean);
               buildLoading(bean);
               break;
          default:
              break;


       }

       ToolRecycler.setDialogStyle(bean);

       ToolRecycler.setCancelable(bean);
       return bean;
   }

    private void buildBottomSheetGv(ConfigBean bean) {
        final CustomBottomSheetDialog dialog = new CustomBottomSheetDialog(bean.context);

        dialog.setContentView(bean.customView);


        bean.dialog = dialog;
    }

    private void buildBottomSheetLv(final ConfigBeanRecycler bean) {
        final BottomSheetDialog dialog = new BottomSheetDialog(bean.context);
        LinearLayout root = (LinearLayout) View.inflate(bean.context, R.layout.dialog_bottomsheet_lv, null);
        TextView tvTitle = (TextView) root.findViewById(R.id.tv_title);
        if (TextUtils.isEmpty(bean.title)){
            tvTitle.setVisibility(View.GONE);
        }else {
            tvTitle.setText(bean.title);
        }

        if (bean.type == DefaultConfig.TYPE_BOTTOM_SHEET_LIST){
            ListView listView = new ListView(bean.context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            listView.setLayoutParams(params);
            listView.setDividerHeight(0);
            // ListView listView = (ListView) root.findViewById(R.id.lv);

            root.addView(listView,1);

            if (bean.mAdapter == null){
                SuperLvAdapter adapter = new SuperLvAdapter(bean.context) {
                    @Override
                    protected SuperLvHolder generateNewHolder(Context context, int itemViewType) {
                        return new BsLvHolder(context);
                    }
                };

                bean.mAdapter = adapter;
            }


            listView.setAdapter(bean.mAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    SlamLocationBean markPose = bean.lvDatas.get(position);
                    dialog.dismiss();
                    bean.itemListener.onItemClick(markPose.getLocationNameChina(),position);
                }
            });


            bean.mAdapter.addAll(bean.lvDatas);
        }else if(bean.type == DefaultConfig.TYPE_BOTTOM_SHEET_GRID){    //自定义recyclerview
            final RecyclerView recyclerView = new RecyclerView(bean.context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            recyclerView.setLayoutManager(new GridLayoutManager(bean.context,4));
            recyclerView.setLayoutParams(params);
            root.addView(recyclerView,1);

            if (bean.mRCAdapter == null){
                bean.mRCAdapter = new RecyclerViewAdapter(bean.context);
            }
            recyclerView.setAdapter(bean.mRCAdapter);

            ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
                @Override
                public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                    int dragFlags = ItemTouchHelper.UP|ItemTouchHelper.DOWN | ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;
                    return makeMovementFlags(dragFlags,ItemTouchHelper.START|ItemTouchHelper.END);
                }

                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    int fromPosition = viewHolder.getAdapterPosition();
                    int toPosition = target.getAdapterPosition();
                    if (fromPosition < toPosition) {
                        for (int i = fromPosition; i < toPosition; i++) {
                            Collections.swap(bean.lvDatas, i, i + 1);
                        }
                    } else {
                        for (int i = fromPosition; i > toPosition; i--) {
                            Collections.swap(bean.lvDatas, i, i - 1);
                        }
                    }
                    bean.mRCAdapter.notifyItemMoved(fromPosition, toPosition);
                    return true;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    Log.e("onSwiped","onSwiped");
                    bean.lvDatas.remove(viewHolder.getAdapterPosition());
                    bean.mRCAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                }

                @Override
                public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                        //滑动时改变Item的透明度
                        final float alpha = 1 - Math.abs(dX) / (float)viewHolder.itemView.getWidth();
                        viewHolder.itemView.setAlpha(alpha);
                        viewHolder.itemView.setTranslationX(dX);
                    }
                }

                @Override
                public boolean isItemViewSwipeEnabled() {
                    return true;
                }

                @Override
                public boolean isLongPressDragEnabled() {
                    return true;
                }
            });
            helper.attachToRecyclerView(recyclerView);
            bean.mRCAdapter.setDatas(bean.lvDatas);
            bean.mRCAdapter.setDialog(dialog);
        }

        dialog.setContentView(root);
        bean.dialog = dialog;
    }

    private void buildBottomSheet(ConfigBeanRecycler bean) {
        final BottomSheetDialog dialog = new BottomSheetDialog(bean.context);
        dialog.setContentView(bean.customView);
        dialog.setCancelable(bean.cancelable);
        dialog.setCanceledOnTouchOutside(bean.outsideTouchable);

        bean.dialog = dialog;
    }

    protected  ConfigBeanRecycler buildLoading(ConfigBeanRecycler bean){
        View root = View.inflate(bean.context, R.layout.dialog_loading, null);
        TextView tvMsg = (TextView) root.findViewById(R.id.tv_msg);
        tvMsg.setText(bean.msg);
        bean.dialog.setContentView(root);
        return bean;
    }


    protected  ConfigBeanRecycler buildMdLoading(ConfigBeanRecycler bean){
        View root = View.inflate(bean.context, R.layout.dialog_progressview_wrapconent, null);
        TextView tvMsg = (TextView) root.findViewById(R.id.message);
        tvMsg.setText(bean.msg);
//        tvMsg.setTextColor(Tool.getColor(tvMsg.getContext(),bean.msgTxtColor));这个就是改变颜色的代码，根据需求自己修改
        bean.dialog.setContentView(root);
        return bean;
    }

    protected  ConfigBeanRecycler buildMdAlert(final ConfigBeanRecycler bean){
        AlertDialog.Builder builder = new AlertDialog.Builder(bean.context);
        builder.setTitle(bean.title)
                .setMessage(bean.msg)
                .setPositiveButton(bean.text1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bean.listener.onFirst();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(bean.text2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bean.listener.onSecond();
                        dialog.dismiss();
                    }
                }).setNeutralButton(bean.text3, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bean.listener.onThird();
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                bean.listener.onCancle();
            }
        });
        bean.alertDialog = dialog;
        return bean;
    }

    protected  ConfigBeanRecycler buildMdSingleChoose(final ConfigBeanRecycler bean){
        AlertDialog.Builder builder = new AlertDialog.Builder(bean.context);
        singleChosen = bean.defaultChosen;
        builder.setTitle(bean.title)
                .setPositiveButton(bean.text1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (bean.listener != null){
                            StyledDialog.dismiss(dialogInterface);
                            bean.listener.onFirst();
                            bean.listener.onGetChoose(singleChosen,bean.wordsMd[singleChosen]);
                        }
                    }
                })
                .setNegativeButton(bean.text2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (bean.listener != null){
                            StyledDialog.dismiss(dialogInterface);
                            bean.listener.onSecond();
                        }
                    }
                })
                .setSingleChoiceItems( bean.wordsMd, bean.defaultChosen, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        singleChosen = i;
                        if (bean.itemListener != null){
                            bean.itemListener.onItemClick(bean.wordsMd[i],i);
                        }

                        if (bean.listener == null){
                            StyledDialog.dismiss(dialogInterface);
                        }

                    }
                });

        AlertDialog dialog = builder.create();
        bean.alertDialog = dialog;
        return bean;
    }

    protected  ConfigBeanRecycler buildMdMultiChoose(final ConfigBeanRecycler bean){
        AlertDialog.Builder builder = new AlertDialog.Builder(bean.context);
        builder.setTitle(bean.title)
                .setCancelable(true)
                .setPositiveButton(bean.text1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (bean.listener != null){
                            StyledDialog.dismiss(dialogInterface);
                            bean.listener.onFirst();
                            bean.listener.onGetChoose(bean.checkedItems);
                        }
                    }
                })
                .setNegativeButton(bean.text2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (bean.listener != null){
                            StyledDialog.dismiss(dialogInterface);
                            bean.listener.onSecond();
                        }
                    }
                })
                .setMultiChoiceItems(bean.wordsMd, bean.checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {

                    }
                })
        ;

        AlertDialog dialog = builder.create();
        bean.alertDialog = dialog;
        return bean;
    }

    protected  ConfigBeanRecycler buildIosAlert(ConfigBeanRecycler bean){
        bean.isVertical = false;
        bean.hint1 = "";
        bean.hint2 = "";
        buildIosCommon(bean);
        return bean;
    }

    protected  ConfigBeanRecycler buildIosAlertVertical(ConfigBeanRecycler bean){
        bean.isVertical = true;
        bean.hint1 = "";
        bean.hint2 = "";
        buildIosCommon(bean);
        return bean;
    }

    protected  ConfigBeanRecycler buildIosSingleChoose(ConfigBeanRecycler bean){
        IosCenterItemHolderRecycler holder = new IosCenterItemHolderRecycler(bean.context);
        bean.dialog.setContentView(holder.rootView);
        holder.assingDatasAndEvents(bean.context,bean);

        bean.viewHeight = Tool.mesureHeight(holder.rootView,holder.lv);

        Window window = bean.dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        return bean;
    }

    protected  ConfigBeanRecycler buildBottomItemDialog(ConfigBeanRecycler bean){
        IosActionSheetHolderRecycler holder = new IosActionSheetHolderRecycler(bean.context);
        bean.dialog.setContentView(holder.rootView);

        holder.assingDatasAndEvents(bean.context,bean);

        bean.viewHeight = Tool.mesureHeight(holder.rootView,holder.lv);

        Window window = bean.dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.mystyle);
        return bean;
    }


    protected  ConfigBeanRecycler buildNormalInput(ConfigBeanRecycler bean){
        buildIosCommon(bean);
        return bean;
    }

    private ConfigBeanRecycler buildIosCommon(ConfigBeanRecycler bean){



        IosAlertDialogHolderRecycler holder = new IosAlertDialogHolderRecycler(bean.context);
        bean.dialog.setContentView(holder.rootView);
        holder.assingDatasAndEvents(bean.context,bean);

        int height = Tool.mesureHeight(holder.rootView,holder.tvMsg,holder.et1,holder.et2);
        bean.viewHeight = height;


        return bean;

    }







    
}
