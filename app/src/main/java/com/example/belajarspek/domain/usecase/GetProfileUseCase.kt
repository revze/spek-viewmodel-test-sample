package com.example.belajarspek.domain.usecase

import kotlinx.coroutines.delay

interface GetProfileUseCase {
    suspend operator fun invoke(): String
}

class GetProfileUseCaseImpl :
    GetProfileUseCase {
    override suspend fun invoke(): String {
        delay(800)
        return "Revando"
    }
}