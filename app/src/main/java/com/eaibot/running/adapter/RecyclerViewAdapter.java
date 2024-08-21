package com.eaibot.running.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.eaibot.running.R;
import com.eaibot.running.constants.HandlerMsgCode;
import com.eaibot.running.db.dao.SlamLocationBean;
//import com.eaibot.utils.RobotPoseTool;
//import com.eaibot.web.bean.pose.MarkPose;
//import com.eaibot.web.bean.pose.SimpleRobotPose;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author Yist
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context context;

    private Handler handler;

    private Dialog dialog;

    private List<SlamLocationBean> list;

    private MyViewHolder currentHolder,lastHolder;

    /**
     * 保留2位小数
     */
    private DecimalFormat df = new DecimalFormat("######0.00");

    public RecyclerViewAdapter(Context context) {
        this.context = context;
        //this.list = new ArrayList<>();
    }

    public void setDialog(Dialog dialog){
        this.dialog = dialog;
        this.notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_left_recyclerview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //holder.posename.setText(list.get(position).getPoseName());                          //显示标记点名称
        holder.posename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nvaToMarkPose(holder,holder.getLayoutPosition());
            }
        });     //点击标记名称，将前往该点导航，并设置正在导航标志(浅蓝色背景)
//        SimpleRobotPose simpleRobotPose = RobotPoseTool.getSimpleRobotPose(list.get(position).getRobotPose());
//        final String poseString =
//                "[" + df.format(simpleRobotPose.getX()) + ","
//                + df.format(simpleRobotPose.getY()) + ","
//                + df.format(simpleRobotPose.getAngle()) + "]";
//        holder.posePosition.setText(poseString);
    }

    //点击标记名称，将前往该点导航/取消导航，并设置正在导航标志(浅蓝色背景)/设置未导航(白色背景)
    private void nvaToMarkPose(MyViewHolder holder, int position){
        this.currentHolder = holder;
        Message message = Message.obtain();
        if (this.lastHolder==null){
            holder.posename.setBackgroundResource(R.drawable.posename_background);
            message.what = HandlerMsgCode.SINGLE_NAV;
            message.arg1 = position;
            handler.sendMessage(message);
            this.lastHolder = holder;
        }else if (this.currentHolder.getAdapterPosition() != this.lastHolder.getAdapterPosition()) {
            this.lastHolder.posename.setBackgroundResource(R.drawable.item_style);
            holder.posename.setBackgroundResource(R.drawable.posename_background);
            message.what = HandlerMsgCode.SINGLE_NAV;
            message.arg1 = position;
            handler.sendMessage(message);
            this.lastHolder = holder;
        }else {
            holder.posename.setBackgroundResource(R.drawable.item_style);
            message.what = HandlerMsgCode.SINGLE_NAV_FINISH;
            message.arg1 = position;
            handler.sendMessage(message);
            if (lastHolder!=null){
                lastHolder = null;
            }
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView posename,posePosition;                                                              //标记点名称
        MyViewHolder(View itemView) {
            super(itemView);
            posename = itemView.findViewById(R.id.pose_name);
            posePosition = itemView.findViewById(R.id.pose_position);
        }
    }

    //设置目标点停止状态
    public void setIsStop(){
        if (currentHolder!=null) {
            currentHolder.posename.setBackgroundResource(R.drawable.item_style);
            if (lastHolder!=null){
                lastHolder = null;
            }
        }
    }

//    //接收主页面的数据
//    public void setDatas(List<MarkPose> list) {
////        this.list = list;
////        this.notifyDataSetChanged();
//    }


    //接收主页面的数据
    public void setDatas(List<SlamLocationBean> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }


    //接收主页面的recyclerView参数
    public void setRecyclerView(){
        this.notifyDataSetChanged();
    }


    //接收主页面的handler参数
    public void setHandler(Handler handler){
        this.handler = handler;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        //return list.size();
        return 0;
    }

    public void setViewBackgroud(){

    }

}

