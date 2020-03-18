package com.softhex.sonic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class sonicClientesDetalheComprasAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    //private List<sonicClientesDetalheComprasHolder> mList;
    private List<sonicClientesDetalheComprasItensHolder> mListItem;
    HashMap<List<sonicClientesDetalheComprasHolder>,List<sonicClientesDetalheComprasItensHolder>> mList = new HashMap<>();
    List<sonicClientesDetalheComprasHolder> mHeader = new ArrayList<>();
    HashMap<List<sonicClientesDetalheComprasHolder>, List<sonicClientesDetalheComprasItensHolder>> mChild = new HashMap<>();
    ImageView ivImagem;
    TextView tvData;
    TextView tvTipoCobranca;
    TextView tvCodigoMobile;
    TextView tvCodigo;
    TextView tvValor;
    TextView tvValorDesc;
    TextView tvPrazo;
    TextView tvNomeProduto;
    TextView tvCodigoUnidade;
    TextView tvQtdPrecoValor;
    TextView tvDesconto;

    public sonicClientesDetalheComprasAdapter(Context mContext, List<sonicClientesDetalheComprasHolder> listaHeader, HashMap<List<sonicClientesDetalheComprasHolder>, List<sonicClientesDetalheComprasItensHolder>> listaChild){
        this.mContext = mContext;
        this.mHeader = listaHeader;
        this.mChild = listaChild;
    }

    @Override
    public int getGroupCount() {
        return mHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChild.size();
        //return mChild.get(mHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChild.get(mHeader.get(groupPosition)).get(childPosition);
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
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if(convertView==null){
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout_cards_list_group,null);
        }

        tvData = convertView.findViewById(R.id.tvData);
        tvTipoCobranca = convertView.findViewById(R.id.tvTipoCobranca);
        tvCodigoMobile = convertView.findViewById(R.id.tvCodigoMobile);
        tvPrazo = convertView.findViewById(R.id.tvPrazo);
        tvValor = convertView.findViewById(R.id.tvValor);
        tvValorDesc = convertView.findViewById(R.id.tvValorDesc);
        tvData.setText(mHeader.get(groupPosition).getData());
        tvTipoCobranca.setText(mHeader.get(groupPosition).getTipoCobranca());
        tvCodigoMobile.setText("Cód.: " + (mHeader.get(groupPosition).getCodigoMobile().equals("") ? mHeader.get(groupPosition).getCodigo() : mHeader.get(groupPosition).getCodigoMobile()));
        tvPrazo.setText("Prazo: "+mHeader.get(groupPosition).getPrazo());
        tvValor.setText("R$ "+mHeader.get(groupPosition).getValor());

        if(mHeader.get(groupPosition).getValorDesc().equals("0,00")){
            tvValorDesc.setVisibility(View.GONE);
        }else{
            tvValorDesc.setVisibility(View.VISIBLE);
            tvValorDesc.setText("R$ "+mHeader.get(groupPosition).getValorDesc());
        }
        if(isExpanded){
            //tvTipoCobranca.setTypeface(null, Typeface.BOLD);
            //tvData.setTypeface(null, Typeface.BOLD);
            //tvData.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_chevron_up_grey600_24dp, 0);
        }else{
            //tvTipoCobranca.setTypeface(null, Typeface.NORMAL);
            //tvData.setTypeface(null, Typeface.NORMAL);
            //tvData.setCompoundDrawablesWithIntrinsicBounds( 0, 0, R.mipmap.ic_chevron_down_grey600_24dp, 0);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout_cards_list_item,null);
        }

        /*Log.d("TESTE", mChild.get(groupPosition).get(childPosition).getProduto());
        ivImagem = convertView.findViewById(R.id.ivImagem);
        tvNomeProduto = convertView.findViewById(R.id.tvNomeProduto);
        tvCodigoUnidade = convertView.findViewById(R.id.tvCodigoUnidade);
        tvQtdPrecoValor = convertView.findViewById(R.id.tvQtdPrecoValor);
        tvDesconto = convertView.findViewById(R.id.tvDesconto);
        File fileJpg = new File(Environment.getExternalStorageDirectory(), sonicConstants.LOCAL_IMG_CATALOGO + mChild.get(childPosition).get(groupPosition).getCodigoProduto() +".JPG");
        if(fileJpg.exists()){

            Glide.with(mContext)
                    .load(fileJpg)
                    .circleCrop()
                    .apply(new RequestOptions().override(100,100))
                    .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                    .into(ivImagem);

        }
        tvNomeProduto.setText(mChild.get(childPosition).get(childPosition).getProduto());
        tvCodigoUnidade.setText("Cód.: "+mChild.get(childPosition).get(childPosition).getCodigoProduto()+ " / Unid.: " + mChild.get(childPosition).get(childPosition).getUnidadeMedidaSigla());
        tvQtdPrecoValor.setText("Qtd.: "+mChild.get(childPosition).get(childPosition).getQuantidade()+ " / Preço: R$ " +mChild.get(childPosition).get(childPosition).getPrecoUnitario()+ " / Total: R$ "+mChild.get(childPosition).get(childPosition).getValorGeral());
        //tvDesconto.setText("Desc.: "+mList.get(childPosition).getValorDesc());*/
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
