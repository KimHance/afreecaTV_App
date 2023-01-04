package com.hence.domain.usecase

import com.hence.domain.repository.AfreecaRepository
import javax.inject.Inject


class GetCategoryListUseCase @Inject constructor(
    private val afreecaRepository: AfreecaRepository
) {
    operator fun invoke() = afreecaRepository.getCategoryList()
}