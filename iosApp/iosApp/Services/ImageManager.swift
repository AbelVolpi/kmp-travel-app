//
//  ImageManager.swift
//  iosApp
//
//  Created by Andrey de Lara on 01/02/25.
//  Copyright © 2025 orgName. All rights reserved.
//


import CryptoKit
import Foundation
import SwiftUI

enum ImageManagerError: Error {
    case invalidDirectory
}

final class ImageManager {
    
    static let shared = ImageManager()
    
    private let fileManager = FileManager.default
    private let directoryURL: URL?
    
    private init() {
        let appSupportURL = fileManager.urls(for: .applicationSupportDirectory, in: .userDomainMask).first
        
        if let appSupportURL {
            directoryURL = appSupportURL.appendingPathComponent("Images")
        } else {
            directoryURL = nil
        }
        
        guard let directoryURL, !fileManager.fileExists(atPath: directoryURL.path) else { return }
        
        do {
            try fileManager.createDirectory(at: directoryURL, withIntermediateDirectories: true, attributes: nil)
        } catch {
            print("Erro ao criar diretório: \(error)")
        }
    }
}

extension ImageManager {
    func downloadAndSaveImage(from urlString: String) async throws {
        guard let url = URL(string: urlString) else { throw URLError(.badURL) }
        
        let (data, _) = try await URLSession.shared.data(from: url)
        
        try saveImageLocally(data: data, id: hashURL(urlString))
    }
    
    private func hashURL(_ urlString: String) -> String {
        let data = Data(urlString.utf8)
        let hashed = SHA256.hash(data: data)
        return hashed.compactMap { String(format: "%02x", $0) }.joined()
    }
    
    private func saveImageLocally(data: Data, id: String) throws {
        guard let directoryURL else { throw ImageManagerError.invalidDirectory }
        
        let fileName = "\(id).jpg"
        let fileURL = directoryURL.appendingPathComponent(fileName)
        
        try data.write(to: fileURL)
    }
    
    private func getImagePath(from urlString: String) -> URL? {
        let id = hashURL(urlString)
        return getImagePath(for: "\(id).jpg")
    }
    
    func getImagePath(for fileName: String) -> URL? {
        guard let directoryURL else { return nil }
        let fileURL = directoryURL.appendingPathComponent(fileName)
        let fileExists = fileManager.fileExists(atPath: fileURL.path)
        return fileExists ? fileURL : nil
    }
    
    func loadImage(from urlString: String) -> Image? {
        guard let path = getImagePath(from: urlString),
              let uiImage = UIImage(contentsOfFile: path.path)
        else { return nil }
        
        return Image(uiImage: uiImage)
    }
    
    func loadUIImage(from urlString: String) -> UIImage? {
        guard let path = getImagePath(from: urlString) else { return nil }
        return UIImage(contentsOfFile: path.path)
    }
    
    func deleteImage(from urlString: String) throws {
        let id = hashURL(urlString)
        try deleteImage(named: "\(id).jpg")
    }
    
    func deleteImage(named fileName: String) throws {
        guard let directoryURL else { return }
        
        let fileURL = directoryURL.appendingPathComponent(fileName)
        
        if fileManager.fileExists(atPath: fileURL.path) {
            try fileManager.removeItem(at: fileURL)
        }
    }
    
    func deleteAllImages() {
        guard let directoryURL else { return }
        try? fileManager.removeItem(at: directoryURL)
    }
}
