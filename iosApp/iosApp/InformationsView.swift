//
//  InformationsView.swift
//  iosApp
//
//  Created by Andrey de Lara on 22/04/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct InformationsView: View {
    
    @State private var sheetIsPresented: Bool = false
    @State private var informationType: InformationType?
    
    var body: some View {
        ScrollView(.vertical, showsIndicators: false) {
            VStack(spacing: 0) {
                whatsAppButton
                
                Divider()
                    .padding([.top, .horizontal], 20)
                
                createListCell(
                    Text("Teste"),
                    icon: "HouseIcon",
                    title: "Acomodações",
                    description: "Veja outras acomodações dos Chalés Lua Cheia",
                    informationType: .accommodations
                )
                
                createListCell(
                    Text("Teste"),
                    icon: "WindIcon",
                    title: "Ar-Condicionado",
                    description: "Cuidados com o ar-condicionado",
                    informationType: .airConditioning
                )
                
                createListCell(
                    Text("Teste"),
                    icon: "TrashIcon",
                    title: "Lixo",
                    description: "Instruções de como lidar com o lixo do chalé",
                    informationType: .trash
                )
                
                createListCell(
                    Text("Teste"),
                    icon: "FireplaceIcon",
                    title: "Lareira",
                    description: "Instruções e cuidados sobre o uso da lareira",
                    informationType: .fireplace
                )
                
                createListCell(
                    Text("Teste"),
                    icon: "StoveIcon",
                    title: "Fogão",
                    description: "Instruções e cuidados sobre o uso do fogão",
                    informationType: .stove
                )
                
                createListCell(
                    Text("Teste"),
                    icon: "DogIcon",
                    title: "Pet Friendly",
                    description: "Instruções e cuidados com o seu pet dentro do chalé",
                    informationType: .petFriendly
                )
            }
        }
        .navigationTitle("Chalés Lua Cheia")
        .background(Color.gray2)
        .sheet(item: $informationType) { informationType in
            InformationGuideView(
                title: informationType.title,
                description: informationType.description
            )
        }
    }
}

extension InformationsView {
    private var whatsAppButton: some View {
        RoundedRectangle(cornerRadius: 15)
            .foregroundColor(.gray1)
            .overlay {
                HStack(spacing: 20) {
                    Image("WhatsAppIcon")
                    Text("Entre em contato conosco através do WhatsApp")
                        .font(.system(size: 18, weight: .bold))
                    Spacer()
                }
                .padding(.horizontal, 20)
            }
            .frame(height: 86)
            .padding(.horizontal, 20)
            .onTapGesture {
                let urlString = "https://api.whatsapp.com/send?phone=+554797332507&text=Ol%C3%A1"
                
                if let url = URL(string: urlString), UIApplication.shared.canOpenURL(url) {
                    UIApplication.shared.open(url, options: [:], completionHandler: nil)
                } else {
                    
                }
            }
    }
    
    private func createListCell(
        _ destination: some View,
        icon: String,
        title: String,
        description: String,
        informationType: InformationType
    ) -> some View {
        VStack(spacing: 0) {
            Spacer()
            HStack(spacing: 20) {
                Image(icon)
                    .resizable()
                    .scaledToFit()
                    .frame(width: 34, height: 34, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
                VStack(alignment: .leading, spacing: 0) {
                    Text(title)
                        .font(.system(size: 18, weight: .bold))
                    Text(description)
                        .font(.system(size: 14, weight: .regular))
                        .multilineTextAlignment(.leading)
                }
                Spacer()
                Image(systemName: "chevron.right")
            }
            Spacer()
            Divider()
        }
        .padding(.horizontal, 20)
        .frame(height: 90)
        .onTapGesture {
            self.informationType = informationType
        }
    }
}

#Preview {
    NavigationStack {
        InformationsView()
    }
    .preferredColorScheme(.dark)
}

enum InformationType: Identifiable {
    var id: UUID { UUID() }
    
    case accommodations
    case airConditioning
    case trash
    case fireplace
    case stove
    case petFriendly
    
