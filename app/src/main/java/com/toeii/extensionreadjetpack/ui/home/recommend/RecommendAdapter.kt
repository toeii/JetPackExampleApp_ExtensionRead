package com.toeii.extensionreadjetpack.ui.home.recommend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.stx.xhb.androidx.XBanner
import com.toeii.extensionreadjetpack.R
import com.toeii.extensionreadjetpack.base.BasePagedListAdapterObserver
import com.toeii.extensionreadjetpack.databinding.ViewListHeaderRecommendBinding
import com.toeii.extensionreadjetpack.databinding.ViewListItemFooterBinding
import com.toeii.extensionreadjetpack.databinding.ViewListItemRecommendBinding
import com.toeii.extensionreadjetpack.databinding.ViewVpItemRecommendBinding
import com.toeii.extensionreadjetpack.entity.RecommendBannerItem
import com.toeii.extensionreadjetpack.entity.ViceResult

class RecommendAdapter : PagedListAdapter<ViceResult, RecyclerView.ViewHolder>(diffCallback) {

    internal var isLoadMore = true

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> ITEM_TYPE_HEADER
            itemCount - 1 -> ITEM_TYPE_FOOTER
            else -> super.getItemViewType(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TYPE_HEADER -> HeaderViewHolder(parent)
            ITEM_TYPE_FOOTER -> FooterViewHolder(parent)
            else -> RecommendViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.bindsHeader(currentList?.get(0))
            is FooterViewHolder -> holder.bindsFooter(isLoadMore)
            is RecommendViewHolder -> holder.bindTo(getDataItem(position))
        }
    }

    private fun getDataItem(position: Int): ViceResult? {
        return getItem(position-1)
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + 2
    }

    override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.registerAdapterDataObserver(BasePagedListAdapterObserver(observer, 1))
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ViceResult>() {
            override fun areItemsTheSame(oldItem: ViceResult, newItem: ViceResult): Boolean {
                return oldItem._id == newItem._id
            }


            override fun areContentsTheSame(oldItem: ViceResult, newItem: ViceResult): Boolean {
                return oldItem == newItem
            }

        }

        private const val ITEM_TYPE_HEADER = 99
        private const val ITEM_TYPE_FOOTER = 100
    }
}

class RecommendViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.view_list_item_recommend, parent, false)) {

    private lateinit var mBinding : ViewListItemRecommendBinding
    private var viceResult: ViceResult? = null

    fun bindTo(viceResult: ViceResult?) {
        this.viceResult = viceResult
        mBinding = initViewBindingImpl(itemView) as ViewListItemRecommendBinding
        mBinding.item = viceResult
    }

}

internal class HeaderViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.view_list_header_recommend, parent, false)) {

    private lateinit var mHeaderBinding : ViewListHeaderRecommendBinding
    private lateinit var mVpBinding : ViewVpItemRecommendBinding
    private val mHeadViewAdapter: XBanner.XBannerAdapter by lazy { getHeadPagerAdapter() }

    fun bindsHeader(viceResult: ViceResult?) {
        mHeaderBinding = initViewBindingImpl(itemView) as ViewListHeaderRecommendBinding
        if (viceResult != null) {
            mHeaderBinding.itemPager.setBannerData(R.layout.view_vp_item_recommend,viceResult.bannerData)
            mHeaderBinding.itemPager.loadImage(mHeadViewAdapter)
            mHeaderBinding.itemTitleText.text = "精彩推荐"
        }
    }

    private fun getHeadPagerAdapter(): XBanner.XBannerAdapter {
        return XBanner.XBannerAdapter { _, model, view, _ ->
            mVpBinding = initViewBindingImpl(view) as ViewVpItemRecommendBinding
            mVpBinding.bannerItem = model as RecommendBannerItem
        }
    }

}

internal class FooterViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.view_list_item_footer, parent, false)) {

    private lateinit var mFooterBinding : ViewListItemFooterBinding

    fun bindsFooter(isLoadMore: Boolean) {
        mFooterBinding = initViewBindingImpl(itemView) as ViewListItemFooterBinding
        if(isLoadMore){
            mFooterBinding.tvBanner.text = "加载中..."
        }else{
            mFooterBinding.tvBanner.text = "没有更多了"
        }
    }

}

private fun initViewBindingImpl(itemView: View): ViewDataBinding? {
    return if (null == DataBindingUtil.getBinding(itemView)) {
        DataBindingUtil.bind(itemView)
    } else {
        DataBindingUtil.getBinding(itemView)
    }
}