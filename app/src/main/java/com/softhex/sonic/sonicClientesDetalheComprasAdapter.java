package com.softhex.sonic;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class sonicClientesDetalheComprasAdapter implements ExpandableListAdapter {

    private Context mContext;
    private List<sonicClientesDetalheComprasHolder> mList;
    private HashMap<String, List<sonicClientesDetalheComprasItensHolder>> mListItens;
    private String mTitle, mChild;

    List<String> cource_title;
    HashMap<String,List<String>> child_title;

    public sonicClientesDetalheComprasAdapter(Context mContext, List<String> cource_title, HashMap<String, List<String>> child_title) {//(Context mContext, List<sonicClientesDetalheComprasHolder> mList, HashMap<String, List<sonicClientesDetalheComprasItensHolder>> mListItens){
        this.mContext = mContext;
        this.mList = mList;
        this.mListItens = mListItens;
        this.cource_title = cource_title;
        this.child_title = child_title;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return cource_title.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return child_title.get(cource_title.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return cource_title.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child_title.get(cource_title.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String titles = (String) this.getGroup(groupPosition);
        if(convertView==null){
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout_cards_list_group,null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.tvGroup);
        textView.setText(titles);
        if(isExpanded){
            textView.setTypeface(null, Typeface.BOLD);
            textView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_account_tie_grey600_24dp, 0,
                    0, 0);
        }else{
            textView.setCompoundDrawablesWithIntrinsicBounds( R.mipmap.ic_arrow_left_black_24dp, 0,
                   0, 0);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String titles = (String) this.getChild(groupPosition,childPosition);
        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout_cards_list_item,null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.tvItem);
        textView.setText(titles);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }
}
