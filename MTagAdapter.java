package com.yeer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yeer.sdzj.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by dell on 2017/3/3.
 * 这是一个标签是适配器
 */
public class MTagAdapter extends BMAdapter<String> {
    /**
     * primarkTextColor：表示第一颜色（默认的标签显示的颜色）
     * secondTextColor:表示第二颜色（标签选中后的颜色）
     */
    private int primarkTextColor,secondTextColor;

    public int getPrimarkTextColor() {
        return primarkTextColor;
    }

    public void setPrimarkTextColor(int primarkTextColor) {
        this.primarkTextColor = primarkTextColor;
    }

    public int getSecondTextColor() {
        return secondTextColor;
    }

    public void setSecondTextColor(int secondTextColor) {
        this.secondTextColor = secondTextColor;
    }

    /**
     * isDuoXuan:表示是否支持多选（默认是false）
     */
    private boolean isDuoXuan=false;
    /**
     * 保存状态数组
     */
    private List<Boolean> status = new ArrayList<>();

    public MTagAdapter(Context context) {
        super(context);
    }

    public void setDuoXuan(boolean duoXuan) {
        isDuoXuan = duoXuan;
    }

    /**
     * 选中状态的资源文件
     */
    private int check_resource;
    /**
     * 未选中状态的自愿文件
     */
    private int nocheck_resource;

    public void setCheck_resource(int check_resource) {
        this.check_resource = check_resource;
    }

    public void setNocheck_resource(int nocheck_resource) {
        this.nocheck_resource = nocheck_resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_mtag,parent,false);
        final TextView text = (TextView) view.findViewById(R.id.tagname);
        text.setText(getItem(position));
        text.setTag(position);

        if(status.get(position))
        {
            text.setBackgroundResource(check_resource);
        }else{
            text.setBackgroundResource(nocheck_resource);
        }

        if(secondTextColor!=0)
        {
            if(status.get(position))
            {
                text.setTextColor(secondTextColor);
            }else{
                text.setTextColor(primarkTextColor);
            }
        }else{
            text.setTextColor(primarkTextColor);
        }


        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos= ((int) view.getTag());
                if(isDuoXuan)
                {
                    if(status.get(pos))
                    {
                        text.setBackgroundResource(nocheck_resource);
                        status.set(pos,false);
                    }else{
                        text.setBackgroundResource(check_resource);
                        status.set(pos,true);
                    }
                }else
                {
                    //在去指定状态
                    if(status.get(pos))
                    {
                        if(secondTextColor==0)
                        {
                            text.setBackgroundResource(nocheck_resource);
                            status.set(pos,false);
                        }
                    }else{

                        chongzhi();//恢复默认状态

                        status.set(pos,true);

                        notifyDataSetChanged();

                    }
                }
                if(mTagOnClickListener!=null)
                {
                    mTagOnClickListener.onClick(pos,view);
                }
            }
        });
        return view;
    }

    @Override
    public void addAll(Collection<? extends String> collection) {
        for (String s : collection) {
            status.add(false);
        }
        super.addAll(collection);
    }

    @Override
    public void clear() {
        status.clear();
        super.clear();
    }

    public void chongzhi()
    {
        for (int i = 0; i < status.size(); i++) {
            status.set(i,false);
        }
    }

    public String getSelected()
    {
        String s="";
        for (int i = 0; i < status.size(); i++) {
            if(status.get(i))
                s+=(i+",");
        }
        s = s.substring(0,s.length()-1);
        return s;
    }

    public Set<Integer> getSelectedSet()
    {
        Set set = new HashSet<Integer>();
        for (int i = 0; i < status.size(); i++) {
            if(status.get(i))
                set.add(i);
        }
        return set;
    }

    public void setSelectByIndex(int position)
    {
        if(!isDuoXuan)//如果是单选需要重置
        {
            chongzhi();
        }
        status.set(position,true);
        notifyDataSetChanged();
    }


    public void setmTagOnClickListener(MTagOnClickListener mTagOnClickListener) {
        this.mTagOnClickListener = mTagOnClickListener;
    }


    private MTagOnClickListener mTagOnClickListener;


    /**
     * 标签的点击事件
     */
    public interface MTagOnClickListener{
        public void onClick(int position,View view);
    }

}
