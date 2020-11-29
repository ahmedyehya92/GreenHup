package com.openet.usecases.usecases

import com.openet.usecases.repository.repository

class GetAboutUseCase {
    operator fun invoke(lang: String)= repository.getAbout(lang)
}