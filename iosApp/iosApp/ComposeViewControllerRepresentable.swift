//
//  ComposeViewControllerRepresentable.swift
//  iosApp
//
//  Created by cyril on 29/07/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import composeApp
import MapKit
import UIKit

// ComposeViewControllerRepresentable.swift
struct ComposeViewControllerRepresentable: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        return Main_iosKt.ComposeEntryPointWithUIViewController(createUIViewController: { () -> UIViewController in
            let swiftUIView = VStack {
                Text("SwiftUI in Compose Multiplatform")
                Text("J'ai réussie !!!!!!!!!!!!")
            }
            return UIHostingController(rootView: swiftUIView)
        })
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}

private class SwiftUIInUIView<Content: View>: UIView {
    init(content: Content) {
        super.init(frame: CGRect())
        let hostingController = UIHostingController(rootView: content)
        hostingController.view.translatesAutoresizingMaskIntoConstraints = false
        addSubview(hostingController.view)
        NSLayoutConstraint.activate([
            hostingController.view.topAnchor.constraint(equalTo: topAnchor),
            hostingController.view.leadingAnchor.constraint(equalTo: leadingAnchor),
            hostingController.view.trailingAnchor.constraint(equalTo: trailingAnchor),
            hostingController.view.bottomAnchor.constraint(equalTo: bottomAnchor)
        ])
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}




