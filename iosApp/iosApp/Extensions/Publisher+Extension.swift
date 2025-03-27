//
//  Publisher+Extension.swift
//  iosApp
//
//  Created by Andrey de Lara on 05/10/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Combine
import Foundation

extension Publisher where Self.Failure == Never {
    func asyncSink(receiveValue: @escaping ((Self.Output) async -> Void)) -> AnyCancellable {
        sink { value in
            Task { await receiveValue(value) }
        }
    }
}

extension Publisher where Output: Equatable {
    func debounceSink(
        debounceTime: DispatchQueue.SchedulerTimeType.Stride = .milliseconds(400),
        scheduler: DispatchQueue = .main,
        action: @escaping () async -> Void
    ) -> AnyCancellable {
        removeDuplicates()
            .debounce(for: debounceTime, scheduler: scheduler)
            .sink(
                receiveCompletion: { _ in },
                receiveValue: { value in Task { await action() } }
            )
    }
}
