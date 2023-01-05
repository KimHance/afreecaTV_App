package com.hence.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hence.data.source.datasource.AfreecaDataSource
import com.hence.data.source.paging.AfreecaPagingSource
import com.hence.di.DispatcherModule
import com.hence.domain.model.Broadcast
import com.hence.domain.model.Category
import com.hence.domain.model.CategoryType
import com.hence.domain.repository.AfreecaRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AfreecaRepositoryImpl @Inject constructor(
    private val afreecaDataSource: AfreecaDataSource,
    @DispatcherModule.DispatcherIO private val dispatcherIO: CoroutineDispatcher
) : AfreecaRepository {
    override fun getBroadcastList(
        selectValue: String
    ): Flow<PagingData<Broadcast>> =
        Pager(PagingConfig(PAGE_SIZE)) {
            AfreecaPagingSource(afreecaDataSource, selectValue)
        }.flow.flowOn(dispatcherIO)

    override fun getCategoryList(): Flow<List<Category>> = flow {
        emit(afreecaDataSource.getCategoryList().filter { category ->
            category.name in listOf(
                CategoryType.EAT.categoryName,
                CategoryType.TALK.categoryName,
                CategoryType.GAME.categoryName
            )
        })
    }.flowOn(dispatcherIO)

    companion object {
        const val PAGE_SIZE = 60
    }
}