package com.hence.domain.usecase

import com.hence.domain.repository.AfreecaRepository
import javax.inject.Inject

class GetBroadcastListUseCase @Inject constructor(
    private val afreecaRepository: AfreecaRepository
) {
    operator fun invoke(selectValue: String) = afreecaRepository.getBroadcastList(selectValue)
}