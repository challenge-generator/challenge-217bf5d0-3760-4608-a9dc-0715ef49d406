# Implementación de Paradigma Reactivo en Sistema de Pagos

El sistema de pagos de una institución financiera requiere una actualización para manejar un mayor volumen de transacciones con resiliencia y escalabilidad. El equipo de desarrollo ha decidido adoptar un paradigma reactivo para mejorar el rendimiento y la resiliencia del sistema. Los actores involucrados son el originador de créditos, el motor antifraude, el buró de riesgos, el core bancario, y el gateway de pagos. El sistema debe procesar un mínimo de 1 500 solicitudes por segundo en hora pico, con una latencia máxima de 200ms y una disponibilidad del 99.9%.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | Adopción de Paradigmas de Programación No Imperativos: Con Enfoque Reactivo y Funcional |
| **Nivel** | senior-l2 |
| **Tipo** | practical |
| **Tiempo estimado** | 4 semanas |

## Fases del Reto

### Fase 0: Configuración del Proyecto

**Objetivo:** Obtener el proyecto base funcional enviando el Código Base a un asistente de IA, que lo analizará, corregirá errores y generará un ZIP listo para usar.

**Tiempo estimado:** 15-30 minutos

**Instrucciones:**

- Asegúrate de tener instalado para ejecutar el proyecto: JDK 17+, Maven 3.9+, IDE con soporte Java.
- Copia todo el contenido del campo **Código Base** de este reto — incluyendo el texto de instrucciones que aparece al inicio.
- Abre un asistente de IA (Claude en claude.ai, ChatGPT o Gemini — se recomienda Claude), pega el contenido copiado en el chat y envíalo.
- El asistente analizará los archivos, corregirá errores y generará un archivo ZIP descargable. Descárgalo y extráelo en la carpeta donde quieras trabajar.
- Ejecuta `mvn compile` en la raíz. Si no hay errores, estás listo.

**Entregable:** El proyecto compila/arranca sin errores.

<details>
<summary>Pistas de conocimiento</summary>

- Copia el Código Base completo incluyendo el texto de instrucciones al inicio — esas instrucciones le indican al asistente exactamente qué hacer con los archivos.
- Si el asistente no genera el ZIP automáticamente al terminar el análisis, escríbele: "genera el ZIP ahora".
- Si el proyecto tiene errores al arrancar, comparte el mensaje de error con el mismo asistente para que lo corrija.

</details>

### Fase 1: Exploración y Modelado del Dominio

**Objetivo:** Entender y modelar el dominio del sistema de pagos con enfoque reactivo.

**Tiempo estimado:** 1 semana

**Instrucciones:**

- Identificar y describir los actores y sus interacciones en el dominio del sistema de pagos.
- Modelar el flujo de transacciones reactivo, incluyendo los eventos clave y las transiciones de estado.

**Entregable:** Diagrama de relaciones y descripción del flujo de transacciones reactivo.

<details>
<summary>Pistas de conocimiento</summary>

- Los cuatro pilares del manifiesto de sistemas reactivos.
- Ejemplos de sistemas reactivos en la industria financiera.

</details>

### Fase 2: Implementación de Operadores Reactivos Básicos

**Objetivo:** Implementar operadores reactivos básicos para manejar el flujo de transacciones.

**Tiempo estimado:** 1 semana

**Instrucciones:**

- Seleccionar y aplicar operadores reactivos para filtrar, transformar y combinar flujos de datos.
- Implementar la lógica de manejo de errores y recuperación en el flujo de transacciones.

**Entregable:** Código que implementa operadores reactivos básicos y manejo de errores en el flujo de transacciones.

<details>
<summary>Pistas de conocimiento</summary>

- Operadores reactivos comunes como map, filter, concat, y merge.
- Estrategias de manejo de errores y recuperación en sistemas reactivos.

</details>

### Fase 3: Optimización y Escalabilidad del Sistema Reactivo

**Objetivo:** Optimizar y escalar el sistema reactivo para manejar un mayor volumen de transacciones.

**Tiempo estimado:** 1 semana

**Instrucciones:**

- Identificar y aplicar técnicas de optimización y escalabilidad en el sistema reactivo.
- Evaluar el rendimiento y la resiliencia del sistema bajo carga.

**Entregable:** Código optimizado y escalado del sistema reactivo, junto con resultados de evaluación de rendimiento y resiliencia.

<details>
<summary>Pistas de conocimiento</summary>

- Técnicas de optimización y escalabilidad en sistemas reactivos.
- Herramientas y métricas para evaluar el rendimiento y la resiliencia de sistemas reactivos.

</details>

### Fase 4: Revisión y Mejora Continua

**Objetivo:** Revisar y mejorar continuamente el sistema reactivo.

**Tiempo estimado:** 1 semana

**Instrucciones:**

- Revisar el código y la implementación del sistema reactivo.
- Identificar áreas de mejora y aplicar cambios iterativamente.

**Entregable:** Código revisado y mejorado del sistema reactivo, junto con un informe de mejoras realizadas.

<details>
<summary>Pistas de conocimiento</summary>

- Prácticas de revisión de código y mejora continua en sistemas reactivos.
- Herramientas y metodologías para identificar y aplicar mejoras iterativamente.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué son los sistemas reactivos y cuáles son sus cuatro pilares?
- **paraQueSirve**: ¿Para qué sirve adoptar un paradigma reactivo en un sistema de pagos?
- **comoSeUsa**: ¿Cómo se aplican operadores reactivos básicos en el flujo de transacciones?
- **erroresComunes**: ¿Cuáles son los errores comunes al implementar un sistema reactivo y cómo se pueden evitar?
- **queDecisionesImplica**: ¿Qué decisiones implica la adopción de un paradigma reactivo en términos de rendimiento, escalabilidad y resiliencia?

## Criterios de Evaluacion

- Implementación correcta de operadores reactivos básicos.
- Manejo efectivo de errores y recuperación en el flujo de transacciones.
- Optimización y escalabilidad del sistema reactivo.
- Revisión y mejora continua del sistema reactivo.

## Como trabajar con un asistente de IA

Hay dos caminos, elegi uno:

- **AGENTS.md** (recomendado) — instrucciones nativas del repo. Abri esta carpeta con tu agente local (Claude Code, Cursor, Codex, Copilot, Gemini) y las carga solo. Sabe que archivos faltan y con que comando se verifica, y completa el scaffold escribiendo en disco.
- **PROMPT_MEJORA.md** — para copiar y pegar en un chat (claude.ai, ChatGPT). Devuelve un ZIP con el proyecto. Sirve si no tenes un agente en el IDE.

Ninguno de los dos resuelve las fases del reto: eso es tu trabajo.

## Verificacion

El proyecto esta listo para trabajar cuando este comando corre sin errores:

```bash
mvn clean compile
```

---

*Reto generado automaticamente por Challenge Generator - Pragma*
