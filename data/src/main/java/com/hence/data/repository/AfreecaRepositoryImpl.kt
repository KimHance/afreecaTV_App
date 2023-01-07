package com.hence.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hence.data.source.datasource.AfreecaDataSource
import com.hence.data.source.paging.AfreecaPagingSource
import com.hence.domain.model.Broadcast
import com.hence.domain.model.Category
import com.hence.domain.model.CategoryType
import com.hence.domain.repository.AfreecaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AfreecaRepositoryImpl @Inject constructor(
    private val afreecaDataSource: AfreecaDataSource
) : AfreecaRepository {
    override fun getBroadcastList(
        selectValue: String
    ): Flow<PagingData<Broadcast>> =
        Pager(PagingConfig(PAGE_SIZE)) {
            AfreecaPagingSource(afreecaDataSource, selectValue)
        }.flow

    override fun getCategoryList(): Flow<List<Category>> = flow {
        runCatching {
            afreecaDataSource.getCategoryList()
        }.onSuccess { list ->
            emit(list.filter { category ->
                category.number in listOf(
                    CategoryType.EAT.categoryNumber,
                    CategoryType.TALK.categoryNumber,
                    CategoryType.GAME.categoryNumber
                )
            })
        }.onFailure { exception ->
            throw exception
        }
    }

    companion object {
        const val PAGE_SIZE = 60
    }
}