# 📚 Biblioteca Unifor — App Android

Aplicativo mobile Android para a Biblioteca da Universidade de Fortaleza (Unifor), desenvolvido em **Kotlin** com arquitetura de Views tradicionais (XML + AppCompat). O projeto está em estágio inicial: a branch `feature/abertura-onboarding-screen` contém exclusivamente o fluxo de abertura do app (Splash Screen + Onboarding de 3 etapas).

---

## 📋 Índice

- [Visão Geral](#visão-geral)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Fluxo de Telas](#fluxo-de-telas)
- [Tecnologias e Dependências](#tecnologias-e-dependências)
- [Recursos Visuais](#recursos-visuais)
- [Como Executar](#como-executar)
- [Problemas Conhecidos e Débitos Técnicos](#problemas-conhecidos-e-débitos-técnicos)
- [Próximos Passos](#próximos-passos)

---

## Visão Geral

O app tem como objetivo centralizar informações sobre eventos, palestras, oficinas e atividades da Biblioteca Unifor, permitindo que alunos acompanhem a programação e participem das atividades oferecidas pela universidade.

**Status atual:** Apenas o fluxo de abertura (Splash + Onboarding) foi implementado. A tela principal do aplicativo ainda não existe.

---

## Estrutura do Projeto

```
BibliotecaUnifor/
├── app/
│   ├── build.gradle.kts                  # Configurações de build do módulo app
│   ├── proguard-rules.pro                # Regras de ofuscação (release)
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml       # Declaração de Activities e configurações do app
│           ├── java/com/example/uniforeventos/
│           │   ├── MainActivity.kt                # Splash Screen (ponto de entrada)
│           │   ├── OnboardingOneActivity.kt       # Onboarding — Tela 1
│           │   ├── OnboardingTwoActivity.kt       # Onboarding — Tela 2
│           │   ├── OnboardingThreeActivity.kt     # Onboarding — Tela 3
│           │   └── ui/theme/
│           │       ├── Color.kt                   # Paleta de cores (Compose — não utilizada)
│           │       ├── Theme.kt                   # Tema Material3 (Compose — não utilizado)
│           │       └── Type.kt                    # Tipografia (Compose — não utilizada)
│           └── res/
│               ├── drawable/
│               │   ├── bg_button_primary.xml      # Shape: botão azul arredondado
│               │   ├── bg_button_secondary.xml    # Shape: botão cinza-azulado arredondado
│               │   ├── ic_arrow_back.xml          # Ícone de seta para voltar
│               │   ├── logo_unifor.png            # Logotipo da Unifor
│               │   ├── onboarding_1_imagem.png    # Ilustração da tela 1 de onboarding
│               │   ├── onboarding_2_imagem.png    # Ilustração da tela 2 de onboarding
│               │   └── onboarding_3_imagem.png    # Ilustração da tela 3 de onboarding
│               ├── font/
│               │   ├── poppins_bold.ttf           # Fonte Poppins Bold (títulos)
│               │   ├── poppins_medium.ttf         # Fonte Poppins Medium (botões)
│               │   └── poppins_regular.ttf        # Fonte Poppins Regular (descrições)
│               ├── layout/
│               │   ├── activity_splash.xml        # Layout da Splash Screen
│               │   ├── activity_onboarding_one.xml
│               │   ├── activity_onboarding_two.xml
│               │   └── activity_onboarding_three.xml
│               └── values/
│                   ├── colors.xml                 # Cores do tema (AppCompat)
│                   ├── strings.xml                # Strings do app
│                   └── themes.xml                 # Tema AppCompat sem ActionBar
├── gradle/
│   ├── libs.versions.toml                # Catálogo central de versões e dependências
│   └── wrapper/
│       └── gradle-wrapper.properties     # Versão do Gradle utilizada
└── build.gradle.kts                      # Build script raiz do projeto
```

---

## Fluxo de Telas

```
[App Launch]
     │
     ▼
MainActivity (Splash Screen)
  • Exibe logo e nome "Biblioteca Unifor"
  • Aguarda 2 segundos (Handler + postDelayed)
  • Navega automaticamente para OnboardingOneActivity
  • Chama finish() — não volta para o Splash
     │
     ▼
OnboardingOneActivity — "Bem-vindo à Biblioteca Unifor"
  • Descreve eventos, palestras e atividades da biblioteca
  • Indicador de progresso: ponto 1 ativo (●○○)
  • [Pular] → sem implementação ⚠️
  • [Próximo] → OnboardingTwoActivity
     │
     ▼
OnboardingTwoActivity — "Descubra tudo o que vai acontecer"
  • Descreve a programação acadêmica, cultural e educacional
  • Indicador de progresso: ponto 2 ativo (○●○)
  • [Voltar] → finish() (retorna à tela anterior)
  • [Pular] → sem implementação ⚠️
  • [Próximo] → OnboardingThreeActivity
     │
     ▼
OnboardingThreeActivity — "Participe e aproveite mais a universidade"
  • Descreve recebimento de informações sobre eventos
  • Indicador de progresso: ponto 3 ativo (○○●)
  • [Voltar] → finish() (retorna à tela anterior)
  • [Pular] → sem implementação ⚠️
  • [Começar] → MainActivity (Splash) ⚠️ BUG: deveria ir para a tela principal
```

---

## Tecnologias e Dependências

### Linguagem
- **Kotlin** — linguagem principal do projeto

### Android SDK
| Configuração     | Valor        |
|-----------------|--------------|
| `minSdk`        | 24 (Android 7.0) |
| `targetSdk`     | 36           |
| `compileSdk`    | 36           |
| `versionCode`   | 1            |
| `versionName`   | 1.0          |

### Dependências principais

| Biblioteca | Versão | Uso |
|---|---|---|
| `androidx.appcompat` | 1.6.1 | Base das Activities (AppCompatActivity) |
| `androidx.constraintlayout` | 2.1.4 | Layout principal das telas |
| `androidx.cardview` | 1.0.0 | Card branco arredondado no rodapé |
| `com.google.android.material` | 1.11.0 | Estilo dos botões e tema base |
| `androidx.core.ktx` | 1.10.1 | Extensões Kotlin para APIs Android |
| `androidx.lifecycle.runtime.ktx` | 2.6.1 | Suporte a ciclo de vida reativo |

### Dependências de Compose (presentes, mas não utilizadas nas telas)
| Biblioteca | Observação |
|---|---|
| `androidx.activity.compose` | Importada, não usada |
| `androidx.compose.bom` | 2026.02.01 — versão muito recente |
| `androidx.compose.material3` | Importada, não usada |

### Ferramentas de Build
| Ferramenta | Versão |
|---|---|
| Android Gradle Plugin (AGP) | 9.1.1 |
| Kotlin | 2.2.10 |
| Gradle Wrapper | (definido em `gradle-wrapper.properties`) |

### Testes
| Biblioteca | Uso |
|---|---|
| `junit:4.13.2` | Testes unitários locais |
| `androidx.test.ext:junit` | Testes instrumentados |
| `espresso-core` | Testes de UI instrumentados |

---

## Recursos Visuais

### Tipografia
A fonte **Poppins** é utilizada em três variantes, todas incluídas localmente na pasta `res/font/`:
- `poppins_bold.ttf` — títulos das telas de onboarding
- `poppins_medium.ttf` — texto dos botões e nome do app no Splash
- `poppins_regular.ttf` — descrições nas telas de onboarding

### Paleta de Cores (usada nos layouts XML)
| Nome / Uso | Código Hex |
|---|---|
| Fundo das telas de onboarding | `#5265FF` (azul) |
| Fundo do Splash Screen | `#FFFFFF` (branco) |
| Texto do app no Splash | `#4A56FF` (azul escuro) |
| Botão primário (Próximo / Começar) | `#5265FF` (azul) |
| Botão secundário (Pular) | `#E1EBFF` (azul claro) |
| Texto botão secundário | `#214798` (azul navy) |
| Indicador ativo | `#3777FD` |
| Indicador inativo | `#E1EBFF` |

### Tema do App
- `Theme.AppCompat.DayNight.NoActionBar` — tema base sem barra de ação nativa

---

## Como Executar

**Pré-requisitos:**
- Android Studio Hedgehog ou superior
- JDK 11
- Dispositivo físico ou emulador com Android 7.0+ (API 24)

**Passos:**
1. Clone o repositório e mude para a branch:
   ```bash
   git clone https://github.com/gabrielneriqa/BibliotecaUnifor.git
   cd BibliotecaUnifor
   git checkout feature/abertura-onboarding-screen
   ```
2. Abra o projeto no Android Studio
3. Aguarde a sincronização do Gradle
4. Execute o app em um dispositivo ou emulador (`Run > Run 'app'`)

---

## Problemas Conhecidos e Débitos Técnicos

> Esta seção documenta issues reais encontrados no código atual. Devem ser corrigidos antes de evoluir o projeto.

### 🔴 Crítico

**1. Loop infinito de navegação**
`OnboardingThreeActivity` ao clicar em "Começar" navega para `MainActivity`, que é a Splash Screen. Isso cria um loop: o app exibe o splash novamente e volta para o onboarding indefinidamente. A tela principal do app ainda não existe, mas a navegação já deveria apontar para ela (ex.: `HomeActivity`).

**2. Botão "Pular" sem implementação**
Os três layouts de onboarding possuem um botão `btnSkip` visível e estilizado, mas nenhuma das três Activities define um `setOnClickListener` para ele. O botão existe na UI mas não faz nada.

**3. Onboarding exibido sempre que o app abre**
Não há verificação via `SharedPreferences` para saber se o usuário já concluiu o onboarding. A cada abertura do app, o fluxo inteiro de 3 telas é exibido novamente.

### 🟡 Importante

**4. Dependências Jetpack Compose não utilizadas**
O `build.gradle.kts` importa um conjunto completo de bibliotecas Compose (BOM, Material3, UI, tooling), mas nenhuma das telas as usa — toda a UI é feita com Views XML e AppCompat. Isso aumenta o tempo de build e o tamanho do APK sem nenhum benefício.

**5. Arquivos de tema Compose desconexos da UI real**
`Color.kt`, `Theme.kt` e `Type.kt` foram gerados automaticamente pelo template do Android Studio e contêm cores padrão (roxo/rosa) que não têm relação com a paleta visual do app (azul `#5265FF`). Estão em contradição direta com os recursos visuais reais.

**6. Cores hardcoded nos layouts XML**
As cores das telas (fundo, textos, botões) estão todas declaradas diretamente nos atributos XML (`android:background="#5265FF"`), em vez de referenciar os recursos de `res/values/colors.xml`. Isso torna a manutenção de tema e a futura implementação de dark mode muito mais difícil.

**7. Nome do pacote incoerente com o propósito do app**
O `applicationId` e o package name são `com.example.uniforeventos`, e `strings.xml` define `app_name` como "UniforEventos". O app, no entanto, é voltado para a **Biblioteca Unifor**, não para eventos gerais. Isso indica uma mudança de escopo que não foi refletida na identidade do projeto.

### 🔵 Melhorias futuras

**8. `Handler` deprecado para o delay do Splash**
`Handler(Looper.getMainLooper()).postDelayed(...)` funciona, mas a abordagem recomendada para Kotlin moderno é usar corrotinas (`lifecycleScope.launch { delay(2000) }`), que são mais legíveis e integradas ao ciclo de vida da Activity.

**9. Indicadores de progresso hardcoded**
Os pontos de progresso do onboarding (○●○) são `View` individuais com cores hardcoded em cada layout separado. Não há lógica centralizada. Qualquer alteração (ex.: adicionar uma 4ª tela) requer modificar os 3 layouts manualmente.

**10. Ausência de testes significativos**
Os arquivos `ExampleUnitTest.kt` e `ExampleInstrumentedTest.kt` são apenas os stubs gerados pelo template. Não há nenhum teste real implementado.

---

## Próximos Passos

Sugestões de evolução para as próximas branches, em ordem de prioridade:

1. **Criar `HomeActivity`** e corrigir a navegação do botão "Começar"
2. **Implementar SharedPreferences** para exibir o onboarding apenas na primeira abertura
3. **Implementar `btnSkip`** em todas as telas de onboarding
4. **Remover dependências Compose** se a decisão for manter Views XML, ou migrar as telas para Compose
5. **Centralizar cores** em `colors.xml` e remover valores hardcoded dos layouts
6. **Renomear pacote e app_name** para refletir corretamente o escopo do projeto (`bibliotecaunifor`)
7. **Refatorar o indicador de progresso** para um componente reutilizável
8. **Substituir Handler** por corrotinas no Splash

---

*Documentação gerada com base na branch `feature/abertura-onboarding-screen` — abril de 2026.*
