//
//  AppDelegate.swift
//  iosApp
//
//  Created by Premiersoft on 01/07/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Firebase
import Foundation
import shared

class AppDelegate: NSObject, UIApplicationDelegate {
    
    var window: UIWindow?
    
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil
    ) -> Bool {
        FirebaseApp.configure()
        InitDIHelperKt.doInitKoin()
        return true
    }
}