    var title: String {
        switch self {
        case .accommodations:
            return "Acomodações"
        case .airConditioning:
            return "Ar-Condicionado"
        case .trash:
            return "Lixo"
        case .fireplace:
            return "Lareira"
        case .stove:
            return "Fogão"
        case .petFriendly:
            return "Pet Friendly"
        }
    }
    
    var description: AttributedString {
        switch self {
        case .accommodations:
            var firstPart = AttributedString("O Ar-Condicionado possuí as funções QUENTE e FRIO, fique a vontade para utilizar o que desejar, o controle é disponibilizado no momento do Check-in.\n\n")
            firstPart.font = .systemFont(ofSize: 18, weight: .regular)
            firstPart.foregroundColor = .white
            return firstPart
        case .airConditioning:
            var firstPart = AttributedString("O Ar-Condicionado possuí as funções QUENTE e FRIO, fique a vontade para utilizar o que desejar, o controle é disponibilizado no momento do Check-in.\n\n")
            firstPart.font = .systemFont(ofSize: 18, weight: .regular)
            firstPart.foregroundColor = .white
            var secondPart = AttributedString("Pedimos encarecidamente que o Ar-Condicionado seja desligado nos momentos em que não estiverem nos Chalés. ")
            secondPart.font = .systemFont(ofSize: 18, weight: .bold)
            secondPart.foregroundColor = .white
            var thirdPart = AttributedString("O espaço é bem compacto e os aparelhos são potentes e revisados, rapidamente o ambiente estará refrigerado ou aquecido como preferirem.\n\nA utilização consciente evita o desperdício e ajuda no consumo consciente de energia. Desde já, agradecemos sua compreensão!")
            thirdPart.font = .systemFont(ofSize: 18, weight: .regular)
            thirdPart.foregroundColor = .white
            return firstPart + secondPart + thirdPart
        case .trash:
            var firstPart = AttributedString("O lixo concentrado em sua estadia pode ser posto em sacolas no lado de fora, na lateral do chalé, passamos recolhendo conforme demanda. Se preferir, pode deixar para o lado de dentro até o final da estadia.\n\n")
            firstPart.font = .systemFont(ofSize: 18, weight: .regular)
            firstPart.foregroundColor = .white
            var secondPart = AttributedString("CUIDADO: ")
            secondPart.font = .systemFont(ofSize: 18, weight: .bold)
            secondPart.foregroundColor = .white
            var thirdPart = AttributedString("Evite que os lixos com mal cheiro ou com líquidos fiquem no lado interno do chalé, coloque-os para o lado de fora, recolhemos assim que possível. Dessa forma, evitamos manchas no assoalho e possíveis imprevistos.")
            thirdPart.font = .systemFont(ofSize: 18, weight: .regular)
            thirdPart.foregroundColor = .white
            return firstPart + secondPart + thirdPart
        case .fireplace:
            var firstPart = AttributedString("O lixo concentrado em sua estadia pode ser posto em sacolas no lado de fora, na lateral do chalé, passamos recolhendo conforme demanda. Se preferir, pode deixar para o lado de dentro até o final da estadia.\n\n")
            firstPart.font = .systemFont(ofSize: 18, weight: .regular)
            firstPart.foregroundColor = .white
            var secondPart = AttributedString("CUIDADO: ")
            secondPart.font = .systemFont(ofSize: 18, weight: .bold)
            secondPart.foregroundColor = .white
            var thirdPart = AttributedString("Evite que os lixos com mal cheiro ou com líquidos fiquem no lado interno do chalé, coloque-os para o lado de fora, recolhemos assim que possível. Dessa forma, evitamos manchas no assoalho e possíveis imprevistos.")
            thirdPart.font = .systemFont(ofSize: 18, weight: .regular)
            thirdPart.foregroundColor = .white
            return firstPart + secondPart + thirdPart
        case .stove:
            var firstPart = AttributedString("Ligue-o na tomada e gire o botão na intensidade que desejar. Recomendamos girá-lo até a intensidade 5, pois ele demora esquentar. Depois, controle a temperatura de acordo com o preparo do seu alimento.")
            firstPart.font = .systemFont(ofSize: 18, weight: .regular)
            firstPart.foregroundColor = .white
            return firstPart
        case .petFriendly:
            return "Pet Friendly"
        }
    }
}
