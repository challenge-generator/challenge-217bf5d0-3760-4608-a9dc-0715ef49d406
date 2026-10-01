# Prompt para Mejorar el Codigo Base

Copia y pega el contenido del bloque de abajo en un asistente de IA (Claude, ChatGPT)
para obtener un ZIP con el proyecto completo y arrancable.

Si preferis trabajar en tu editor con un agente local (Claude Code, Cursor, Copilot), usa `AGENTS.md` en vez de este archivo: dice lo mismo pero para que escriba los archivos en disco.

## Las dos reglas que no se negocian

1. **Completa el boilerplate.** Todo lo que el proyecto necesita para compilar y arrancar: manifiesto de dependencias, punto de entrada, configuracion, capa de interfaz, y las capas del patron arquitectonico declarado. Eso es andamiaje y es tu trabajo.
2. **NO resuelvas el reto.** Los entregables de las fases son el trabajo de la persona. El hueco pedagogico se deja como esta: el proyecto arranca, pero lo que el reto pide implementar NO esta implementado.

Dicho de otra forma: si algo impide compilar, arreglalo. Si algo es logica de negocio incompleta, validaciones ausentes, un secreto hardcodeado o un patron mejorable, dejalo exactamente como esta — es lo que la persona tiene que encontrar.

## Superficie de practica — NO resuelvas

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs.

- `src/main/java/com/pragma/payments/infrastructure/config/ResilienceConfig.java` — El topic pide resiliencia: este archivo es el ejercicio.

## Lo que le falta a este proyecto

Esto NO lo tenes que adivinar: salio de comparar el proyecto contra la arquitectura declarada del reto y de un analisis estatico del codigo. Completalo TODO.

### Referencias colgando en el codigo que si esta

Cada una rompe la compilacion:

- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `com.pragma.payments.domain.model.TransactionRequest`: El import com.pragma.payments.domain.model.TransactionRequest usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `com.pragma.payments.domain.model.TransactionResponse`: El import com.pragma.payments.domain.model.TransactionResponse usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `src/main/java/com/pragma/payments/infrastructure/adapter/PaymentGatewayAdapter.java` — `com.pragma.payments.domain.port.PaymentGatewayService`: El import com.pragma.payments.domain.port.PaymentGatewayService usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `src/main/java/com/pragma/payments/PaymentsApplication.java` — `reactor.core.publisher`: El import reactor.core.publisher.Hooks pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/domain/port/TransactionRepository.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/domain/port/FraudDetectionService.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/domain/port/RiskAssessmentService.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/domain/port/CoreBankingService.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/application/service/TransactionService.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/application/service/TransactionService.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/application/service/TransactionService.java` — `reactor.util.function`: El import reactor.util.function.Tuple2 pertenece a reactor.util.function, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionRepositoryAdapter.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapter/FraudDetectionAdapter.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapter/FraudDetectionAdapter.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapter/RiskAssessmentAdapter.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapter/RiskAssessmentAdapter.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapter/CoreBankingAdapter.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapter/CoreBankingAdapter.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/config/WebClientConfig.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/config/WebClientConfig.java` — `reactor.core.scheduler`: El import reactor.core.scheduler.Schedulers pertenece a reactor.core.scheduler, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapter/PaymentGatewayAdapter.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapter/PaymentGatewayAdapter.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/pragma/payments/application/service/TransactionServiceTest.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/pragma/payments/infrastructure/adapter/FraudDetectionAdapterTest.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/application/service/TransactionService.java` — `Transaction.id`: Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/application/service/TransactionService.java` — `Transaction.fraudDetectionResult`: Se invoca `fraudDetectionResult` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/application/service/TransactionService.java` — `Transaction.riskAssessmentResult`: Se invoca `riskAssessmentResult` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/application/service/TransactionService.java` — `Transaction.coreBankingResult`: Se invoca `coreBankingResult` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionRepositoryAdapter.java` — `Transaction.id`: Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionRepositoryAdapter.java` — `Transaction.accountOrigin`: Se invoca `accountOrigin` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionRepositoryAdapter.java` — `Transaction.accountDestination`: Se invoca `accountDestination` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionRepositoryAdapter.java` — `Transaction.amount`: Se invoca `amount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionRepositoryAdapter.java` — `Transaction.currency`: Se invoca `currency` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionRepositoryAdapter.java` — `Transaction.status`: Se invoca `status` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionRepositoryAdapter.java` — `Transaction.fraudResult`: Se invoca `fraudResult` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionRepositoryAdapter.java` — `Transaction.riskResult`: Se invoca `riskResult` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionRepositoryAdapter.java` — `Transaction.coreResult`: Se invoca `coreResult` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionRepositoryAdapter.java` — `Transaction.createdAt`: Se invoca `createdAt` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/TransactionRepositoryAdapter.java` — `Transaction.updatedAt`: Se invoca `updatedAt` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/FraudDetectionAdapter.java` — `Transaction.id`: Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/FraudDetectionAdapter.java` — `Transaction.accountOrigin`: Se invoca `accountOrigin` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/FraudDetectionAdapter.java` — `Transaction.accountDestination`: Se invoca `accountDestination` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/FraudDetectionAdapter.java` — `Transaction.amount`: Se invoca `amount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/FraudDetectionAdapter.java` — `Transaction.currency`: Se invoca `currency` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/RiskAssessmentAdapter.java` — `Transaction.id`: Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/RiskAssessmentAdapter.java` — `Transaction.accountOrigin`: Se invoca `accountOrigin` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/RiskAssessmentAdapter.java` — `Transaction.accountDestination`: Se invoca `accountDestination` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/RiskAssessmentAdapter.java` — `Transaction.amount`: Se invoca `amount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/RiskAssessmentAdapter.java` — `Transaction.currency`: Se invoca `currency` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/RiskAssessmentAdapter.java` — `Transaction.fraudResult`: Se invoca `fraudResult` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/CoreBankingAdapter.java` — `Transaction.id`: Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/CoreBankingAdapter.java` — `Transaction.accountOrigin`: Se invoca `accountOrigin` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/CoreBankingAdapter.java` — `Transaction.accountDestination`: Se invoca `accountDestination` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/CoreBankingAdapter.java` — `Transaction.amount`: Se invoca `amount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/CoreBankingAdapter.java` — `Transaction.currency`: Se invoca `currency` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/CoreBankingAdapter.java` — `Transaction.type`: Se invoca `type` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `TransactionService.processTransaction`: Se invoca `processTransaction` sobre `TransactionService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `TransactionService.findByCreatedAtBetween`: Se invoca `findByCreatedAtBetween` sobre `TransactionService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `TransactionService.updateStatus`: Se invoca `updateStatus` sobre `TransactionService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java` — `TransactionService.getServiceStatus`: Se invoca `getServiceStatus` sobre `TransactionService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/PaymentGatewayAdapter.java` — `Transaction.id`: Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/PaymentGatewayAdapter.java` — `Transaction.accountOrigin`: Se invoca `accountOrigin` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/PaymentGatewayAdapter.java` — `Transaction.accountDestination`: Se invoca `accountDestination` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapter/PaymentGatewayAdapter.java` — `Transaction.amount`: Se invoca `amount` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/application/service/TransactionServiceTest.java` — `RiskAssessmentAdapter.evaluateRisk`: Se invoca `evaluateRisk` sobre `RiskAssessmentAdapter`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/application/service/TransactionServiceTest.java` — `PaymentGatewayAdapter.executePayment`: Se invoca `executePayment` sobre `PaymentGatewayAdapter`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/application/service/TransactionServiceTest.java` — `TransactionService.createTransaction`: Se invoca `createTransaction` sobre `TransactionService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/application/service/TransactionServiceTest.java` — `TransactionService.getTransactionById`: Se invoca `getTransactionById` sobre `TransactionService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/application/service/TransactionServiceTest.java` — `TransactionService.getTransactionsByAccountOrigin`: Se invoca `getTransactionsByAccountOrigin` sobre `TransactionService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/application/service/TransactionServiceTest.java` — `TransactionService.updateTransactionStatus`: Se invoca `updateTransactionStatus` sobre `TransactionService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/application/service/TransactionServiceTest.java` — `TransactionService.createTransactionWithFallback`: Se invoca `createTransactionWithFallback` sobre `TransactionService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/application/service/TransactionServiceTest.java` — `Transaction.id`: Se invoca `id` sobre `Transaction`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `pom.xml` — `io.r2dbc:r2dbc-postgresql@1.0.5.RELEASE`: io.r2dbc:r2dbc-postgresql declara la version 1.0.5.RELEASE, pero Maven Central respondio que esa version no existe. Es una version inventada: reemplazala por una version publicada real, o si no se conoce con certeza, usa el mecanismo centralizado del ecosistema (BOM/parent/platform/version catalog) y no declares una version individual.

## Como saber que terminaste

```bash
mvn clean compile
```

Ese comando corriendo sin errores es la definicion de "listo".

---

