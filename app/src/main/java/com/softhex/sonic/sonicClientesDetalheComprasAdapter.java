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
import android.widget.ImageView;
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
        //return mChild.get(mHeader.get(groupPosition)).get(childPosition).getCodigoVenda();
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

        try{
            holder.tvData = convertView.findViewById(R.id.tvData);
            holder.tvTipoCobranca = convertView.findViewById(R.id.tvTipoCobranca);
            holder.tvCodigoMobile = convertView.findViewById(R.id.tvCodigoMobile);
            holder.tvPrazo = convertView.findViewById(R.id.tvPrazo);
            holder.tvValor = convertView.findViewById(R.id.tvValor);
            holder.tvValorDesc = convertView.findViewById(R.id.tvValorDesc);
            holder.tvData.setText(mUtils.Data.dataFotmatadaBR(mList.get(groupPosition).getData()));
            holder.tvTipoCobranca.setText(mList.get(groupPosition).getTipoCobranca());
            holder.tvCodigoMobile.setText("CÓD. PEDIDO: " + (mList.get(groupPosition).getCodigo()));
            holder.tvPrazo.setText("PRAZO: "+mList.get(groupPosition).getPrazo());
            holder.tvValor.setText("R$ "+mList.get(groupPosition).getValor());

            if(mList.get(groupPosition).getValorDesc().equals("0,00")){
                holder.tvValorDesc.setVisibility(View.GONE);
            }else{
                SpannableString stringEstilizada = new SpannableString("R$ "+mList.get(groupPosition).getValorDesc());
                stringEstilizada.setSpan(new StrikethroughSpan(),0,stringEstilizada.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                holder.tvValorDesc.setVisibility(View.VISIBLE);
                holder.tvValorDesc.setText(stringEstilizada);
            }
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        List<TextView> mList;
        mList =  holder.getAllViews();
        if(isExpanded){
            
            for(int i=0; i< mList.size(); i++){
                mList.get(i).setTypeface(Typeface.DEFAULT_BOLD);
            }
            holder.tvTipoCobranca.setCompoundDrawablesWithIntrinsicBounds( R.mipmap.ic_chevron_up_grey600_24dp, 0, 0, 0);
        }else{
            for(int i=0; i< mList.size(); i++){
                mList.get(i).setTypeface(Typeface.DEFAULT);
            }
            holder.tvTipoCobranca.setCompoundDrawablesWithIntrinsicBounds( R.mipmap.ic_chevron_down_grey600_24dp, 0, 0, 0);
        }

        return convertView;
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
        holder.tvCodigoUnidade.setText("Cód.: "+mItens.getCodigoProduto()+ " / Unid.: " + mItens.getUnidadeMedidaSigla());
        holder.tvQtdPrecoValor.setText("Qtd.: "+mItens.getQuantidade()+ " / Preço: R$ " + mItens.getPrecoUnitario()+ " / Total: R$ "+mItens.getValorGeral());
        if(mItens.getDesconto().equals("0,00")){
            holder.tvDesconto.setVisibility(View.GONE);
        }else{
            holder.tvDesconto.setVisibility(View.VISIBLE);
            SpannableString stringEstilizada = new SpannableString("R$ "+mItens.getDesconto());
            stringEstilizada.setSpan(new StrikethroughSpan(),0,stringEstilizada.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            holder.tvDesconto.setText(stringEstilizada);
        }
        //tvDesconto.setText("Desc.: "+mList.get(childPosition).getValorDesc());*/
        return convertView;
    }

    class ViewHolderGroup{
        TextView tvData;
        TextView tvTipoCobranca;
        TextView tvCodigoMobile;
        TextView tvCodigo;
        TextView tvValor;
        TextView tvValorDesc;
        TextView tvPrazo;
        public List<TextView> getAllViews(){
            List<TextView> mList = new ArrayList<>();
            mList.add(tvData);
            mList.add(tvTipoCobranca);
            mList.add(tvCodigoMobile);
            mList.add(tvValor);
            mList.add(tvValorDesc);
            mList.add(tvPrazo);
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
