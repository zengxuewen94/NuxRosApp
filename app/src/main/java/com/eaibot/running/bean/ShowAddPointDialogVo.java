package com.eaibot.running.bean;

import com.eaibot.running.db.dao.SlamLocationBean;

import java.util.List;

/**
 * @author :  zengxuewen
 * @date :  2023/1/12
 * @desc :  显示位置点列表Vo
 */
public class ShowAddPointDialogVo {

    int type ;  // 1 点击新建位置点  2点击位置点列表
    List<SlamLocationBean> list;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<SlamLocationBean> getList() {
        return list;
    }

    public void setList(List<SlamLocationBean> list) {
        this.list = list;
    }

    public ShowAddPointDialogVo(int type,List<SlamLocationBean> list) {
        this.type = type;
        this.list = list;
    }

    public ShowAddPointDialogVo() {

    }

}
