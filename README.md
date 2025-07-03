WeatherApp

WeatherApp es una aplicación de pronóstico del tiempo moderna y responsiva para Android, diseñada para demostrar las mejores prácticas en el desarrollo de aplicaciones móviles. Construida con Jetpack Compose, sigue rigurosamente los principios de Clean Architecture y el patrón MVVM (Model-View-ViewModel), garantizando una base de código robusta, escalable y fácil de mantener.
🚀 Instrucciones para Compilar y Ejecutar la App

Sigue estos pasos para poner en marcha el proyecto en tu máquina local:

    Clonar el Repositorio:

    git clone https://github.com/alfredorespal97/weatherapp.git
    cd WeaterApp
    
    Abrir en Android Studio:

        Abre Android Studio.

        Selecciona File > Open y navega hasta el directorio WeaterApp que acabas de clonar.

        Espera a que Android Studio sincronice el proyecto con Gradle.

    Ejecutar la Aplicación:

        Conecta un dispositivo Android físico a tu computadora o inicia un emulador Android.

        Haz clic en el botón Run 'app' (el icono de triángulo verde) en la barra de herramientas de Android Studio.

📱 Versión Mínima de Android Requerida

    API Level 24 (Android 7.0 Nougat) o superior.

        Esto asegura compatibilidad con las librerías de Jetpack Compose y Hilt.

🏗️ Arquitectura Implementada: Clean Architecture + MVVM

Este proyecto está diseñado siguiendo los principios de Clean Architecture (Arquitectura Limpia) de Robert C. Martin (Uncle Bob) y el patrón MVVM (Model-View-ViewModel). Esta combinación promueve una clara separación de preocupaciones, lo que resulta en un código más modular, testable y mantenible.

La arquitectura se organiza en las siguientes capas concéntricas:

    Dominio (Domain Layer):

        Core de la aplicación. Contiene las entidades de negocio puras (Weather) y los casos de uso (GetWeatherUseCase) que encapsulan la lógica de negocio específica de la aplicación.

        Define las interfaces de los repositorios (WeatherRepository), estableciendo contratos sobre cómo se interactuará con los datos, sin preocuparse de la implementación.

        Independiente de Android, UI, o fuentes de datos.

    Datos (Data Layer):

        Implementación de los contratos del Dominio. Aquí residen las implementaciones concretas de las interfaces de repositorio (WeatherRepositoryImpl).

        Se encarga de interactuar con las fuentes de datos externas (APIs remotas como OpenWeatherMap) y locales (si las hubiera).

    Presentación (Presentation Layer):

        Prepara los datos para la UI. Contiene los ViewModels (WeatherViewModel) que gestionan el estado de la UI y exponen los datos a los Composables.

        Utiliza StateFlow para emitir el estado de la UI (WeatherUiState) y reaccionar a las interacciones del usuario, delegando la lógica de negocio a los casos de uso.

    Interfaz de Usuario (UI Layer - Jetpack Compose):

        Capa más externa. Compuesta por funciones Composable (WeatherDisplayScreen, WeatherSkeleton) que se encargan de renderizar la interfaz de usuario.

        Observa el estado expuesto por el ViewModel y actualiza la UI en consecuencia.

        No contiene lógica de negocio ni de acceso a datos.

(Puedes insertar aquí un diagrama simple de la Clean Architecture si lo tienes)
⚙️ Decisiones Técnicas Relevantes

    Jetpack Compose: Elegido como el toolkit de UI moderno y declarativo de Android. Permite construir interfaces de usuario de forma más rápida e intuitiva, con un código más conciso y potente.

    Hilt (Inyección de Dependencias): La solución recomendada por Google para la inyección de dependencias en Android. Hilt simplifica enormemente la gestión de dependencias, reduciendo el boilerplate y mejorando la testabilidad al facilitar la provisión de instancias de clases a través de todo el grafo de la aplicación.

    Retrofit: Una librería popular y robusta para realizar llamadas HTTP a APIs REST. Utilizada para interactuar con la API de OpenWeatherMap, facilitando la definición de endpoints y el parseo de JSON.

    Coil: Una librería ligera y rápida para la carga de imágenes en Android, optimizada para Jetpack Compose. Se utiliza para cargar los iconos del tiempo desde URLs.

    Kotlin Coroutines & Flow: Para la gestión de operaciones asíncronas y reactivas. Flow se utiliza para la transmisión de datos desde la capa de datos hasta la UI, y Coroutines para manejar las operaciones de red y los casos de uso en hilos secundarios.

    Patrón Result: Implementado para un manejo explícito y seguro de los resultados de operaciones que pueden fallar (ej., llamadas a la API). Esto evita el uso excesivo de try-catch y hace que el flujo de éxito/error sea más claro en cada capa.

    Estructura de Proyecto Modular: El proyecto está organizado en paquetes que reflejan las capas de la arquitectura (dominio, datos, presentación, ui, di), lo que mejora la organización y escalabilidad del código.

    Esqueleto de Carga (WeatherSkeleton): Implementado para mejorar la experiencia de usuario durante la carga de datos, proporcionando una retroalimentación visual inmediata.
