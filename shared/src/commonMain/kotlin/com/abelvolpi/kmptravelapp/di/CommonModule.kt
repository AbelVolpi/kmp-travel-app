package com.abelvolpi.kmptravelapp.di

import com.abelvolpi.kmptravelapp.data.datasource.local.CategoryLocalDataSource
import com.abelvolpi.kmptravelapp.data.datasource.local.model.RealmCategory
import com.abelvolpi.kmptravelapp.data.datasource.remote.CategoryRemoteDataSource
import com.abelvolpi.kmptravelapp.data.repository.CategoryRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module

fun appModule() = listOf(appModule)

val appModule = module {
    single { provideCategoryRepository(get(), get()) }
    single { provideCategoryRemoteDataSource(get()) }
    single { provideCategoryLocalDataSource(get()) }
    single { provideFirestore() }
    single { provideRealm() }
}

private fun provideCategoryRepository(
    categoryRemoteDataSource: CategoryRemoteDataSource,
    categoryLocalDataSource: CategoryLocalDataSource
) = CategoryRepository(categoryRemoteDataSource, categoryLocalDataSource)

private fun provideCategoryRemoteDataSource(
    firestore: FirebaseFirestore
) = CategoryRemoteDataSource(firestore)

private fun provideFirestore() = Firebase.firestore

private fun provideCategoryLocalDataSource(realm: Realm) = CategoryLocalDataSource(realm)

private fun provideRealm(): Realm {
    val config = RealmConfiguration.create(schema = setOf(RealmCategory::class))
    return Realm.open(config)
}