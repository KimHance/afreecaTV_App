package com.hence.data.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hence.data.response.BroadCastList.Broad
import com.hence.data.service.AfreecaService.Companion.STARTING_PAGE
import com.hence.data.source.datasource.AfreecaDataSource
import java.io.IOException

class AfreecaPagingSource(
    private val afreecaDataSource: AfreecaDataSource,
    private val selectKey: String,
    private val selectValue: String,
) : PagingSource<Int, Broad>() {

    override fun getRefreshKey(state: PagingState<Int, Broad>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Broad> {
        val position = params.key ?: STARTING_PAGE
        return try {
            val broadCastList =
                afreecaDataSource.getBroadCastList(
                    selectKey = selectKey,
                    selectValue = selectValue,
                    page = position
                )
            LoadResult.Page(
                data = broadCastList,
                prevKey = if (position == STARTING_PAGE) null else position - 1,
                nextKey = if (broadCastList.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        }
    }
}