package com.abelvolpi.kmptravelapp.di

import com.abelvolpi.kmptravelapp.data.datasource.local.CategoryLocalDataSource
import com.abelvolpi.kmptravelapp.data.datasource.local.PlaceLocalDataSource
import com.abelvolpi.kmptravelapp.data.datasource.local.model.RealmCategory
import com.abelvolpi.kmptravelapp.data.datasource.local.model.RealmPlace
import com.abelvolpi.kmptravelapp.data.datasource.remote.AccommodationRemoteDataSource
import com.abelvolpi.kmptravelapp.data.datasource.remote.CategoryRemoteDataSource
import com.abelvolpi.kmptravelapp.data.datasource.remote.GuidanceRemoteDataSource
import com.abelvolpi.kmptravelapp.data.datasource.remote.InfoRemoteDataSource
import com.abelvolpi.kmptravelapp.data.datasource.remote.PlaceRemoteDataSource
import com.abelvolpi.kmptravelapp.data.repository.AccommodationRepository
import com.abelvolpi.kmptravelapp.data.repository.CategoryRepository
import com.abelvolpi.kmptravelapp.data.repository.GuidanceRepository
import com.abelvolpi.kmptravelapp.data.repository.InfoRepository
import com.abelvolpi.kmptravelapp.data.repository.PlaceRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module

fun appModule() = listOf(appModule)

val appModule = module {

    // Places
    single { providePlaceRepository(get(), get()) }
    single { providePlaceRemoteDataSource(get()) }
    single { providePlaceLocalDataSource(providePlaceRealm()) }
    // single { providePlaceRealm() }

    // Categories
    single { provideCategoryRepository(get(), get()) }
    single { provideCategoryRemoteDataSource(get()) }
    single { provideCategoryLocalDataSource(provideCategoryRealm()) }
    // single { provideCategoryRealm() }

    // Infos
    single { provideInfoRepository(get()) }
    single { provideInfoRemoteDataSource(get()) }

    // Accommodation
    single { provideAccommodationRepository(get()) }
    single { provideAccommodationRemoteDataSource(get()) }

    // Guidances
    single { provideGuidanceRepository(get()) }
    single { provideGuidanceRemoteDataSource(get()) }

    // Remote Data
    single { provideFirestore() }
}

private fun providePlaceRepository(
    placeRemoteDataSource: PlaceRemoteDataSource,
    placeLocalDataSource: PlaceLocalDataSource
) = PlaceRepository(placeRemoteDataSource, placeLocalDataSource)

private fun providePlaceRemoteDataSource(
    firestore: FirebaseFirestore
) = PlaceRemoteDataSource(firestore)

private fun providePlaceLocalDataSource(realm: Realm) = PlaceLocalDataSource(realm)

private fun providePlaceRealm(): Realm {
    val config = RealmConfiguration.create(schema = setOf(RealmPlace::class))
    return Realm.open(config)
}

private fun provideCategoryRepository(
    categoryRemoteDataSource: CategoryRemoteDataSource,
    categoryLocalDataSource: CategoryLocalDataSource
) = CategoryRepository(categoryRemoteDataSource, categoryLocalDataSource)

private fun provideCategoryRemoteDataSource(
    firestore: FirebaseFirestore
) = CategoryRemoteDataSource(firestore)

private fun provideCategoryLocalDataSource(realm: Realm) = CategoryLocalDataSource(realm)

private fun provideCategoryRealm(): Realm {
    val config = RealmConfiguration.create(schema = setOf(RealmCategory::class))
    return Realm.open(config)
}

private fun provideInfoRepository(
    infoRemoteDataSource: InfoRemoteDataSource
) = InfoRepository(infoRemoteDataSource)

private fun provideInfoRemoteDataSource(
    firestore: FirebaseFirestore
) = InfoRemoteDataSource(firestore)

private fun provideAccommodationRepository(
    accommodationRemoteDataSource: AccommodationRemoteDataSource
) = AccommodationRepository(accommodationRemoteDataSource)

private fun provideAccommodationRemoteDataSource(
    firestore: FirebaseFirestore
) = AccommodationRemoteDataSource(firestore)

private fun provideGuidanceRepository(
    guidanceRemoteDataSource: GuidanceRemoteDataSource
) = GuidanceRepository(guidanceRemoteDataSource)

private fun provideGuidanceRemoteDataSource(
    firestore: FirebaseFirestore
) = GuidanceRemoteDataSource(firestore)

private fun provideFirestore() = Firebase.firestore
