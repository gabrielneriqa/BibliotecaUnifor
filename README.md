# UniforEventos

Aplicativo Android desenvolvido em Kotlin com interface em XML, com foco inicial na construção do fluxo de autenticação, onboarding e navegação básica entre telas.

---

## Status atual do projeto

Até o momento, o projeto já possui:

- Splash screen funcional
- Tela de login com autenticação mockada
- Fluxo de recuperação de senha
- Fluxo de onboarding em 3 etapas
- Tela de livros reservados
- Navegação básica entre as telas via `Intent`
- Bottom navigation estruturada visualmente
- Menu lateral estruturado visualmente na tela de livros reservados

---

## Fluxo navegável implementado

O fluxo atual da aplicação está organizado da seguinte forma:

- Splash
- → Login
- → Onboarding 1
- → Onboarding 2
- → Onboarding 3
- → Livros Reservados

### Fluxo alternativo de recuperação de senha

- Login
- → Esqueceu a senha?
- → Redefinir Senha
- → Email Enviado
- → Retorno ao Login

---

## Credenciais mockadas para teste

Atualmente o login está mockado para fins de validação do fluxo.

Use as seguintes credenciais:

- **Matrícula:** `20240001`
- **Senha:** `123456`

Caso os dados estejam incorretos, o app exibe uma mensagem de erro informando que as credenciais são inválidas.

---

## Telas já implementadas

### 1. Splash
**Arquivo de layout:** `activity_splash.xml`  
**Activity:** `MainActivity.kt`

Responsável por exibir a tela inicial e redirecionar automaticamente para a tela de login.

---

### 2. Login
**Arquivo de layout:** `activity_login.xml`  
**Activity:** `LoginActivity.kt`

Funcionalidades atuais:
- Campo de matrícula
- Campo de senha
- Exibição/ocultação de senha
- Validação mockada de credenciais
- Redirecionamento para onboarding
- Acesso à recuperação de senha

---

### 3. Redefinir Senha
**Arquivo de layout:** `activity_redefinir_senha.xml`  
**Activity:** `RedefinirSenhaActivity.kt`

Funcionalidades atuais:
- Campo de e-mail
- Validação básica de preenchimento
- Verificação de formato de e-mail
- Redirecionamento para a tela de confirmação de envio

---

### 4. Email Enviado
**Arquivo de layout:** `activity_email_enviado.xml`  
**Activity:** `EmailEnviadoActivity.kt`

Funcionalidades atuais:
- Exibição da confirmação de envio
- Retorno à tela de login

---

### 5. Onboarding
**Arquivos de layout:**
- `activity_onboarding_one.xml`
- `activity_onboarding_two.xml`
- `activity_onboarding_three.xml`

**Activities:**
- `OnboardingOneActivity.kt`
- `OnboardingTwoActivity.kt`
- `OnboardingThreeActivity.kt`

Funcionalidades atuais:
- Navegação entre as etapas
- Botão de voltar nas telas intermediárias
- Botão de pular com redirecionamento para a tela final
- Finalização do fluxo na tela de livros reservados

---

### 6. Livros Reservados
**Arquivo de layout:** `activity_livros_reservados.xml`  
**Activity:** `LivrosReservadosActivity.kt`

Elementos já implementados:
- Header com identidade visual
- Ícone de menu no topo esquerdo
- Campo de pesquisa estilizado
- Botão de filtros
- Card de livro com imagem mockada
- Informações mockadas de livro
- Botão "Estender empréstimo"
- Bottom navigation visual
- Sidebar estrutural preparada para evolução futura

---

## Estrutura principal do projeto

### Activities implementadas
- `MainActivity`
- `LoginActivity`
- `RedefinirSenhaActivity`
- `EmailEnviadoActivity`
- `OnboardingOneActivity`
- `OnboardingTwoActivity`
- `OnboardingThreeActivity`
- `LivrosReservadosActivity`
- `IdiomasActivity` *(fora do fluxo principal no momento)*

### Layouts relevantes
- `activity_splash.xml`
- `activity_login.xml`
- `activity_redefinir_senha.xml`
- `activity_email_enviado.xml`
- `activity_onboarding_one.xml`
- `activity_onboarding_two.xml`
- `activity_onboarding_three.xml`
- `activity_livros_reservados.xml`

---

## Recursos visuais já adicionados

O projeto já conta com diversos recursos visuais em `res/drawable`, incluindo:

- backgrounds customizados
- ícones da navegação inferior
- ícones de menu, busca, filtro e voltar
- imagem mockada de livro
- imagens das telas de onboarding
- logo da aplicação

Também foram adicionadas fontes customizadas:
- Poppins Regular
- Poppins Medium
- Poppins Bold

---

## O que está mockado no momento

Algumas partes foram implementadas com mock para permitir validação visual e de fluxo:

- autenticação do login
- dados do livro na tela de livros reservados
- imagem da capa do livro
- navegação inferior sem redirecionamento real
- menu lateral sem abertura funcional

Esses pontos ainda não estão conectados a backend, banco de dados ou API real.

---

## Decisões de implementação adotadas até agora

### Navegação
A navegação atual foi feita com `Intent` entre `Activities`, respeitando a estrutura existente do projeto.

### Estilo
A interface foi construída com XML, drawables customizados e componentes visuais próprios, sem migração para Compose.

### Escopo
Nesta etapa, o foco foi:
- fazer o fluxo funcionar
- deixar a experiência visual coerente
- preparar base para evolução futura

Não foi objetivo desta fase:
- autenticação real
- persistência de sessão
- integração com backend
- navegação completa por menu lateral e bottom nav

---

## Próximos passos recomendados

Os próximos passos mais coerentes para evolução do projeto são:

1. Implementar autenticação real
2. Conectar a tela de livros a dados dinâmicos
3. Tornar a bottom navigation funcional
4. Implementar abertura real do menu lateral
5. Criar persistência para evitar repetir onboarding após primeiro acesso
6. Melhorar validações e feedbacks de erro no login e recuperação de senha
7. Estruturar melhor estados visuais e navegação global

---

## Como executar o projeto

1. Abrir o projeto no Android Studio
2. Sincronizar o Gradle
3. Executar em emulador ou dispositivo físico
4. Utilizar as credenciais mockadas para acessar o fluxo principal

---

## Observação importante

Este projeto já possui uma base visual e de navegação funcional, mas ainda está em fase de consolidação estrutural. Parte das interações foi implementada de forma mockada para permitir evolução incremental sem bloquear o andamento da interface.