```
## Briefing del reto (autoridad)
Este bloque manda sobre los archivos adjuntos. El stack y el rol salen de AQUÍ, no de un topic genérico ni de markdown placeholder.

### Perfil
Chapter Backend, Especialidad Desarrollador, Tecnología Java, Senior

### Brecha de conocimiento
Implementa un paradigma de programación distinto al imperativo, como el paradigma reactivo o el paradigma funcional. Domina los cuatro pilares especificados en el manifiesto de sistemas reactivos, favoreciendo mejor rendimiento, una mayor escalabilidad y una mayor resiliencia. Conoce las ventajas, desventajas y operadores básicos en la implementación de este paradigma.

### Misión / candidato
Candidato con experiencia en desarrollo backend con Java.

### Reto
- Tema: Adopción de Paradigmas de Programación No Imperativos: Con Enfoque Reactivo y Funcional
- Seniority: senior-l2
- Tipo: practical
- Título: Implementación de Paradigma Reactivo en Sistema de Pagos
- Tiempo estimado: 4 semanas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Exploración y Modelado del Dominio — objetivo: Entender y modelar el dominio del sistema de pagos con enfoque reactivo. — entregable (NO resolver): Diagrama de relaciones y descripción del flujo de transacciones reactivo.
- Fase 2: Implementación de Operadores Reactivos Básicos — objetivo: Implementar operadores reactivos básicos para manejar el flujo de transacciones. — entregable (NO resolver): Código que implementa operadores reactivos básicos y manejo de errores en el flujo de transacciones.
- Fase 3: Optimización y Escalabilidad del Sistema Reactivo — objetivo: Optimizar y escalar el sistema reactivo para manejar un mayor volumen de transacciones. — entregable (NO resolver): Código optimizado y escalado del sistema reactivo, junto con resultados de evaluación de rendimiento y resiliencia.
- Fase 4: Revisión y Mejora Continua — objetivo: Revisar y mejorar continuamente el sistema reactivo. — entregable (NO resolver): Código revisado y mejorado del sistema reactivo, junto con un informe de mejoras realizadas.

Eres un asistente experto en análisis, corrección y generación de archivos de cualquier tipo:
código fuente, documentación, hojas de cálculo, documentos Word, configuraciones, entre otros.
Voy a enviarte una cadena de texto que contiene uno o más archivos. Cada archivo está delimitado por un marcador con el siguiente formato:
// === ARCHIVO: ruta/del/archivo.extension ===
o también puede aparecer como:
## === ARCHIVO: ruta/del/archivo.extension ===
Lo que sigue al marcador puede ser:

El contenido real del archivo (código, texto, YAML, etc.)
Una descripción en lenguaje natural de lo que debe contener el archivo


TU TAREA
PASO 0 — ¿Esto es un proyecto o una carcasa?
Antes de extraer archivos, leé el Briefing (si está) y diagnosticá el adjunto.

Es CARCASA si ocurre CUALQUIERA de estas:
- No hay manifiesto de dependencias del stack del briefing (manifest.json de VTEX IO / package.json / pom.xml / build.gradle / requirements.txt / go.mod / *.tf / *.csproj, según corresponda)
- Hay un "binario" que en realidad es un comentario ("no puede ser mostrado como texto plano", placeholder .fig/.docx vacío)
- Los markdowns ya completan entregables de fases posteriores ("se implementó fade-in", lista de áreas ya resuelta)

Si es CARCASA:
- MATERIALIZÁ un proyecto que arranca en el stack del briefing (VTEX IO Store Framework, Angular, Terraform, pytest, Nest, etc.). Incluí manifiesto, punto de entrada y capa de interfaz reales.
- NO copies los markdowns de "solución" como si fueran el producto. Son ruido de generación.
- NO resuelvas las fases del briefing (están marcadas PROHIBIDO). Dejá el hueco pedagógico: el flujo existe, las microinteracciones/calidad/infra que el reto pide NO están hechas.
- Después seguí al PASO 5 (ZIP).

Si es un proyecto REAL (manifiesto + código que compila o arranca):
- Seguí PASO 1 en adelante. 🔴 compilación sí. 🟡 pedagógico no.

PASO 1 — Detección y extracción
Identifica todos los archivos presentes en la cadena. Para cada archivo extrae:

Su ruta completa (ej: src/main/java/com/pragma/Service.java)
Su contenido o descripción

PASO 2 — Clasificación por tipo
Clasifica cada archivo en una de estas categorías:
A) Código fuente (Java, Python, TypeScript, JavaScript, Kotlin, etc.)
B) Configuración / documentación (YAML, properties, Markdown, JSON, txt, etc.)
C) Excel (.xlsx, .xls, .csv)
D) Word (.docx, .doc)
E) Otro tipo de archivo binario o especial
PASO 3 — Clasificación de errores en código fuente

Objetivo prioritario: que el proyecto compile. No corrijas flujo de negocio ni lógica funcional.

Antes de modificar cualquier archivo de código fuente, clasifica cada problema encontrado en una de estas dos categorías:
🔴 ERROR DE COMPILACIÓN — corregir siempre
Son errores que impiden que el proyecto arranque, sin valor pedagógico:

Import faltante o incorrecto
Clase, método o variable referenciada que no existe en ningún archivo del proyecto
Error de sintaxis
Anotación con atributos inválidos
Dependencia ausente en pom.xml, package.json, etc.
Archivo referenciado que no existe y debe ser creado con implementación mínima

→ CORREGIR estos errores.
🟡 PROBLEMA FUNCIONAL O DE CALIDAD — preservar siempre
Son problemas que no impiden compilar. Pueden ser intencionales para el aprendizaje:

Clave secreta hardcodeada ("secret", "password123")
API deprecada que funciona pero tiene reemplazo moderno
Lógica de negocio incorrecta o incompleta
Código redundante o de baja legibilidad
Falta de validaciones en flujo de negocio
Patrones de diseño incorrectos pero funcionales
Concurrencia no segura
Configuración funcional pero no óptima

→ PRESERVAR tal cual. No corregir, no mejorar, no comentar.
PASO 4 — Procesamiento según tipo de archivo
Tipo A — Código fuente
Aplica únicamente las correcciones clasificadas como 🔴 ERROR DE COMPILACIÓN.
No alteres ningún elemento clasificado como 🟡 PROBLEMA FUNCIONAL O DE CALIDAD.
Si falta un archivo referenciado, créalo con la implementación mínima necesaria para compilar.
Tipo B — Configuración / documentación
Extrae el contenido tal cual, sin modificaciones salvo errores evidentes de sintaxis
(ej: YAML mal indentado).
Tipo C — Excel (.xlsx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un archivo Excel funcional con:

Fila de encabezados en negrita con color de fondo distintivo
Columnas con ancho ajustado al contenido
Tipos de dato correctos por columna
Validaciones si la descripción lo indica
Hojas nombradas descriptivamente si hay más de una
Filas de ejemplo si no hay datos reales

Tipo D — Word (.docx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un documento Word funcional con:

Estilos de título (Título 1, Título 2) para jerarquía de secciones
Fuente legible (Calibri o equivalente), tamaño 11-12pt para cuerpo
Márgenes estándar
Tabla de contenido si tiene múltiples secciones
Tablas con encabezados en negrita si aplica

Tipo E — Otro
Genera el archivo con el contenido o estructura más apropiada según la descripción.
PASO 5 — Exportación en ZIP
Empaqueta todos los archivos en un único archivo ZIP descargable respetando exactamente
la estructura de rutas indicada por los marcadores.
El ZIP debe incluir:

Archivos de código con únicamente los errores de compilación corregidos
Archivos de configuración y documentación sin cambios
Archivos nuevos creados para resolver dependencias de compilación faltantes
Archivos Excel y Word generados desde descripción

IMPORTANTE: El ZIP debe estar listo para descargar al finalizar. No preguntes si el usuario
quiere generarlo. Simplemente genera el archivo y proporciona el enlace de descarga; No debes desplegar en el chat el resumen de lo que arreglaste al Zip, solo entregalo.

REGLAS IMPORTANTES

No omitas ningún archivo aunque no tenga errores ni modificaciones
Respeta los nombres y rutas exactas indicadas por los marcadores
Si un archivo no tiene marcador claro, infiere el nombre desde su contenido
Si la cadena contiene solo documentación, placeholders o binarios fake, NO la reproduzcas:
aplicá PASO 0 (materializar el proyecto del briefing). Reproducir la carcasa es un fallo.
No agregues texto después del enlace de descarga del ZIP
No preguntes si el usuario quiere el ZIP: simplemente generalo siempre
Si detectas que falta un archivo de configuración necesario para compilar
(pom.xml, package.json, requirements.txt, build.gradle, etc.), créalo e inclúyelo
inferiendo su contenido desde los imports y frameworks detectados en el código
Nunca corrijas problemas 🟡 aunque parezcan obvios o fáciles de mejorar.
El participante que recibirá este proyecto los debe encontrar y resolver él mismo.


INPUT
Aquí está la cadena con los archivos:

// === ARCHIVO: pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.6</version>
        <relativePath/>
    </parent>

    <groupId>com.pragma</groupId>
    <artifactId>payments</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>payments</name>
    <description>Sistema de pagos con paradigma reactivo</description>

    <properties>
        <java.version>21</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    </properties>

    <dependencies>
        <!-- Spring WebFlux -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>

        <!-- R2DBC para persistencia reactiva -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-r2dbc</artifactId>
        </dependency>
        <dependency>
            <groupId>io.r2dbc</groupId>
            <artifactId>r2dbc-postgresql</artifactId>
            <version>1.0.5.RELEASE</version>
        </dependency>

        <!-- Reactor Core -->
        <dependency>
            <groupId>io.projectreactor</groupId>
            <artifactId>reactor-core</artifactId>
        </dependency>
        <dependency>
            <groupId>io.projectreactor.netty</groupId>
            <artifactId>reactor-netty</artifactId>
        </dependency>

        <!-- Resilience4j para patrones de resiliencia -->
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-spring-boot3</artifactId>
            <version>2.2.0</version>
        </dependency>
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-reactor</artifactId>
            <version>2.2.0</version>
        </dependency>

        <!-- Actuator para monitoreo -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- Testing -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.projectreactor</groupId>
            <artifactId>reactor-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>postgresql</artifactId>
            <version>1.20.1</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>21</source>
                    <target>21</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>

// === ARCHIVO: src/main/java/com/pragma/payments/PaymentsApplication.java ===
package com.pragma.payments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Hooks;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import java.time.Duration;

@SpringBootApplication
public class PaymentsApplication {

    public static void main(String[] args) {
        Hooks.onOperatorDebug();
        SpringApplication.run(PaymentsApplication.class, args);
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

    @Bean
    public CircuitBreakerConfig customCircuitBreakerConfig() {
        return CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .permittedNumberOfCallsInHalfOpenState(3)
                .slidingWindowSize(5)
                .recordExceptions(
                    org.springframework.web.reactive.function.client.WebClientResponseException.class,
                    java.util.concurrent.TimeoutException.class,
                    io.netty.handler.timeout.TimeoutException.class
                )
                .build();
    }

    @Bean
    public CircuitBreaker circuitBreaker(CircuitBreakerConfig customCircuitBreakerConfig) {
        return CircuitBreaker.of("paymentCircuitBreaker", customCircuitBreakerConfig);
    }

    @Bean
    public CircuitBreakerOperator<Object> circuitBreakerOperator(CircuitBreaker circuitBreaker) {
        return CircuitBreakerOperator.of(circuitBreaker);
    }
}

// === ARCHIVO: src/main/resources/application.yml ===
server:
  port: 8080
  netty:
    connection-timeout: 2s
    idle-timeout: 15s

spring:
  r2dbc:
    url: r2dbc:postgresql://localhost:5432/payments_db
    username: payments_user
    password: payments_password
    pool:
      enabled: true
      initial-size: 5
      max-size: 20
      max-idle-time: 30m
      max-create-connection-time: 2s

  webflux:
    base-path: /api/payments

resilience4j:
  circuitbreaker:
    instances:
      paymentCircuitBreaker:
        registerHealthIndicator: true
        slidingWindowSize: 5
        minimumNumberOfCalls: 5
        permittedNumberOfCallsInHalfOpenState: 3
        automaticTransitionFromOpenToHalfOpenEnabled: true
        waitDurationInOpenState: 1s
        failureRateThreshold: 50
        eventConsumerBufferSize: 10
        recordExceptions:
          - org.springframework.web.reactive.function.client.WebClientResponseException
          - java.util.concurrent.TimeoutException
          - io.netty.handler.timeout.TimeoutException
  retry:
    instances:
      paymentRetry:
        maxAttempts: 3
        waitDuration: 500ms
        enableExponentialBackoff: true
        exponentialBackoffMultiplier: 2
        retryExceptions:
          - org.springframework.web.reactive.function.client.WebClientResponseException
          - java.util.concurrent.TimeoutException
  bulkhead:
    instances:
      paymentBulkhead:
        maxConcurrentCalls: 20
        maxWaitDuration: 10ms

management:
  endpoints:
    web:
      exposure:
        include: health, metrics, prometheus, circuitbreakers
  endpoint:
    health:
      show-details: always
  health:
    circuitbreakers:
      enabled: true
    ratelimiters:
      enabled: true

// === ARCHIVO: src/main/java/com/pragma/payments/domain/model/Transaction.java ===
package com.pragma.payments.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidad que representa una transacción financiera en el sistema.
 * Contiene todos los atributos necesarios para procesar y validar una transacción,
 * incluyendo información del originador, destinatario y montos involucrados.
 */
public record Transaction(
    UUID id,
    String accountOrigin,
    String accountDestination,
    BigDecimal amount,
    TransactionStatus status,
    String reference,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    String currency,
    String description,
    String paymentMethod,
    String fraudDetectionResult,
    String riskAssessmentResult,
    String coreBankingResult
) {
    /**
     * Constructor que inicializa una transacción con los campos básicos requeridos.
     * Los campos de estado y timestamps se inicializan con valores por defecto.
     */
    public Transaction {
        if (id == null) {
            id = UUID.randomUUID();
        }
        if (accountOrigin == null || accountOrigin.isBlank()) {
            throw new IllegalArgumentException("La cuenta de origen no puede ser nula o vacía");
        }
        if (accountDestination == null || accountDestination.isBlank()) {
            throw new IllegalArgumentException("La cuenta de destino no puede ser nula o vacía");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("La moneda no puede ser nula o vacía");
        }
        if (status == null) {
            status = TransactionStatus.PENDING;
        }
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (updatedAt == null) {
            updatedAt = createdAt;
        }
    }

    /**
     * Crea una nueva transacción con estado PENDING y timestamps actualizados.
     */
    public static Transaction create(
            String accountOrigin,
            String accountDestination,
            BigDecimal amount,
            String reference,
            String currency,
            String description,
            String paymentMethod
    ) {
        return new Transaction(
                UUID.randomUUID(),
                accountOrigin,
                accountDestination,
                amount,
                TransactionStatus.PENDING,
                reference,
                LocalDateTime.now(),
                LocalDateTime.now(),
                currency,
                description,
                paymentMethod,
                null,
                null,
                null
        );
    }

    /**
     * Actualiza el estado de la transacción y registra el resultado de la detección de fraude.
     */
    public Transaction withFraudDetectionResult(String fraudResult) {
        return new Transaction(
                this.id,
                this.accountOrigin,
                this.accountDestination,
                this.amount,
                this.status,
                this.reference,
                this.createdAt,
                LocalDateTime.now(),
                this.currency,
                this.description,
                this.paymentMethod,
                fraudResult,
                this.riskAssessmentResult,
                this.coreBankingResult
        );
    }

    /**
     * Actualiza el estado de la transacción y registra el resultado de la evaluación de riesgo.
     */
    public Transaction withRiskAssessmentResult(String riskResult) {
        return new Transaction(
                this.id,
                this.accountOrigin,
                this.accountDestination,
                this.amount,
                this.status,
                this.reference,
                this.createdAt,
                LocalDateTime.now(),
                this.currency,
                this.description,
                this.paymentMethod,
                this.fraudDetectionResult,
                riskResult,
                this.coreBankingResult
        );
    }

    /**
     * Actualiza el estado de la transacción y registra el resultado del core bancario.
     */
    public Transaction withCoreBankingResult(String coreResult) {
        return new Transaction(
                this.id,
                this.accountOrigin,
                this.accountDestination,
                this.amount,
                this.status,
                this.reference,
                this.createdAt,
                LocalDateTime.now(),
                this.currency,
                this.description,
                this.paymentMethod,
                this.fraudDetectionResult,
                this.riskAssessmentResult,
                coreResult
        );
    }

    /**
     * Actualiza el estado de la transacción a COMPLETED.
     */
    public Transaction markAsCompleted() {
        return new Transaction(
                this.id,
                this.accountOrigin,
                this.accountDestination,
                this.amount,
                TransactionStatus.COMPLETED,
                this.reference,
                this.createdAt,
                LocalDateTime.now(),
                this.currency,
                this.description,
                this.paymentMethod,
                this.fraudDetectionResult,
                this.riskAssessmentResult,
                this.coreBankingResult
        );
    }

    /**
     * Actualiza el estado de la transacción a FAILED.
     */
    public Transaction markAsFailed() {
        return new Transaction(
                this.id,
                this.accountOrigin,
                this.accountDestination,
                this.amount,
                TransactionStatus.FAILED,
                this.reference,
                this.createdAt,
                LocalDateTime.now(),
                this.currency,
                this.description,
                this.paymentMethod,
                this.fraudDetectionResult,
                this.riskAssessmentResult,
                this.coreBankingResult
        );
    }
}

/**
 * Enum que representa los posibles estados de una transacción.
 */
enum TransactionStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED,
    FAILED,
    REVERSED
}

// === ARCHIVO: src/main/java/com/pragma/payments/domain/port/TransactionRepository.java ===
package com.pragma.payments.domain.port;

import com.pragma.payments.domain.model.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Puerto de persistencia para operaciones sobre transacciones.
 * Define las operaciones básicas de creación, lectura y actualización
 * que deben ser implementadas por los adaptadores de infraestructura.
 */
public interface TransactionRepository {
    /**
     * Guarda una transacción en el repositorio.
     * @param transaction La transacción a guardar
     * @return Mono con la transacción guardada
     */
    Mono<Transaction> save(Transaction transaction);

    /**
     * Busca una transacción por su ID.
     * @param id El ID de la transacción
     * @return Mono con la transacción encontrada, o vacío si no existe
     */
    Mono<Transaction> findById(UUID id);

    /**
     * Busca todas las transacciones asociadas a una cuenta de origen.
     * @param accountOrigin La cuenta de origen
     * @return Flux con las transacciones encontradas
     */
    Flux<Transaction> findByAccountOrigin(String accountOrigin);

    /**
     * Busca todas las transacciones asociadas a una cuenta de destino.
     * @param accountDestination La cuenta de destino
     * @return Flux con las transacciones encontradas
     */
    Flux<Transaction> findByAccountDestination(String accountDestination);

    /**
     * Actualiza el estado de una transacción.
     * @param id El ID de la transacción
     * @param status El nuevo estado
     * @return Mono con la transacción actualizada
     */
    Mono<Transaction> updateStatus(UUID id, String status);

    /**
     * Busca transacciones por rango de fechas de creación.
     * @param startDate Fecha de inicio
     * @param endDate Fecha de fin
     * @return Flux con las transacciones encontradas
     */
    Flux<Transaction> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}

// === ARCHIVO: src/main/java/com/pragma/payments/domain/port/FraudDetectionService.java ===
package com.pragma.payments.domain.port;

import com.pragma.payments.domain.model.Transaction;
import reactor.core.publisher.Mono;

/**
 * Puerto para el servicio de detección de fraude.
 * Define las operaciones que el dominio necesita para interactuar
 * con servicios externos de detección de fraude.
 */
public interface FraudDetectionService {
    /**
     * Evalúa una transacción para detectar posibles fraudes.
     * @param transaction La transacción a evaluar
     * @return Mono con el resultado de la evaluación de fraude
     */
    Mono<String> evaluateForFraud(Transaction transaction);

    /**
     * Obtiene el estado actual de un servicio de detección de fraude.
     * @return Mono con el estado del servicio (ej: "UP", "DOWN", "DEGRADED")
     */
    Mono<String> getServiceStatus();
}

// === ARCHIVO: src/main/java/com/pragma/payments/domain/port/RiskAssessmentService.java ===
package com.pragma.payments.domain.port;

import com.pragma.payments.domain.model.Transaction;
import reactor.core.publisher.Mono;

/**
 * Puerto para el servicio de evaluación de riesgos del buró de riesgos.
 * Define la interfaz que el dominio necesita para evaluar el riesgo de una transacción.
 */
public interface RiskAssessmentService {

    /**
     * Evalúa el riesgo de una transacción específica.
     * @param transaction La transacción a evaluar
     * @return Mono que emite el resultado de la evaluación de riesgo
     */
    Mono<String> evaluateRisk(Transaction transaction);

    /**
     * Obtiene el estado de disponibilidad del servicio de riesgo.
     * @return Mono que emite el estado del servicio
     */
    Mono<String> getServiceStatus();

    /**
     * Evalúa el límite de crédito disponible para una cuenta.
     * @param accountNumber Número de cuenta a verificar
     * @param amount Monto a verificar
     * @return Mono que emite true si el monto está dentro del límite
     */
    Mono<Boolean> checkCreditLimit(String accountNumber, java.math.BigDecimal amount);
}

// === ARCHIVO: src/main/java/com/pragma/payments/domain/port/CoreBankingService.java ===
package com.pragma.payments.domain.port;

import com.pragma.payments.domain.model.Transaction;
import reactor.core.publisher.Mono;

/**
 * Puerto para el servicio del core bancario.
 * Define la interfaz que el dominio necesita para interactuar con el core bancario.
 */
public interface CoreBankingService {

    /**
     * Procesa una transacción en el core bancario.
     * @param transaction La transacción a procesar
     * @return Mono que emite el resultado del procesamiento
     */
    Mono<String> processTransaction(Transaction transaction);

    /**
     * Consulta el saldo de una cuenta.
     * @param accountNumber Número de cuenta a consultar
     * @return Mono que emite el saldo actual
     */
    Mono<java.math.BigDecimal> getAccountBalance(String accountNumber);

    /**
     * Reserva fondos para una transacción.
     * @param accountNumber Cuenta origen
     * @param amount Monto a reservar
     * @return Mono que emite true si la reserva fue exitosa
     */
    Mono<Boolean> reserveFunds(String accountNumber, java.math.BigDecimal amount);

    /**
     * Confirma la transferencia de fondos.
     * @param transactionId ID de la transacción
     * @return Mono que emite true si la confirmación fue exitosa
     */
    Mono<Boolean> confirmTransfer(java.util.UUID transactionId);

    /**
     * Revierte una transacción previamente confirmada.
     * @param transactionId ID de la transacción a revertir
     * @return Mono que emite true si la reversa fue exitosa
     */
    Mono<Boolean> reverseTransaction(java.util.UUID transactionId);

    /**
     * Obtiene el estado del servicio del core bancario.
     * @return Mono que emite el estado del servicio
     */
    Mono<String> getServiceStatus();
}

// === ARCHIVO: src/main/java/com/pragma/payments/application/service/TransactionService.java ===
package com.pragma.payments.application.service;

import com.pragma.payments.domain.model.Transaction;
import com.pragma.payments.domain.port.CoreBankingService;
import com.pragma.payments.domain.port.FraudDetectionService;
import com.pragma.payments.domain.port.RiskAssessmentService;
import com.pragma.payments.domain.port.TransactionRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Servicio de aplicación que orquesta el flujo de transacciones reactivas.
 * Coordina la interacción entre el detector de fraude, el buró de riesgos,
 * el core bancario y el repositorio de transacciones.
 */
@Service
public class TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);
    private static final String APPROVED = "APPROVED";
    private static final String REJECTED = "REJECTED";
    private static final String PROCESSED = "PROCESSED";
    private static final String FAILED = "FAILED";

    private final TransactionRepository transactionRepository;
    private final FraudDetectionService fraudDetectionService;
    private final RiskAssessmentService riskAssessmentService;
    private final CoreBankingService coreBankingService;

    public TransactionService(
            TransactionRepository transactionRepository,
            FraudDetectionService fraudDetectionService,
            RiskAssessmentService riskAssessmentService,
            CoreBankingService coreBankingService) {
        this.transactionRepository = transactionRepository;
        this.fraudDetectionService = fraudDetectionService;
        this.riskAssessmentService = riskAssessmentService;
        this.coreBankingService = coreBankingService;
    }

    /**
     * Crea y procesa una nueva transacción aplicando el flujo reactivo completo.
     * @param accountOrigin Cuenta origen
     * @param accountDestination Cuenta destino
     * @param amount Monto de la transacción
     * @return Mono que emite la transacción procesada
     */
    public Mono<Transaction> createAndProcessTransaction(
            String accountOrigin,
            String accountDestination,
            BigDecimal amount) {

        log.info("Iniciando procesamiento de transacción: origen={}, destino={}, monto={}",
                accountOrigin, accountDestination, amount);

        Transaction transaction = Transaction.create(
                accountOrigin,
                accountDestination,
                amount
        );

        return transactionRepository.save(transaction)
                .flatMap(this::executeFraudDetection)
                .flatMap(this::executeRiskAssessment)
                .flatMap(this::executeCoreBankingProcessing)
                .flatMap(this::finalizeTransaction)
                .doOnSuccess(t -> log.info("Transacción procesada exitosamente: id={}, estado={}",
                        t.id(), t.status()))
                .doOnError(e -> log.error("Error en procesamiento de transacción: {}", e.getMessage()));
    }

    /**
     * Ejecuta la detección de fraude sobre la transacción.
     * @param transaction Transacción a evaluar
     * @return Mono con la transacción actualizada
     */
    @CircuitBreaker(name = "fraudDetection", fallbackMethod = "fraudDetectionFallback")
    @Retry(name = "fraudDetectionRetry")
    private Mono<Transaction> executeFraudDetection(Transaction transaction) {
        log.debug("Ejecutando detección de fraude para transacción: {}", transaction.id());

        return fraudDetectionService.evaluateForFraud(transaction)
                .map(result -> {
                    Transaction updated = transaction.withFraudDetectionResult(result);
                    log.info("Resultado detección de fraude para {}: {}", transaction.id(), result);
                    return updated;
                });
    }

    /**
     * Ejecuta la evaluación de riesgos sobre la transacción.
     * @param transaction Transacción a evaluar
     * @return Mono con la transacción actualizada
     */
    @CircuitBreaker(name = "riskAssessment", fallbackMethod = "riskAssessmentFallback")
    @Retry(name = "riskAssessmentRetry")
    private Mono<Transaction> executeRiskAssessment(Transaction transaction) {
        log.debug("Ejecutando evaluación de riesgo para transacción: {}", transaction.id());

        if (REJECTED.equals(transaction.fraudDetectionResult())) {
            log.info("Transacción rechazada por fraude, saltando evaluación de riesgo: {}", transaction.id());
            return Mono.just(transaction);
        }

        return riskAssessmentService.evaluateRisk(transaction)
                .map(result -> {
                    Transaction updated = transaction.withRiskAssessmentResult(result);
                    log.info("Resultado evaluación de riesgo para {}: {}", transaction.id(), result);
                    return updated;
                });
    }

    /**
     * Procesa la transacción en el core bancario.
     * @param transaction Transacción a procesar
     * @return Mono con la transacción actualizada
     */
    @CircuitBreaker(name = "coreBanking", fallbackMethod = "coreBankingFallback")
    @Retry(name = "coreBankingRetry")
    private Mono<Transaction> executeCoreBankingProcessing(Transaction transaction) {
        log.debug("Ejecutando procesamiento en core bancario para transacción: {}", transaction.id());

        if (REJECTED.equals(transaction.fraudDetectionResult()) ||
            REJECTED.equals(transaction.riskAssessmentResult())) {
            log.info("Transacción rechazada en validación previa, no se procesa en core: {}", transaction.id());
            return Mono.just(transaction.markAsFailed());
        }

        return coreBankingService.processTransaction(transaction)
                .map(result -> {
                    Transaction updated = transaction.withCoreBankingResult(result);
                    log.info("Resultado procesamiento core bancario para {}: {}", transaction.id(), result);
                    return updated;
                });
    }

    /**
     * Finaliza la transacción guardando el resultado en el repositorio.
     * @param transaction Transacción a finalizar
     * @return Mono con la transacción finalizada
     */
    private Mono<Transaction> finalizeTransaction(Transaction transaction) {
        log.debug("Finalizando transacción: {}", transaction.id());

        boolean isSuccess = APPROVED.equals(transaction.coreBankingResult());
        Transaction finalTransaction = isSuccess ?
                transaction.markAsCompleted() :
                transaction.markAsFailed();

        return transactionRepository.save(finalTransaction)
                .doOnSuccess(t -> log.info("Transacción finalizada y guardada: id={}, estado={}",
                        t.id(), t.status()));
    }

    /**
     * Consulta transacciones por cuenta origen.
     * @param accountOrigin Cuenta origen
     * @return Flux de transacciones
     */
    public Flux<Transaction> findByAccountOrigin(String accountOrigin) {
        log.debug("Consultando transacciones por cuenta origen: {}", accountOrigin);
        return transactionRepository.findByAccountOrigin(accountOrigin);
    }

    /**
     * Consulta transacciones por cuenta destino.
     * @param accountDestination Cuenta destino
     * @return Flux de transacciones
     */
    public Flux<Transaction> findByAccountDestination(String accountDestination) {
        log.debug("Consultando transacciones por cuenta destino: {}", accountDestination);
        return transactionRepository.findByAccountDestination(accountDestination);
    }

    /**
     * Consulta transacciones por rango de fechas.
     * @param startDate Fecha inicial
     * @param endDate Fecha final
     * @return Flux de transacciones
     */
    public Flux<Transaction> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        log.debug("Consultando transacciones entre {} y {}", startDate, endDate);
        return transactionRepository.findByCreatedAtBetween(startDate, endDate);
    }

    /**
     * Consulta una transacción por ID.
     * @param id ID de la transacción
     * @return Mono con la transacción
     */
    public Mono<Transaction> findById(UUID id) {
        log.debug("Consultando transacción por ID: {}", id);
        return transactionRepository.findById(id);
    }

    /**
     * Procesa múltiples transacciones en paralelo usando merge.
     * @param transactions Flux de transacciones a procesar
     * @return Flux de transacciones procesadas
     */
    public Flux<Transaction> processMultipleTransactions(Flux<Transaction> transactions) {
        log.info("Procesando múltiples transacciones en paralelo");

        return transactions
                .flatMap(this::executeFraudDetection)
                .flatMap(this::executeRiskAssessment)
                .flatMap(this::executeCoreBankingProcessing)
                .flatMap(this::finalizeTransaction);
    }

    /**
     * Procesa transacciones con verificación de crédito previa.
     * @param accountOrigin Cuenta origen
     * @param accountDestination Cuenta destino
     * @param amount Monto
     * @return Mono de transacción procesada
     */
    public Mono<Transaction> processTransactionWithCreditCheck(
            String accountOrigin,
            String accountDestination,
            BigDecimal amount) {

        return riskAssessmentService.checkCreditLimit(accountOrigin, amount)
                .filter(hasLimit -> hasLimit)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Límite de crédito insuficiente")))
                .flatMap(hasLimit -> createAndProcessTransaction(accountOrigin, accountDestination, amount));
    }

    /**
     * Ejecuta múltiples validaciones en paralelo y combina los resultados.
     * @param transaction Transacción a validar
     * @return Tuple2 con resultados de fraude y riesgo
     */
    private Mono<Tuple2<String, String>> executeParallelValidations(Transaction transaction) {
        log.debug("Ejecutando validaciones en paralelo para: {}", transaction.id());

        Mono<String> fraudCheck = fraudDetectionService.evaluateForFraud(transaction)
                .doOnNext(result -> log.info("Fraude evaluado: {}", result));

        Mono<String> riskCheck = riskAssessmentService.evaluateRisk(transaction)
                .doOnNext(result -> log.info("Riesgo evaluado: {}", result));

        return Mono.zip(fraudCheck, riskCheck);
    }

    /**
     * Fallback para detección de fraude cuando el circuito está abierto.
     */
    private Mono<Transaction> fraudDetectionFallback(Transaction transaction, Throwable ex) {
        log.warn("Fallback de detección de fraude activado para {}: {}", transaction.id(), ex.getMessage());
        return Mono.just(transaction.withFraudDetectionResult(APPROVED));
    }

    /**
     * Fallback para evaluación de riesgos cuando el circuito está abierto.
     */
    private Mono<Transaction> riskAssessmentFallback(Transaction transaction, Throwable ex) {
        log.warn("Fallback de evaluación de riesgos activado para {}: {}", transaction.id(), ex.getMessage());
        return Mono.just(transaction.withRiskAssessmentResult(APPROVED));
    }

    /**
     * Fallback para procesamiento del core bancario cuando el circuito está abierto.
     */
    private Mono<Transaction> coreBankingFallback(Transaction transaction, Throwable ex) {
        log.warn("Fallback de core bancario activado para {}: {}", transaction.id(), ex.getMessage());
        return Mono.just(transaction.withCoreBankingResult(REJECTED));
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/adapter/TransactionPersistenceException.java ===
package com.pragma.payments.infrastructure.adapter;


import com.pragma.payments.domain.model.Transaction;
public class TransactionPersistenceException extends RuntimeException {
    private final String operation;
    private final String transactionId;
    private final String originalMessage;

    public TransactionPersistenceException(String message) {
        super(message);
        this.operation = "UNKNOWN";
        this.transactionId = null;
        this.originalMessage = message;
    }

    public TransactionPersistenceException(String message, Throwable cause) {
        super(message, cause);
        this.operation = "UNKNOWN";
        this.transactionId = null;
        this.originalMessage = message;
    }

    public TransactionPersistenceException(String operation, String transactionId, String message, Throwable cause) {
        super(buildMessage(operation, transactionId, message), cause);
        this.operation = operation;
        this.transactionId = transactionId;
        this.originalMessage = message;
    }

    private static String buildMessage(String operation, String transactionId, String message) {
        StringBuilder sb = new StringBuilder("Transaction persistence error");
        sb.append(" [operation=").append(operation).append("]");
        if (transactionId != null) {
            sb.append(" [transactionId=").append(transactionId).append("]");
        }
        sb.append(": ").append(message);
        return sb.toString();
    }

    public String getOperation() {
        return operation;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getOriginalMessage() {
        return originalMessage;
    }

    public boolean isRetryable() {
        Throwable cause = getCause();
        if (cause instanceof org.springframework.dao.DataAccessException) {
            String className = cause.getClass().getSimpleName();
            return "DeadlockLoserDataAccessException".equals(className) ||
                   "CannotAcquireLockException".equals(className) ||
                   "TransientDataAccessException".equals(className);
        }
        return false;
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/adapter/TransactionNotFoundException.java ===
package com.pragma.payments.infrastructure.adapter;


import com.pragma.payments.domain.model.Transaction;
public class TransactionNotFoundException extends RuntimeException {
    private final String transactionId;
    private final String accountOrigin;
    private final String accountDestination;
    private final java.time.LocalDateTime searchTimestamp;

    public TransactionNotFoundException(String transactionId) {
        super("Transaction not found with id: " + transactionId);
        this.transactionId = transactionId;
        this.accountOrigin = null;
        this.accountDestination = null;
        this.searchTimestamp = java.time.LocalDateTime.now();
    }

    public TransactionNotFoundException(String transactionId, Throwable cause) {
        super("Transaction not found with id: " + transactionId, cause);
        this.transactionId = transactionId;
        this.accountOrigin = null;
        this.accountDestination = null;
        this.searchTimestamp = java.time.LocalDateTime.now();
    }

    public TransactionNotFoundException forAccountOrigin(String accountOrigin) {
        this.accountOrigin = accountOrigin;
        return this;
    }

    public TransactionNotFoundException forAccountDestination(String accountDestination) {
        this.accountDestination = accountDestination;
        return this;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getAccountOrigin() {
        return accountOrigin;
    }

    public String getAccountDestination() {
        return accountDestination;
    }

    public java.time.LocalDateTime getSearchTimestamp() {
        return searchTimestamp;
    }

    public String getDetailedMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("Transaction not found");
        if (transactionId != null) {
            sb.append(" [id=").append(transactionId).append("]");
        }
        if (accountOrigin != null) {
            sb.append(" [origin=").append(accountOrigin).append("]");
        }
        if (accountDestination != null) {
            sb.append(" [destination=").append(accountDestination).append("]");
        }
        return sb.toString();
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/adapter/TransactionRepositoryAdapter.java ===
package com.pragma.payments.infrastructure.adapter;


import com.pragma.payments.domain.model.TransactionStatus;
import com.pragma.payments.domain.model.Transaction;
import com.pragma.payments.domain.port.TransactionRepository;
import io.r2dbc.postgresql.codec.Json;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public class TransactionRepositoryAdapter implements TransactionRepository {

    private final DatabaseClient databaseClient;

    public TransactionRepositoryAdapter(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @Override
    public Mono<Transaction> save(Transaction transaction) {
        String sql = """
            INSERT INTO transactions 
                (id, account_origin, account_destination, amount, currency, status, 
                 fraud_result, risk_result, core_result, created_at, updated_at)
            VALUES 
                (:id, :accountOrigin, :accountDestination, :amount, :currency, :status,
                 :fraudResult, :riskResult, :coreResult, :createdAt, :updatedAt)
            """;

        return databaseClient.sql(sql)
            .bind("id", transaction.id().toString())
            .bind("accountOrigin", transaction.accountOrigin())
            .bind("accountDestination", transaction.accountDestination())
            .bind("amount", transaction.amount().doubleValue())
            .bind("currency", transaction.currency())
            .bind("status", transaction.status().name())
            .bind("fraudResult", transaction.fraudResult() != null ? transaction.fraudResult() : Json.of("null"))
            .bind("riskResult", transaction.riskResult() != null ? transaction.riskResult() : Json.of("null"))
            .bind("coreResult", transaction.coreResult() != null ? transaction.coreResult() : Json.of("null"))
            .bind("createdAt", transaction.createdAt())
            .bind("updatedAt", transaction.updatedAt())
            .fetch()
            .rowsUpdated()
            .flatMap(rows -> {
                if (rows > 0) {
                    return Mono.just(transaction);
                }
                return Mono.error(new TransactionPersistenceException("SAVE", 
                    transaction.id().toString(), "Failed to save transaction, no rows affected"));
            })
            .onErrorResume(e -> Mono.error(new TransactionPersistenceException("SAVE", 
                transaction.id().toString(), "Database error during save", e)));
    }

    @Override
    public Mono<Transaction> findById(UUID id) {
        String sql = "SELECT * FROM transactions WHERE id = :id";
        
        return databaseClient.sql(sql)
            .bind("id", id.toString())
            .map((row, metadata) -> mapRowToTransaction(row))
            .first()
            .switchIfEmpty(Mono.error(new TransactionNotFoundException(id.toString())))
            .onErrorResume(TransactionNotFoundException.class, Mono::error)
            .onErrorResume(e -> Mono.error(new TransactionPersistenceException("FIND_BY_ID", 
                id.toString(), "Database error during findById", e)));
    }

    @Override
    public Flux<Transaction> findByAccountOrigin(String accountOrigin) {
        String sql = "SELECT * FROM transactions WHERE account_origin = :accountOrigin ORDER BY created_at DESC";
        
        return databaseClient.sql(sql)
            .bind("accountOrigin", accountOrigin)
            .map((row, metadata) -> mapRowToTransaction(row))
            .all()
            .onErrorResume(e -> Flux.error(new TransactionPersistenceException("FIND_BY_ORIGIN", 
                null, "Database error during findByAccountOrigin", e)));
    }

    @Override
    public Flux<Transaction> findByAccountDestination(String accountDestination) {
        String sql = "SELECT * FROM transactions WHERE account_destination = :accountDestination ORDER BY created_at DESC";
        
        return databaseClient.sql(sql)
            .bind("accountDestination", accountDestination)
            .map((row, metadata) -> mapRowToTransaction(row))
            .all()
            .onErrorResume(e -> Flux.error(new TransactionPersistenceException("FIND_BY_DESTINATION", 
                null, "Database error during findByAccountDestination", e)));
    }

    @Override
    public Mono<Transaction> updateStatus(UUID id, String status) {
        String sql = "UPDATE transactions SET status = :status, updated_at = :updatedAt WHERE id = :id";
        
        return databaseClient.sql(sql)
            .bind("id", id.toString())
            .bind("status", status)
            .bind("updatedAt", LocalDateTime.now())
            .fetch()
            .rowsUpdated()
            .flatMap(rows -> {
                if (rows > 0) {
                    return findById(id);
                }
                return Mono.error(new TransactionNotFoundException(id.toString()));
            })
            .onErrorResume(TransactionNotFoundException.class, Mono::error)
            .onErrorResume(e -> Mono.error(new TransactionPersistenceException("UPDATE_STATUS", 
                id.toString(), "Database error during updateStatus", e)));
    }

    @Override
    public Flux<Transaction> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        String sql = "SELECT * FROM transactions WHERE created_at BETWEEN :startDate AND :endDate ORDER BY created_at DESC";
        
        return databaseClient.sql(sql)
            .bind("startDate", startDate)
            .bind("endDate", endDate)
            .map((row, metadata) -> mapRowToTransaction(row))
            .all()
            .onErrorResume(e -> Flux.error(new TransactionPersistenceException("FIND_BY_DATE_RANGE", 
                null, "Database error during findByCreatedAtBetween", e)));
    }

    private Transaction mapRowToTransaction(org.springframework.r2dbc.core.Row row) {
        UUID id = UUID.fromString(row.get("id", String.class));
        String accountOrigin = row.get("account_origin", String.class);
        String accountDestination = row.get("account_destination", String.class);
        java.math.BigDecimal amount = row.get("amount", java.math.BigDecimal.class);
        String currency = row.get("currency", String.class);
        String statusStr = row.get("status", String.class);
        TransactionStatus status = TransactionStatus.valueOf(statusStr);
        String fraudResult = extractJsonValue(row.get("fraud_result", Json.class));
        String riskResult = extractJsonValue(row.get("risk_result", Json.class));
        String coreResult = extractJsonValue(row.get("core_result", Json.class));
        LocalDateTime createdAt = row.get("created_at", LocalDateTime.class);
        LocalDateTime updatedAt = row.get("updated_at", LocalDateTime.class);

        return new Transaction(id, accountOrigin, accountDestination, amount, currency, 
            status, fraudResult, riskResult, coreResult, createdAt, updatedAt);
    }

    private String extractJsonValue(Json json) {
        if (json == null || json.asString() == null) {
            return null;
        }
        String value = json.asString();
        if ("null".equals(value) || value.isBlank()) {
            return null;
        }
        return value;
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/adapter/FraudDetectionAdapter.java ===
package com.pragma.payments.infrastructure.adapter;

import com.pragma.payments.domain.model.Transaction;
import com.pragma.payments.domain.port.FraudDetectionService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.function.Function;

@Service
public class FraudDetectionAdapter implements FraudDetectionService {

    private static final Logger log = LoggerFactory.getLogger(FraudDetectionAdapter.class);
    private static final String SERVICE_NAME = "fraud-detection";
    private static final String APPROVED = "APPROVED";
    private static final String REJECTED = "REJECTED";
    private static final String REVIEW = "REVIEW";

    private final WebClient webClient;
    private final CircuitBreaker circuitBreaker;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    public FraudDetectionAdapter(WebClient webClient, CircuitBreakerRegistry circuitBreakerRegistry) {
        this.webClient = webClient;
        this.circuitBreakerRegistry = circuitBreakerRegistry;
        this.circuitBreaker = initCircuitBreaker();
    }

    private CircuitBreaker initCircuitBreaker() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
            .failureRateThreshold(50)
            .waitDurationInOpenState(Duration.ofSeconds(30))
            .slidingWindowSize(10)
            .minimumNumberOfCalls(5)
            .permittedNumberOfCallsInHalfOpenState(3)
            .automaticTransitionFromOpenToHalfOpenEnabled(true)
            .build();
        
        return circuitBreakerRegistry.circuitBreaker(SERVICE_NAME, config);
    }

    @Override
    public Mono<String> evaluateForFraud(Transaction transaction) {
        log.info("Evaluating transaction {} for fraud", transaction.id());
        
        Map<String, Object> requestBody = Map.of(
            "transactionId", transaction.id().toString(),
            "accountOrigin", transaction.accountOrigin(),
            "accountDestination", transaction.accountDestination(),
            "amount", transaction.amount().doubleValue(),
            "currency", transaction.currency()
        );

        return webClient.post()
            .uri("/api/v1/fraud/evaluate")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(Map.class)
            .timeout(Duration.ofMillis(150), Mono.error(
                new FraudDetectionTimeoutException("Fraud detection service timeout")))
            .map(response -> parseFraudResponse(response, transaction.id().toString()))
            .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
            .doOnSuccess(result -> log.info("Fraud evaluation for {} completed: {}", 
                transaction.id(), result))
            .doOnError(e -> log.error("Fraud evaluation failed for {}: {}", 
                transaction.id(), e.getMessage()))
            .onErrorResume(FraudDetectionTimeoutException.class, e -> {
                log.warn("Fraud detection timeout for {}, applying fallback", transaction.id());
                return applyFallback(transaction);
            })
            .onErrorResume(CircuitBreakerOpenException.class, e -> {
                log.warn("Circuit breaker open for fraud detection, applying fallback");
                return applyFallback(transaction);
            })
            .onErrorResume(Exception.class, e -> {
                log.error("Unexpected error in fraud detection for {}", transaction.id(), e);
                return Mono.just(REVIEW);
            });
    }

    private Mono<String> applyFallback(Transaction transaction) {
        log.info("Applying fraud detection fallback for transaction {}", transaction.id());
        if (transaction.amount().compareTo(java.math.BigDecimal.valueOf(10000)) > 0) {
            return Mono.just(REVIEW);
        }
        return Mono.just(APPROVED);
    }

    private String parseFraudResponse(Map<String, Object> response, String transactionId) {
        if (response == null) {
            log.warn("Empty fraud response for {}, defaulting to REVIEW", transactionId);
            return REVIEW;
        }

        Object result = response.get("result");
        if (result == null) {
            log.warn("No result field in fraud response for {}, defaulting to REVIEW", transactionId);
            return REVIEW;
        }

        String resultStr = result.toString().toUpperCase();
        return switch (resultStr) {
            case "APPROVE", "APPROVED", "LOW_RISK", "CLEAR" -> APPROVED;
            case "REJECT", "REJECTED", "HIGH_RISK", "FRAUD" -> REJECTED;
            default -> REVIEW;
        };
    }

    @Override
    public Mono<String> getServiceStatus() {
        return webClient.get()
            .uri("/api/v1/fraud/health")
            .retrieve()
            .bodyToMono(Map.class)
            .map(response -> {
                Object status = response.get("status");
                return status != null ? status.toString() : "UNKNOWN";
            })
            .onErrorResume(e -> Mono.just("UNAVAILABLE"))
            .defaultIfEmpty("UNKNOWN");
    }

    public static class FraudDetectionTimeoutException extends RuntimeException {
        public FraudDetectionTimeoutException(String message) {
            super(message);
        }
    }

    public static class CircuitBreakerOpenException extends RuntimeException {
        public CircuitBreakerOpenException(String message) {
            super(message);
        }
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/adapter/RiskAssessmentAdapter.java ===
package com.pragma.payments.infrastructure.adapter;

import com.pragma.payments.domain.model.Transaction;
import com.pragma.payments.domain.port.RiskAssessmentService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.reactor.retry.operator.RetryOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

@Service
public class RiskAssessmentAdapter implements RiskAssessmentService {

    private static final Logger log = LoggerFactory.getLogger(RiskAssessmentAdapter.class);
    private static final String SERVICE_NAME = "risk-assessment";
    private static final String LOW_RISK = "LOW_RISK";
    private static final String MEDIUM_RISK = "MEDIUM_RISK";
    private static final String HIGH_RISK = "HIGH_RISK";
    private static final String UNKNOWN = "UNKNOWN";

    private final WebClient webClient;
    private final CircuitBreaker circuitBreaker;
    private final Retry retry;

    public RiskAssessmentAdapter(WebClient webClient, 
                                  CircuitBreakerRegistry circuitBreakerRegistry,
                                  RetryRegistry retryRegistry) {
        this.webClient = webClient;
        this.circuitBreaker = initCircuitBreaker(circuitBreakerRegistry);
        this.retry = initRetry(retryRegistry);
    }

    private CircuitBreaker initCircuitBreaker(CircuitBreakerRegistry registry) {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
            .failureRateThreshold(40)
            .waitDurationInOpenState(Duration.ofSeconds(45))
            .slidingWindowSize(8)
            .minimumNumberOfCalls(4)
            .permittedNumberOfCallsInHalfOpenState(2)
            .automaticTransitionFromOpenToHalfOpenEnabled(true)
            .build();
        
        return registry.circuitBreaker(SERVICE_NAME, config);
    }

    private Retry initRetry(RetryRegistry registry) {
        RetryConfig config = RetryConfig.custom()
            .maxAttempts(3)
            .waitDuration(Duration.ofMillis(500))
            .retryExceptions(RuntimeException.class)
            .ignoreExceptions(IllegalArgumentException.class)
            .build();
        
        return registry.retry(SERVICE_NAME, config);
    }

    @Override
    public Mono<String> assessRisk(Transaction transaction) {
        log.info("Assessing risk for transaction {}", transaction.id());
        
        Map<String, Object> requestBody = Map.of(
            "transactionId", transaction.id().toString(),
            "accountOrigin", transaction.accountOrigin(),
            "accountDestination", transaction.accountDestination(),
            "amount", transaction.amount().doubleValue(),
            "currency", transaction.currency(),
            "fraudResult", transaction.fraudResult() != null ? transaction.fraudResult() : "PENDING"
        );

        return webClient.post()
            .uri("/api/v1/risk/assess")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(Map.class)
            .timeout(Duration.ofMillis(180), Mono.error(
                new RiskAssessmentTimeoutException("Risk assessment service timeout")))
            .retryWhen(RetryOperator.of(retry))
            .map(response -> parseRiskResponse(response, transaction.id().toString()))
            .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
            .doOnSuccess(result -> log.info("Risk assessment for {} completed: {}", 
                transaction.id(), result))
            .doOnError(e -> log.error("Risk assessment failed for {}: {}", 
                transaction.id(), e.getMessage()))
            .onErrorResume(RiskAssessmentTimeoutException.class, e -> {
                log.warn("Risk assessment timeout for {}, applying fallback", transaction.id());
                return applyFallback(transaction);
            })
            .onErrorResume(Exception.class, e -> {
                log.error("Unexpected error in risk assessment for {}", transaction.id(), e);
                return applyFallback(transaction);
            });
    }

    private Mono<String> applyFallback(Transaction transaction) {
        log.info("Applying risk assessment fallback for transaction {}", transaction.id());
        java.math.BigDecimal amount = transaction.amount();
        
        if (amount.compareTo(java.math.BigDecimal.valueOf(5000)) <= 0) {
            return Mono.just(LOW_RISK);
        } else if (amount.compareTo(java.math.BigDecimal.valueOf(20000)) <= 0) {
            return Mono.just(MEDIUM_RISK);
        } else {
            return Mono.just(HIGH_RISK);
        }
    }

    private String parseRiskResponse(Map<String, Object> response, String transactionId) {
        if (response == null) {
            log.warn("Empty risk response for {}, defaulting to MEDIUM_RISK", transactionId);
            return MEDIUM_RISK;
        }

        Object level = response.get("riskLevel");
        if (level == null) {
            Object score = response.get("riskScore");
            if (score != null) {
                return mapScoreToRiskLevel(score, transactionId);
            }
            log.warn("No riskLevel or riskScore in response for {}, defaulting to MEDIUM_RISK", 
                transactionId);
            return MEDIUM_RISK;
        }

        String levelStr = level.toString().toUpperCase();
        return switch (levelStr) {
            case "LOW", "LOW_RISK", "MINIMAL", "NEGLIGIBLE" -> LOW_RISK;
            case "MEDIUM", "MEDIUM_RISK", "MODERATE" -> MEDIUM_RISK;
            case "HIGH", "HIGH_RISK", "ELEVATED", "CRITICAL" -> HIGH_RISK;
            default -> MEDIUM_RISK;
        };
    }

    private String mapScoreToRiskLevel(Object score, String transactionId) {
        try {
            double numericScore = Double.parseDouble(score.toString());
            if (numericScore <= 30) {
                return LOW_RISK;
            } else if (numericScore <= 70) {
                return MEDIUM_RISK;
            } else {
                return HIGH_RISK;
            }
        } catch (NumberFormatException e) {
            log.warn("Invalid risk score format for {}: {}, defaulting to MEDIUM_RISK", 
                transactionId, score);
            return MEDIUM_RISK;
        }
    }

    @Override
    public Mono<String> getServiceStatus() {
        return webClient.get()
            .uri("/api/v1/risk/health")
            .retrieve()
            .bodyToMono(Map.class)
            .map(response -> {
                Object status = response.get("status");
                return status != null ? status.toString() : UNKNOWN;
            })
            .onErrorResume(e -> Mono.just("UNAVAILABLE"))
            .defaultIfEmpty(UNKNOWN);
    }

    public static class RiskAssessmentTimeoutException extends RuntimeException {
        public RiskAssessmentTimeoutException(String message) {
            super(message);
        }
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/adapter/CoreBankingAdapter.java ===
package com.pragma.payments.infrastructure.adapter;

import com.pragma.payments.domain.model.Transaction;
import com.pragma.payments.domain.port.CoreBankingService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.operator.CircuitBreakerOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CoreBankingAdapter implements CoreBankingService {

    private static final Logger log = LoggerFactory.getLogger(CoreBankingAdapter.class);
    private static final String CORE_BANKING_BASE_URL = "http://localhost:8081/api/core-banking";
    private static final String PROCESS_TRANSACTION_ENDPOINT = "/process";
    private static final String GET_ACCOUNT_ENDPOINT = "/account";
    private static final String VERIFY_BALANCE_ENDPOINT = "/verify-balance";

    private final WebClient webClient;
    private final CircuitBreaker circuitBreaker;

    public CoreBankingAdapter(
            @Qualifier("webClient") WebClient webClient,
            @Qualifier("circuitBreaker") CircuitBreaker circuitBreaker) {
        this.webClient = webClient;
        this.circuitBreaker = circuitBreaker;
    }

    @Override
    public Mono<String> processTransaction(Transaction transaction) {
        log.info("Procesando transacción en core bancario: {}", transaction.id());

        return webClient
                .post()
                .uri(CORE_BANKING_BASE_URL + PROCESS_TRANSACTION_ENDPOINT)
                .bodyValue(buildCoreBankingRequest(transaction))
                .retrieve()
                .bodyToMono(String.class)
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .doOnSuccess(response -> log.info("Transacción procesada exitosamente en core: {}", response))
                .doOnError(error -> log.error("Error al procesar transacción en core bancario: {}", error.getMessage()))
                .onErrorResume(error -> handleCoreBankingError(error, transaction));
    }

    @Override
    public Mono<String> getAccountStatus(String accountNumber) {
        log.info("Consultando estado de cuenta en core bancario: {}", accountNumber);

        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(CORE_BANKING_BASE_URL + GET_ACCOUNT_ENDPOINT)
                        .queryParam("account", accountNumber)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .doOnSuccess(response -> log.info("Estado de cuenta consultado: {}", response))
                .doOnError(error -> log.error("Error al consultar cuenta: {}", error.getMessage()))
                .onErrorResume(error -> {
                    log.warn("Fallback al consultar cuenta {}: {}", accountNumber, error.getMessage());
                    return Mono.just("ACCOUNT_STATUS_UNAVAILABLE");
                });
    }

    @Override
    public Mono<Boolean> verifySufficientBalance(String accountNumber, java.math.BigDecimal amount) {
        log.info("Verificando saldo suficiente para cuenta: {}, monto: {}", accountNumber, amount);

        return webClient
                .post()
                .uri(CORE_BANKING_BASE_URL + VERIFY_BALANCE_ENDPOINT)
                .bodyValue(buildBalanceVerificationRequest(accountNumber, amount))
                .retrieve()
                .bodyToMono(Boolean.class)
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .doOnSuccess(verified -> log.info("Verificación de saldo completada: cuenta={}, monto={}, resultado={}",
                        accountNumber, amount, verified))
                .doOnError(error -> log.error("Error al verificar saldo: {}", error.getMessage()))
                .onErrorResume(error -> {
                    log.warn("Fallback al verificar saldo para {}: {}", accountNumber, error.getMessage());
                    return Mono.just(false);
                });
    }

    private CoreBankingRequest buildCoreBankingRequest(Transaction transaction) {
        return new CoreBankingRequest(
                transaction.id().toString(),
                transaction.accountOrigin(),
                transaction.accountDestination(),
                transaction.amount().toString(),
                transaction.currency(),
                transaction.type().name()
        );
    }

    private BalanceVerificationRequest buildBalanceVerificationRequest(String accountNumber, java.math.BigDecimal amount) {
        return new BalanceVerificationRequest(accountNumber, amount.toString());
    }

    private Mono<String> handleCoreBankingError(Throwable error, Transaction transaction) {
        if (error instanceof io.github.resilience4j.circuitbreaker.CallNotPermittedException) {
            log.warn("Circuit breaker abierto para core bancario, usando fallback para transacción: {}", transaction.id());
            return Mono.just("CORE_BANKING_CIRCUIT_OPEN");
        }
        return Mono.just("CORE_BANKING_ERROR: " + error.getMessage());
    }

    private record CoreBankingRequest(
            String transactionId,
            String accountOrigin,
            String accountDestination,
            String amount,
            String currency,
            String transactionType
    ) {}

    private record BalanceVerificationRequest(
            String accountNumber,
            String amount
    ) {}
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/config/WebClientConfig.java ===
package com.pragma.payments.infrastructure.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class WebClientConfig {

    private static final Logger log = LoggerFactory.getLogger(WebClientConfig.class);
    private static final String CORE_BANKING_BASE_URL = "http://localhost:8081/api/core-banking";
    private static final String FRAUD_DETECTION_BASE_URL = "http://localhost:8082/api/fraud-detection";
    private static final String RISK_ASSESSMENT_BASE_URL = "http://localhost:8083/api/risk-assessment";
    private static final String PAYMENT_GATEWAY_BASE_URL = "http://localhost:8084/api/payment-gateway";

    @Bean("coreBankingWebClient")
    public WebClient coreBankingWebClient() {
        return createWebClient(CORE_BANKING_BASE_URL);
    }

    @Bean("fraudDetectionWebClient")
    public WebClient fraudDetectionWebClient() {
        return createWebClient(FRAUD_DETECTION_BASE_URL);
    }

    @Bean("riskAssessmentWebClient")
    public WebClient riskAssessmentWebClient() {
        return createWebClient(RISK_ASSESSMENT_BASE_URL);
    }

    @Bean("paymentGatewayWebClient")
    public WebClient paymentGatewayWebClient() {
        return createWebClient(PAYMENT_GATEWAY_BASE_URL);
    }

    @Bean("webClient")
    public WebClient defaultWebClient() {
        return createWebClient(CORE_BANKING_BASE_URL);
    }

    private WebClient createWebClient(String baseUrl) {
        log.info("Creando WebClient con base URL: {}", baseUrl);

        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024))
                .build();

        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.CONNECTION, "keep-alive")
                .exchangeStrategies(strategies)
                .clientConnector(new reactor.netty.http.client.HttpClient()
                        .option(io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                        .responseTimeout(Duration.ofSeconds(10))
                        .poolResources(
                                io.netty.channel.pool.FixedChannelPool.builder()
                                        .maxConnections(100)
                                        .pendingAcquireMaxCount(200)
                                        .pendingAcquireTimeout(Duration.ofSeconds(30))
                                        .build()
                        ))
                .filter((request, next) -> {
                    log.debug("Enviando request a: {} {}", request.method(), request.url());
                    return next.exchange(request)
                            .doOnTerminate(() -> log.debug("Request completado: {} {}", request.method(), request.url()))
                            .doOnError(error -> log.error("Error en request: {} {} - {}",
                                    request.method(), request.url(), error.getMessage()));
                })
                .build();
    }

    @Bean("coreBankingCircuitBreaker")
    public CircuitBreaker coreBankingCircuitBreaker() {
        return createCircuitBreaker("coreBanking", 3, 60, 10);
    }

    @Bean("fraudDetectionCircuitBreaker")
    public CircuitBreaker fraudDetectionCircuitBreaker() {
        return createCircuitBreaker("fraudDetection", 5, 30, 5);
    }

    @Bean("riskAssessmentCircuitBreaker")
    public CircuitBreaker riskAssessmentCircuitBreaker() {
        return createCircuitBreaker("riskAssessment", 5, 30, 5);
    }

    @Bean("paymentGatewayCircuitBreaker")
    public CircuitBreaker paymentGatewayCircuitBreaker() {
        return createCircuitBreaker("paymentGateway", 3, 120, 15);
    }

    private CircuitBreaker createCircuitBreaker(String name, int failureRateThreshold, int waitDurationInSeconds, int slidingWindowSize) {
        log.info("Configurando CircuitBreaker '{}': failureRateThreshold={}%, waitDuration={}s, slidingWindow={}",
                name, failureRateThreshold, waitDurationInSeconds, slidingWindowSize);

        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(failureRateThreshold)
                .waitDurationInOpenState(Duration.ofSeconds(waitDurationInSeconds))
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .slidingWindowSize(slidingWindowSize)
                .minimumNumberOfCalls(10)
                .permittedNumberOfCallsInHalfOpenState(3)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .recordExceptions(java.io.IOException.class, java.util.concurrent.TimeoutException.class,
                        org.springframework.web.reactive.function.client.WebClientResponseException.class)
                .build();

        return CircuitBreaker.of(name, config);
    }

    @Bean
    public reactor.core.scheduler.Scheduler boundedElasticScheduler() {
        log.info("Configurando scheduler boundedElastic con pool personalizado");
        return Schedulers.boundedElastic(
                Thread.ofVirtual()
                        .name("webclient-bounded-", -1)
                        .factory(),
                200,
                60,
                java.util.concurrent.TimeUnit.SECONDS
        );
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/controller/TransactionController.java ===
package com.pragma.payments.infrastructure.controller;

import com.pragma.payments.application.service.TransactionService;
import com.pragma.payments.domain.model.TransactionRequest;
import com.pragma.payments.domain.model.TransactionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private static final Logger log = LoggerFactory.getLogger(TransactionController.class);

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public Mono<ResponseEntity<TransactionResponse>> createTransaction(@RequestBody TransactionRequest request) {
        log.info("Recibida solicitud de transacción: origen={}, destino={}, monto={}",
                request.accountOrigin(), request.accountDestination(), request.amount());

        return transactionService.processTransaction(request)
                .map(transaction -> {
                    log.info("Transacción procesada exitosamente: id={}, estado={}",
                            transaction.id(), transaction.status());
                    return ResponseEntity
                            .status(HttpStatus.CREATED)
                            .body(TransactionResponse.fromTransaction(transaction));
                })
                .doOnError(error -> log.error("Error al procesar transacción: {}", error.getMessage()))
                .onErrorResume(error -> {
                    log.error("Error en createTransaction: {}", error.getMessage());
                    return Mono.just(ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(TransactionResponse.error(error.getMessage())));
                });
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<TransactionResponse>> getTransactionById(@PathVariable UUID id) {
        log.info("Consultando transacción por ID: {}", id);

        return transactionService.findById(id)
                .map(transaction -> {
                    log.info("Transacción encontrada: id={}, estado={}", id, transaction.status());
                    return ResponseEntity.ok(TransactionResponse.fromTransaction(transaction));
                })
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .doOnError(error -> log.error("Error al buscar transacción {}: {}", id, error.getMessage()));
    }

    @GetMapping("/account/{accountOrigin}")
    public Flux<TransactionResponse> getTransactionsByAccountOrigin(@PathVariable String accountOrigin) {
        log.info("Consultando transacciones por cuenta origen: {}", accountOrigin);

        return transactionService.findByAccountOrigin(accountOrigin)
                .doOnSubscribe(s -> log.debug("Iniciandoflux de transacciones para cuenta: {}", accountOrigin))
                .doOnComplete(() -> log.debug("Completado flujo de transacciones para cuenta: {}", accountOrigin))
                .doOnError(error -> log.error("Error al buscar transacciones por cuenta {}: {}",
                        accountOrigin, error.getMessage()))
                .map(TransactionResponse::fromTransaction);
    }

    @GetMapping("/account-destination/{accountDestination}")
    public Flux<TransactionResponse> getTransactionsByAccountDestination(@PathVariable String accountDestination) {
        log.info("Consultando transacciones por cuenta destino: {}", accountDestination);

        return transactionService.findByAccountDestination(accountDestination)
                .doOnSubscribe(s -> log.debug("Iniciando flujo de transacciones para cuenta destino: {}", accountDestination))
                .doOnComplete(() -> log.debug("Completado flujo de transacciones para cuenta destino: {}", accountDestination))
                .doOnError(error -> log.error("Error al buscar transacciones por cuenta destino {}: {}",
                        accountDestination, error.getMessage()))
                .map(TransactionResponse::fromTransaction);
    }

    @GetMapping("/date-range")
    public Flux<TransactionResponse> getTransactionsByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        log.info("Consultando transacciones entre {} y {}", startDate, endDate);

        return transactionService.findByCreatedAtBetween(startDate, endDate)
                .doOnSubscribe(s -> log.debug("Iniciando flujo de transacciones por rango de fechas"))
                .doOnComplete(() -> log.debug("Completado flujo de transacciones por rango de fechas"))
                .doOnError(error -> log.error("Error al buscar transacciones por fecha: {}", error.getMessage()))
                .map(TransactionResponse::fromTransaction);
    }

    @PatchMapping("/{id}/status")
    public Mono<ResponseEntity<TransactionResponse>> updateTransactionStatus(
            @PathVariable UUID id,
            @RequestParam String status) {
        log.info("Actualizando estado de transacción {} a {}", id, status);

        return transactionService.updateStatus(id, status)
                .map(transaction -> {
                    log.info("Estado de transacción {} actualizado a {}", id, status);
                    return ResponseEntity.ok(TransactionResponse.fromTransaction(transaction));
                })
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .doOnError(error -> log.error("Error al actualizar estado de transacción {}: {}", id, error.getMessage()));
    }

    @GetMapping("/health")
    public Mono<ResponseEntity<HealthResponse>> healthCheck() {
        log.debug("Verificando salud del servicio de transacciones");

        return transactionService.getServiceStatus()
                .map(status -> ResponseEntity.ok(new HealthResponse("UP", status)))
                .onErrorResume(error -> {
                    log.warn("Health check falló: {}", error.getMessage());
                    return Mono.just(ResponseEntity.ok(new HealthResponse("DEGRADED", "Servicios externos no disponibles")));
                });
    }

    public record HealthResponse(String status, String message) {}
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/config/ResilienceConfig.java ===
package com.pragma.payments.infrastructure.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ResilienceConfig {

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(60))
                .slidingWindowSize(10)
                .minimumNumberOfCalls(5)
                .permittedNumberOfCallsInHalfOpenState(3)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .build();
        return CircuitBreakerRegistry.of(config);
    }

    @Bean
    public CircuitBreaker paymentCircuitBreaker(CircuitBreakerRegistry circuitBreakerRegistry) {
        return circuitBreakerRegistry.circuitBreaker("paymentGateway");
    }

    @Bean
    public CircuitBreaker fraudDetectionCircuitBreaker(CircuitBreakerRegistry circuitBreakerRegistry) {
        return circuitBreakerRegistry.circuitBreaker("fraudDetection");
    }

    @Bean
    public CircuitBreaker riskAssessmentCircuitBreaker(CircuitBreakerRegistry circuitBreakerRegistry) {
        return circuitBreakerRegistry.circuitBreaker("riskAssessment");
    }

    @Bean
    public CircuitBreaker coreBankingCircuitBreaker(CircuitBreakerRegistry circuitBreakerRegistry) {
        return circuitBreakerRegistry.circuitBreaker("coreBanking");
    }

    @Bean
    public RetryRegistry retryRegistry() {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(500))
                .ignoreExceptions(Exception.class)
                .build();
        return RetryRegistry.of(config);
    }

    @Bean
    public Retry paymentRetry(RetryRegistry retryRegistry) {
        return retryRegistry.retry("paymentGatewayRetry");
    }

    @Bean
    public Retry fraudDetectionRetry(RetryRegistry retryRegistry) {
        return retryRegistry.retry("fraudDetectionRetry");
    }

    @Bean
    public Retry riskAssessmentRetry(RetryRegistry retryRegistry) {
        return retryRegistry.retry("riskAssessmentRetry");
    }

    @Bean
    public Retry coreBankingRetry(RetryRegistry retryRegistry) {
        return retryRegistry.retry("coreBankingRetry");
    }

    @Bean
    public BulkheadRegistry bulkheadRegistry() {
        BulkheadConfig config = BulkheadConfig.custom()
                .maxConcurrentCalls(100)
                .maxWaitDuration(Duration.ofMillis(500))
                .build();
        return BulkheadRegistry.of(config);
    }

    @Bean
    public Bulkhead paymentBulkhead(BulkheadRegistry bulkheadRegistry) {
        return bulkheadRegistry.bulkhead("paymentGatewayBulkhead");
    }

    @Bean
    public Bulkhead fraudDetectionBulkhead(BulkheadRegistry bulkheadRegistry) {
        return bulkheadRegistry.bulkhead("fraudDetectionBulkhead");
    }

    @Bean
    public Bulkhead riskAssessmentBulkhead(BulkheadRegistry bulkheadRegistry) {
        return bulkheadRegistry.bulkhead("riskAssessmentBulkhead");
    }

    @Bean
    public Bulkhead coreBankingBulkhead(BulkheadRegistry bulkheadRegistry) {
        return bulkheadRegistry.bulkhead("coreBankingBulkhead");
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/adapter/PaymentGatewayAdapter.java ===
package com.pragma.payments.infrastructure.adapter;

import com.pragma.payments.domain.model.Transaction;
import com.pragma.payments.domain.port.PaymentGatewayService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.bulkhead.Bulkhead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class PaymentGatewayAdapter implements PaymentGatewayService {

    private static final Logger log = LoggerFactory.getLogger(PaymentGatewayAdapter.class);
    private static final String GATEWAY_BASE_URL = "http://payment-gateway-service:8080";

    private final WebClient webClient;
    private final CircuitBreaker circuitBreaker;
    private final Retry retry;
    private final Bulkhead bulkhead;

    public PaymentGatewayAdapter(
            WebClient webClient,
            @Qualifier("paymentCircuitBreaker") CircuitBreaker circuitBreaker,
            @Qualifier("paymentRetry") Retry retry,
            @Qualifier("paymentBulkhead") Bulkhead bulkhead) {
        this.webClient = webClient;
        this.circuitBreaker = circuitBreaker;
        this.retry = retry;
        this.bulkhead = bulkhead;
    }

    @Override
    public Mono<String> processPayment(Transaction transaction) {
        return Mono.fromCallable(() -> bulkhead.executeSupplier(() -> ""))
                .transformDeferred(it -> Retry.decorateRetryMono(retry, it))
                .transformDeferred(it -> CircuitBreaker.decorateMono(circuitBreaker, it))
                .flatMap(result -> executePaymentRequest(transaction))
                .doOnSuccess(response -> log.info("Pago procesado exitosamente para transacción: {}", transaction.id()))
                .doOnError(error -> log.error("Error al procesar pago para transacción: {}", transaction.id(), error))
                .onErrorResume(Exception.class, error -> Mono.just("PAYMENT_FAILED:" + error.getMessage()));
    }

    private Mono<String> executePaymentRequest(Transaction transaction) {
        PaymentRequest request = new PaymentRequest(
                transaction.accountOrigin(),
                transaction.accountDestination(),
                transaction.amount()
        );

        return webClient.post()
                .uri(GATEWAY_BASE_URL + "/api/payments")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(PaymentResponse.class)
                .map(response -> {
                    if ("APPROVED".equals(response.status())) {
                        return "PAYMENT_APPROVED:" + response.transactionId();
                    } else {
                        return "PAYMENT_DECLINED:" + response.reason();
                    }
                })
                .onErrorResume(WebClientException.class, error -> {
                    log.error("Error de comunicación con gateway de pagos", error);
                    return Mono.just("PAYMENT_ERROR:CONNECTION_FAILED");
                });
    }

    @Override
    public Mono<String> getPaymentStatus(String paymentId) {
        return Mono.fromCallable(() -> bulkhead.executeSupplier(() -> ""))
                .transformDeferred(it -> Retry.decorateRetryMono(retry, it))
                .transformDeferred(it -> CircuitBreaker.decorateMono(circuitBreaker, it))
                .flatMap(result -> executeStatusRequest(paymentId))
                .doOnSuccess(response -> log.info("Estado de pago consultado: {}", paymentId))
                .doOnError(error -> log.error("Error al consultar estado de pago: {}", paymentId, error))
                .onErrorResume(Exception.class, error -> Mono.just("STATUS_ERROR:" + error.getMessage()));
    }

    private Mono<String> executeStatusRequest(String paymentId) {
        return webClient.get()
                .uri(GATEWAY_BASE_URL + "/api/payments/{paymentId}", paymentId)
                .retrieve()
                .bodyToMono(PaymentResponse.class)
                .map(response -> response.status() + ":" + response.transactionId())
                .onErrorResume(WebClientException.class, error -> {
                    log.error("Error al consultar estado del pago", error);
                    return Mono.just("STATUS_ERROR:CONNECTION_FAILED");
                });
    }

    private record PaymentRequest(String originAccount, String destinationAccount, java.math.BigDecimal amount) {}

    private record PaymentResponse(String transactionId, String status, String reason) {}
}

// === ARCHIVO: src/test/java/com/pragma/payments/application/service/TransactionServiceTest.java ===
package com.pragma.payments.application.service;

import com.pragma.payments.domain.model.Transaction;
import com.pragma.payments.domain.model.TransactionStatus;
import com.pragma.payments.domain.port.FraudDetectionService;
import com.pragma.payments.domain.port.TransactionRepository;
import com.pragma.payments.infrastructure.adapter.CoreBankingAdapter;
import com.pragma.payments.infrastructure.adapter.PaymentGatewayAdapter;
import com.pragma.payments.infrastructure.adapter.RiskAssessmentAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para TransactionService - Paradigma Reactivo")
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private FraudDetectionService fraudDetectionService;

    @Mock
    private RiskAssessmentAdapter riskAssessmentAdapter;

    @Mock
    private CoreBankingAdapter coreBankingAdapter;

    @Mock
    private PaymentGatewayAdapter paymentGatewayAdapter;

    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionService = new TransactionService(
            transactionRepository,
            fraudDetectionService,
            riskAssessmentAdapter,
            coreBankingAdapter,
            paymentGatewayAdapter
        );
    }

    @Test
    @DisplayName("Debe crear transacción exitosamente con todos los servicios de validación")
    void shouldCreateTransactionSuccessfully() {
        Transaction transaction = Transaction.create(
            "ACC-001",
            "ACC-002",
            new BigDecimal("1000.00"),
            "Pago de servicio"
        );

        when(transactionRepository.save(any(Transaction.class)))
            .thenReturn(Mono.just(transaction.withFraudDetectionResult("APPROVED")));
        when(fraudDetectionService.evaluateForFraud(any(Transaction.class)))
            .thenReturn(Mono.just("APPROVED"));
        when(riskAssessmentAdapter.evaluateRisk(any(Transaction.class)))
            .thenReturn(Mono.just("LOW_RISK"));
        when(coreBankingAdapter.processTransaction(any(Transaction.class)))
            .thenReturn(Mono.just("PROCESSED"));
        when(paymentGatewayAdapter.executePayment(any(Transaction.class)))
            .thenReturn(Mono.just("SUCCESS"));

        StepVerifier.create(transactionService.createTransaction(transaction))
            .assertNext(result -> {
                assertThat(result).isNotNull();
                assertThat(result.fraudDetectionResult()).isEqualTo("APPROVED");
            })
            .verifyComplete();

        verify(fraudDetectionService).evaluateForFraud(any(Transaction.class));
        verify(riskAssessmentAdapter).evaluateRisk(any(Transaction.class));
        verify(coreBankingAdapter).processTransaction(any(Transaction.class));
    }

    @Test
    @DisplayName("Debe rechazar transacción cuando el servicio de fraude retorna FRAUD_DETECTED")
    void shouldRejectTransactionWhenFraudDetected() {
        Transaction transaction = Transaction.create(
            "ACC-001",
            "ACC-002",
            new BigDecimal("5000.00"),
            "Transferencia sospechosa"
        );

        when(fraudDetectionService.evaluateForFraud(any(Transaction.class)))
            .thenReturn(Mono.just("FRAUD_DETECTED"));
        when(transactionRepository.save(any(Transaction.class)))
            .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(transactionService.createTransaction(transaction))
            .expectErrorMatches(throwable -> 
                throwable.getMessage().contains("Transacción rechazada por fraude") ||
                throwable.getMessage().contains("FRAUD")
            )
            .verify();
    }

    @Test
    @DisplayName("Debe manejar error al comunicarse con el servicio de fraude")
    void shouldHandleFraudServiceError() {
        Transaction transaction = Transaction.create(
            "ACC-001",
            "ACC-002",
            new BigDecimal("1000.00"),
            "Pago de servicio"
        );

        when(fraudDetectionService.evaluateForFraud(any(Transaction.class)))
            .thenReturn(Mono.error(new RuntimeError("Servicio de fraude no disponible"))));
        when(transactionRepository.save(any(Transaction.class)))
            .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(transactionService.createTransaction(transaction))
            .expectError()
            .verify();
    }

    @Test
    @DisplayName("Debe obtener transacción por ID exitosamente")
    void shouldGetTransactionById() {
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = Transaction.create(
            "ACC-001",
            "ACC-002",
            new BigDecimal("1000.00"),
            "Pago de servicio"
        );

        when(transactionRepository.findById(transactionId))
            .thenReturn(Mono.just(transaction));

        StepVerifier.create(transactionService.getTransactionById(transactionId))
            .assertNext(result -> {
                assertThat(result).isNotNull();
                assertThat(result.accountOrigin()).isEqualTo("ACC-001");
            })
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe retornar Mono.empty cuando la transacción no existe")
    void shouldReturnEmptyWhenTransactionNotFound() {
        UUID transactionId = UUID.randomUUID();

        when(transactionRepository.findById(transactionId))
            .thenReturn(Mono.empty());

        StepVerifier.create(transactionService.getTransactionById(transactionId))
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe obtener transacciones por cuenta de origen")
    void shouldGetTransactionsByAccountOrigin() {
        String accountOrigin = "ACC-001";
        Transaction t1 = Transaction.create(accountOrigin, "ACC-002", new BigDecimal("100.00"), "Pago 1");
        Transaction t2 = Transaction.create(accountOrigin, "ACC-003", new BigDecimal("200.00"), "Pago 2");

        when(transactionRepository.findByAccountOrigin(accountOrigin))
            .thenReturn(Flux.just(t1, t2));

        StepVerifier.create(transactionService.getTransactionsByAccountOrigin(accountOrigin))
            .expectNextCount(2)
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe actualizar el estado de una transacción")
    void shouldUpdateTransactionStatus() {
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = Transaction.create(
            "ACC-001",
            "ACC-002",
            new BigDecimal("1000.00"),
            "Pago de servicio"
        ).markAsCompleted();

        when(transactionRepository.updateStatus(transactionId, TransactionStatus.COMPLETED.name()))
            .thenReturn(Mono.just(transaction));

        StepVerifier.create(
            transactionService.updateTransactionStatus(transactionId, TransactionStatus.COMPLETED.name())
        )
            .assertNext(result -> {
                assertThat(result).isNotNull();
            })
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe encadenar servicios de forma reactiva con flatMap")
    void shouldChainServicesReactively() {
        Transaction transaction = Transaction.create(
            "ACC-001",
            "ACC-002",
            new BigDecimal("500.00"),
            "Pago encadenado"
        );

        when(fraudDetectionService.evaluateForFraud(any(Transaction.class)))
            .thenReturn(Mono.just("APPROVED"));
        when(riskAssessmentAdapter.evaluateRisk(any(Transaction.class)))
            .thenReturn(Mono.just("LOW_RISK"));
        when(transactionRepository.save(any(Transaction.class)))
            .thenReturn(Mono.just(transaction));

        StepVerifier.create(
            transactionService.createTransaction(transaction)
                .flatMap(t -> transactionRepository.findById(t.id()))
        )
            .assertNext(result -> assertThat(result).isNotNull())
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe aplicar manejo de errores con onErrorResume")
    void shouldApplyErrorHandlingWithOnErrorResume() {
        Transaction transaction = Transaction.create(
            "ACC-001",
            "ACC-002",
            new BigDecimal("1000.00"),
            "Pago con manejo de errores"
        );

        when(fraudDetectionService.evaluateForFraud(any(Transaction.class)))
            .thenReturn(Mono.error(new RuntimeException("Error temporal")));
        when(transactionRepository.save(any(Transaction.class)))
            .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(
            transactionService.createTransactionWithFallback(transaction)
        )
            .assertNext(result -> {
                assertThat(result).isNotNull();
            })
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe filtrar transacciones con operador filter")
    void shouldFilterTransactionsWithOperator() {
        Transaction t1 = Transaction.create("ACC-001", "ACC-002", new BigDecimal("100.00"), "Pago bajo");
        Transaction t2 = Transaction.create("ACC-001", "ACC-003", new BigDecimal("5000.00"), "Pago alto");
        Transaction t3 = Transaction.create("ACC-001", "ACC-004", new BigDecimal("200.00"), "Pago bajo 2");

        when(transactionRepository.findByAccountOrigin("ACC-001"))
            .thenReturn(Flux.just(t1, t2, t3));

        StepVerifier.create(
            transactionService.getTransactionsByAccountOrigin("ACC-001")
                .filter(t -> t.amount().compareTo(new BigDecimal("1000.00")) < 0)
        )
            .expectNextCount(2)
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe transformar transacciones con operador map")
    void shouldTransformTransactionsWithMap() {
        Transaction transaction = Transaction.create(
            "ACC-001",
            "ACC-002",
            new BigDecimal("1000.00"),
            "Pago original"
        );

        when(transactionRepository.findById(transaction.id()))
            .thenReturn(Mono.just(transaction));

        StepVerifier.create(
            transactionService.getTransactionById(transaction.id())
                .map(t -> "Transacción: " + t.id() + "Monto: " + t.amount())
        )
            .assertNext(result -> assertThat(result).contains("Transacción:"))
            .verifyComplete();
    }
}

// === ARCHIVO: src/test/java/com/pragma/payments/infrastructure/adapter/FraudDetectionAdapterTest.java ===
package com.pragma.payments.infrastructure.adapter;

import com.pragma.payments.domain.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Integración para FraudDetectionAdapter")
class FraudDetectionAdapterTest {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    private FraudDetectionAdapter fraudDetectionAdapter;

    @BeforeEach
    void setUp() {
        fraudDetectionAdapter = new FraudDetectionAdapter(webClient);
    }

    @Test
    @DisplayName("Debe retornar APPROVED cuando el servicio externo aprueba la transacción")
    void shouldReturnApprovedWhenServiceApproves() {
        Transaction transaction = Transaction.create(
            "ACC-001",
            "ACC-002",
            new BigDecimal("1000.00"),
            "Pago de servicio"
        );

        mockWebClientResponse("{\"result\":\"APPROVED\"}");

        StepVerifier.create(fraudDetectionAdapter.evaluateForFraud(transaction))
            .assertNext(result -> {
                assertThat(result).isEqualTo("APPROVED");
            })
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe retornar FRAUD_DETECTED cuando se detecta fraude")
    void shouldReturnFraudDetectedWhenFraudIsFound() {
        Transaction transaction = Transaction.create(
            "ACC-001",
            "ACC-002",
            new BigDecimal("50000.00"),
            "Transferencia inusualmente alta"
        );

        mockWebClientResponse("{\"result\":\"FRAUD_DETECTED\",\"reason\":\"Monto exceeds limit\"}");

        StepVerifier.create(fraudDetectionAdapter.evaluateForFraud(transaction))
            .assertNext(result -> {
                assertThat(result).isEqualTo("FRAUD_DETECTED");
            })
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe manejar error de timeout del servicio externo")
    void shouldHandleTimeoutError() {
        Transaction transaction = Transaction.create(
            "ACC-001",
            "ACC-002",
            new BigDecimal("1000.00"),
            "Pago de servicio"
        );

        when(webClient.post())
            .thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString()))
            .thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.header(anyString(), anyString()))
            .thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve())
            .thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class))
            .thenReturn(Mono.error(new RuntimeException("Connection timeout")));

        StepVerifier.create(
            fraudDetectionAdapter.evaluateForFraud(transaction)
                .timeout(Duration.ofSeconds(5))
        )
            .expectErrorMatches(throwable -> 
                throwable.getMessage().contains("timeout") ||
                throwable.getMessage().contains("Connection")
            )
            .verify();
    }

    @Test
    @DisplayName("Debe manejar respuesta inválida del servicio externo")
    void shouldHandleInvalidResponse() {
        Transaction transaction = Transaction.create(
            "ACC-001",
            "ACC-002",
            new BigDecimal("1000.00"),
            "Pago de servicio"
        );

        mockWebClientResponse("invalid-json-response");

        StepVerifier.create(
            fraudDetectionAdapter.evaluateForFraud(transaction)
                .onErrorResume(Exception.class, e -> Mono.just("ERROR"))
        )
            .assertNext(result -> {
                assertThat(result).isEqualTo("ERROR");
            })
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe retornar estado del servicio cuando se consulta el estado")
    void shouldReturnServiceStatus() {
        mockWebClientResponse("{\"status\":\"UP\",\"timestamp\":\"2024-01-01T00:00:00Z\"}");

        StepVerifier.create(fraudDetectionAdapter.getServiceStatus())
            .assertNext(status -> {
                assertThat(status).isIn("UP", "DOWN", "DEGRADED");
            })
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe aplicar retry automático en caso de error transitorio")
    void shouldApplyRetryOnTransientError() {
        Transaction transaction = Transaction.create(
            "ACC-001",
            "ACC-002",
            new BigDecimal("1000.00"),
            "Pago de servicio"
        );

        when(webClient.post())
            .thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString()))
            .thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.header(anyString(), anyString()))
            .thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve())
            .thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class))
            .thenReturn(
                Mono.error(new RuntimeException("Temporary failure")),
                Mono.just("{\"result\":\"APPROVED\"}")
            );

        StepVerifier.create(
            fraudDetectionAdapter.evaluateForFraud(transaction)
                .retry(1)
        )
            .assertNext(result -> assertThat(result).isEqualTo("APPROVED"))
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe construir correctamente el payload JSON enviado al servicio")
    void shouldBuildCorrectPayloadJson() {
        Transaction transaction = Transaction.create(
            "ACC-001",
            "ACC-002",
            new BigDecimal("2500.50"),
            "Pago de prueba"
        );

        mockWebClientResponse("{\"result\":\"APPROVED\"}");

        StepVerifier.create(fraudDetectionAdapter.evaluateForFraud(transaction))
            .assertNext(result -> assertThat(result).isNotBlank())
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe manejar excepción cuando el servicio retorna código de error HTTP")
    void shouldHandleHttpErrorCode() {
        Transaction transaction = Transaction.create(
            "ACC-001",
            "ACC-002",
            new BigDecimal("1000.00"),
            "Pago de servicio"
        );

        when(webClient.post())
            .thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString()))
            .thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.header(anyString(), anyString()))
            .thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve())
            .thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class))
            .thenReturn(Mono.error(new RuntimeException("HTTP 500: Internal Server Error")));

        StepVerifier.create(
            fraudDetectionAdapter.evaluateForFraud(transaction)
                .onErrorResume(Exception.class, e -> Mono.just("FALLBACK_APPROVED"))
        )
            .assertNext(result -> assertThat(result).isEqualTo("FALLBACK_APPROVED"))
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe usar circuit breaker para proteger el servicio externo")
    void shouldUseCircuitBreakerForProtection() {
        Transaction transaction = Transaction.create(
            "ACC-001",
            "ACC-002",
            new BigDecimal("1000.00"),
            "Pago de servicio"
        );

        mockWebClientResponse("{\"result\":\"APPROVED\"}");

        StepVerifier.create(
            fraudDetectionAdapter.evaluateForFraud(transaction)
        )
            .assertNext(result -> assertThat(result).isIn("APPROVED", "FRAUD_DETECTED", "ERROR"))
            .verifyComplete();
    }

    private void mockWebClientResponse(String responseBody) {
        when(webClient.post())
            .thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString()))
            .thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.header(anyString(), anyString()))
            .thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve())
            .thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class))
            .thenReturn(Mono.just(responseBody));
    }
}
```
