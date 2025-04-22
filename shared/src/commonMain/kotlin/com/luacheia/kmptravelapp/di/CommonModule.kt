package com.luacheia.kmptravelapp.di

import com.luacheia.kmptravelapp.data.datasource.local.AccommodationLocalDataSource
import com.luacheia.kmptravelapp.data.datasource.local.CategoryLocalDataSource
import com.luacheia.kmptravelapp.data.datasource.local.GuidanceLocalDataSource
import com.luacheia.kmptravelapp.data.datasource.local.InfoLocalDataSource
import com.luacheia.kmptravelapp.data.datasource.local.PlaceLocalDataSource
import com.luacheia.kmptravelapp.data.datasource.local.TimeLocalDataSource
import com.luacheia.kmptravelapp.data.datasource.local.model.RealmAccommodation
import com.luacheia.kmptravelapp.data.datasource.local.model.RealmCategory
import com.luacheia.kmptravelapp.data.datasource.local.model.RealmGuidance
import com.luacheia.kmptravelapp.data.datasource.local.model.RealmInfo
import com.luacheia.kmptravelapp.data.datasource.local.model.RealmPlace
import com.luacheia.kmptravelapp.data.datasource.local.model.RealmTimestamp
import com.luacheia.kmptravelapp.data.datasource.remote.AccommodationRemoteDataSource
import com.luacheia.kmptravelapp.data.datasource.remote.CategoryRemoteDataSource
import com.luacheia.kmptravelapp.data.datasource.remote.GuidanceRemoteDataSource
import com.luacheia.kmptravelapp.data.datasource.remote.InfoRemoteDataSource
import com.luacheia.kmptravelapp.data.datasource.remote.PlaceRemoteDataSource
import com.luacheia.kmptravelapp.data.manager.SyncManager
import com.luacheia.kmptravelapp.data.repository.AccommodationRepository
import com.luacheia.kmptravelapp.data.repository.CategoryRepository
import com.luacheia.kmptravelapp.data.repository.GuidanceRepository
import com.luacheia.kmptravelapp.data.repository.ImageManager
import com.luacheia.kmptravelapp.data.repository.InfoRepository
import com.luacheia.kmptravelapp.data.repository.PlaceRepository
import com.luacheia.kmptravelapp.data.repository.TimeRepository
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

    // Categories
    single { provideCategoryRepository(get(), get()) }
    single { provideCategoryRemoteDataSource(get()) }
    single { provideCategoryLocalDataSource(provideCategoryRealm()) }

    // Infos
    single { provideInfoRepository(get(), get()) }
    single { provideInfoRemoteDataSource(get()) }
    single { provideInfoLocalDataSource(provideInfoRealm()) }

    // Accommodations
    single { provideAccommodationRepository(get(), get()) }
    single { provideAccommodationRemoteDataSource(get()) }
    single { provideAccommodationLocalDataSource(provideAccommodationRealm()) }

    // Guidances
    single { provideGuidanceRepository(get(), get()) }
    single { provideGuidanceRemoteDataSource(get()) }
    single { provideGuidanceLocalDataSource(provideGuidanceRealm()) }

    // Time
    single { TimeRepository(get(), get()) }
    single { TimeLocalDataSource(provideTimestampRealm()) }

    // Remote Data
    single { provideFirestore() }

    // Local Image Manager
    single { provideImageManager() }

    // SyncManager
    single { SyncManager(get(), get(), get(), get(), get(), get()) }
}

// Places
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

// Categories
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

// Infos
private fun provideInfoRepository(
    infoRemoteDataSource: InfoRemoteDataSource,
    infoLocalDataSource: InfoLocalDataSource
) = InfoRepository(infoRemoteDataSource, infoLocalDataSource)

private fun provideInfoRemoteDataSource(
    firestore: FirebaseFirestore
) = InfoRemoteDataSource(firestore)

private fun provideInfoLocalDataSource(realm: Realm) = InfoLocalDataSource(realm)

private fun provideInfoRealm(): Realm {
    val config = RealmConfiguration.create(schema = setOf(RealmInfo::class))
    return Realm.open(config)
}

// Accommodations
private fun provideAccommodationRepository(
    accommodationRemoteDataSource: AccommodationRemoteDataSource,
    accommodationLocalDataSource: AccommodationLocalDataSource
) = AccommodationRepository(
    accommodationRemoteDataSource,
    accommodationLocalDataSource,
)

private fun provideAccommodationRemoteDataSource(
    firestore: FirebaseFirestore
) = AccommodationRemoteDataSource(firestore)

private fun provideAccommodationLocalDataSource(realm: Realm) = AccommodationLocalDataSource(realm)

private fun provideAccommodationRealm(): Realm {
    val config = RealmConfiguration.create(schema = setOf(RealmAccommodation::class))
    return Realm.open(config)
}

// Guidances
private fun provideGuidanceRepository(
    guidanceRemoteDataSource: GuidanceRemoteDataSource,
    guidanceLocalDataSource: GuidanceLocalDataSource,
) = GuidanceRepository(guidanceRemoteDataSource, guidanceLocalDataSource)

private fun provideGuidanceRemoteDataSource(
    firestore: FirebaseFirestore
) = GuidanceRemoteDataSource(firestore)

private fun provideGuidanceLocalDataSource(realm: Realm) = GuidanceLocalDataSource(realm)

private fun provideGuidanceRealm(): Realm {
    val config = RealmConfiguration.create(schema = setOf(RealmGuidance::class))
    return Realm.open(config)
}

// Time
private fun provideTimestampRealm(): Realm {
    val config = RealmConfiguration.create(schema = setOf(RealmTimestamp::class))
    return Realm.open(config)
}

// Firebase
private fun provideFirestore() = Firebase.firestore

// Local Image Manager
// TODO review usage of this class
private fun provideImageManager() = ImageManager()

