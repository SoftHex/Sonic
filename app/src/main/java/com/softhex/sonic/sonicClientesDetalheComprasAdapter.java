package com.softhex.sonic;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Environment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class sonicClientesDetalheComprasAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<sonicClientesDetalheComprasHolder> mList;
    private HashMap<String, List<sonicClientesDetalheComprasItensHolder>> childData;
    private List<String> headerData = new ArrayList<>();
    private sonicUtils mUtils;
    private sonicClientesDetalheComprasItensHolder mItens;
    private sonicPreferences mPrefs;

    public sonicClientesDetalheComprasAdapter(Context mContext, List<String> headerData, List<sonicClientesDetalheComprasHolder> mList, HashMap<String, List<sonicClientesDetalheComprasItensHolder>> mHash){
        this.mContext = mContext;
        this.headerData = headerData;
        this.childData = mHash;
        this.mList = mList;
        this.mUtils = new sonicUtils(mContext);
        this.mPrefs = new sonicPreferences(mContext);
    }

    @Override
    public int getGroupCount() {
        return headerData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childData.get(headerData.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return headerData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childData.get(headerData.get(groupPosition)).get(childPosition);
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
        ViewHolderGroup holder  = new ViewHolderGroup();
        if(convertView==null){
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout_cards_list_group,null);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolderGroup) convertView.getTag();
        }

        holder.pbGroup =  convertView.findViewById(R.id.pbGroup);
        holder.tvC1L1 = convertView.findViewById(R.id.tvC1L1);
        holder.tvC1L2 = convertView.findViewById(R.id.tvC1L2);
        holder.tvC1L3 = convertView.findViewById(R.id.tvC1L3);
        holder.tvC2L1 = convertView.findViewById(R.id.tvC2L1);
        holder.tvC2L2 = convertView.findViewById(R.id.tvC2L2);
        holder.tvC2L3 = convertView.findViewById(R.id.tvC2L3);
        holder.tvEmpresa = convertView.findViewById(R.id.tvEmpresa);

        holder.tvC2L1.setText(mUtils.Data.dataFotmatadaBR(mList.get(groupPosition).getData()));
        holder.tvC1L1.setText(mList.get(groupPosition).getTipoCobranca());
        holder.tvC1L3.setText("CÓD.: " + (mList.get(groupPosition).getCodigo())+" / "+mList.get(groupPosition).getVendedor());
        holder.tvC1L2.setText("PRAZO: "+mList.get(groupPosition).getPrazo());
        holder.tvC2L2.setText("R$ "+mList.get(groupPosition).getValor());
        holder.tvEmpresa.setText("EMP.: "+mList.get(groupPosition).getEmpresa());

         if(mList.get(groupPosition).getValorDesc().equals("0,00")){
             holder.tvC2L3.setVisibility(View.INVISIBLE);
         }else{
             SpannableString stringEstilizada = new SpannableString("R$ "+mList.get(groupPosition).getValorDesc());
             stringEstilizada.setSpan(new StrikethroughSpan(),0,stringEstilizada.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
             holder.tvC2L3.setVisibility(View.VISIBLE);
             holder.tvC2L3.setText(stringEstilizada);
         }

        List<TextView> mList;
        mList =  holder.getAllViews();
        if(isExpanded){
            
            for(int i=0; i< mList.size(); i++){
                mList.get(i).setTypeface(Typeface.DEFAULT_BOLD);
            }
            holder.tvC1L1.setCompoundDrawablesWithIntrinsicBounds( R.mipmap.ic_chevron_up_grey600_24dp, 0, 0, 0);
        }else{
            for(int i=0; i< mList.size(); i++){
                mList.get(i).setTypeface(Typeface.DEFAULT);
            }
            holder.tvC1L1.setCompoundDrawablesWithIntrinsicBounds( R.mipmap.ic_chevron_down_grey600_24dp, 0, 0, 0);
        }

        return convertView;
    }

    public View getGroupView(ExpandableListView listView, int groupPosition) {
        long packedPosition = ExpandableListView.getPackedPositionForGroup(groupPosition);
        int flatPosition = listView.getFlatListPosition(packedPosition);
        int first = listView.getFirstVisiblePosition();
        return listView.getChildAt(flatPosition - first);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        mItens = (sonicClientesDetalheComprasItensHolder)getChild(groupPosition,childPosition);
        ViewHolderItem holder = new ViewHolderItem();
        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout_cards_list_item,null);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolderItem) convertView.getTag();
        }
        holder.tvNomeProduto = convertView.findViewById(R.id.tvNomeProduto);
        holder.ivImagem = convertView.findViewById(R.id.ivImagem);
        holder.tvCodigoUnidade = convertView.findViewById(R.id.tvCodigoUnidade);
        holder.tvQtdPrecoValor = convertView.findViewById(R.id.tvQtdPrecoValor);
        holder.tvDesconto = convertView.findViewById(R.id.tvDescontoItem);
        holder.tvLetra = convertView.findViewById(R.id.tvLetra);
        File fileJpg = new File(Environment.getExternalStorageDirectory(), sonicConstants.LOCAL_IMG_CATALOGO + mItens.getCodigoProduto() +".JPG");
        if(fileJpg.exists()){

            holder.tvLetra.setVisibility(View.GONE);
            holder.ivImagem.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(fileJpg)
                    .circleCrop()
                    .apply(new RequestOptions().override(100,100))
                    .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                    .into(holder.ivImagem);

        }else{
            holder.ivImagem.setVisibility(View.GONE);
            holder.tvLetra.setVisibility(View.VISIBLE);
            holder.tvLetra.setText(mItens.getLetra());
        }
        holder.tvNomeProduto.setText(mItens.getProduto());
        holder.tvCodigoUnidade.setText("CÓD.: "+mItens.getCodigoProduto()+ " / UNID.: " + mItens.getUnidadeMedidaSigla());
        holder.tvQtdPrecoValor.setText("QTD.: "+mItens.getQuantidade()+ " / PREÇO: R$ " + mItens.getPrecoUnitario()+ " / TOTAL: R$ "+mItens.getValorGeral());
        if(mItens.getDesconto().equals("0,00")){
            holder.tvDesconto.setVisibility(View.GONE);
        }else{
            holder.tvDesconto.setVisibility(View.VISIBLE);
            SpannableString stringEstilizada = new SpannableString("R$ "+mItens.getDesconto());
            stringEstilizada.setSpan(new StrikethroughSpan(),0,stringEstilizada.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            holder.tvDesconto.setText(stringEstilizada);
        }

        return convertView;
    }

    class ViewHolderGroup{
        TextView tvC1L1;
        TextView tvC1L2;
        TextView tvC1L3;
        TextView tvC2L1;
        TextView tvC2L2;
        TextView tvC2L3;
        TextView tvEmpresa;
        ProgressBar pbGroup;
        public List<TextView> getAllViews(){
            List<TextView> mList = new ArrayList<>();
            mList.add(tvC1L1);
            mList.add(tvC1L2);
            mList.add(tvC1L3);
            mList.add(tvC2L1);
            mList.add(tvC2L2);
            mList.add(tvC2L3);
            mList.add(tvEmpresa);
            return mList;
        }
    }
    class ViewHolderItem{
        ImageView ivImagem;
        TextView tvLetra;
        TextView tvNomeProduto;
        TextView tvCodigoUnidade;
        TextView tvQtdPrecoValor;
        TextView tvDesconto;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
