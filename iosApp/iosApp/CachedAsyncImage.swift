//
//  CachedAsyncImage.swift
//  iosApp
//
//  Created by Andrey de Lara on 11/04/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct CachedAsyncImage<Content: View, Placeholder: View>: View {
    
    let url: URL
    let content: (Image) -> Content
    let placeholder: () -> Placeholder
    
    @State private var cachedImage: Image?
    private let imageManager = ImageManager.shared
    
    var body: some View {
        Group {
            if let cachedImage {
                content(cachedImage)
            } else {
                AsyncImage(url: url) { phase in
                    switch phase {
                    case .success(let image):
                        content(image)
                    case .failure:
                        placeholder()
                    default:
                        placeholder()
                    }
                }
            }
        }
        .onAppear {
            guard cachedImage == nil else { return }
            loadCachedImage()
        }
    }
    
    private func loadCachedImage() {
        guard let cachedUIImage = imageManager.loadUIImage(from: url.absoluteString) else { return }
        cachedImage = Image(uiImage: cachedUIImage)
    }
}
