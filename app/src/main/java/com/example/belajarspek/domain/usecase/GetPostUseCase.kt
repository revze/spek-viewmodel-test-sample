package com.example.belajarspek.domain.usecase

import kotlinx.coroutines.delay

interface GetPostUseCase {
    suspend operator fun invoke(): List<String>
}

class GetPostUseCaseImpl :
    GetPostUseCase {
    override suspend fun invoke(): List<String> {
        delay(800)
        return mutableListOf(
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit",
            "Sed ut perspiciatis unde omnis iste natus",
            "Li Europan lingues es membres del sam familie"
        )
    }
}