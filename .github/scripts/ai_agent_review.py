import os
import requests

API_KEY = os.getenv("OPENAI_API_KEY")

with open("diff.txt") as f:
    diff = f.read()[:12000]  # límite seguro

SYSTEM_PROMPT = """(Eres un AI Code Review Agent especializado en:

- Java 17+
- Spring Boot
- Spring WebFlux
- Programación reactiva (Mono / Flux)
- Arquitectura hexagonal
- Microservicios
- Kubernetes
- Buenas prácticas cloud-native

OBJETIVO:
Analizar Pull Requests y detectar únicamente problemas REALES y accionables.

REGLAS:
- NO elogies código sin motivo
- NO repitas recomendaciones genéricas
- NO inventes problemas
- Prioriza bugs, seguridad y problemas reactivos
- Si no hay observaciones importantes, responde: "No se encontraron problemas relevantes"

DEBES DETECTAR:
1. Errores en flujos reactivos (subscribe, block, map vs flatMap)
2. Uso incorrecto de WebClient
3. Bloqueos en hilos no reactivos
4. Manejo incorrecto de errores (onErrorResume / onErrorMap)
5. Problemas de concurrencia
6. Violaciones de arquitectura (controller → repository)
7. Configuraciones peligrosas
8. Código no cloud-native
9. Antipatrones comunes en Spring Boot

FORMATO DE RESPUESTA (OBLIGATORIO):
- Lista numerada
- Cada punto debe incluir:
  - Archivo
  - Línea aproximada
  - Problema
  - Recomendación concreta
)"""

response = requests.post(
    "https://api.openai.com/v1/responses",
    headers={
        "Authorization": f"Bearer {API_KEY}",
        "Content-Type": "application/json"
    },
    json={
        "model": "gpt-4.1-mini",
        "input": [
            {"role": "system", "content": SYSTEM_PROMPT},
            {"role": "user", "content": diff}
        ],
        "temperature": 0.1
    }
)

data = response.json()

if response.status_code != 200:
    print("Status:", response.status_code)
    print("Response:", data)
    raise Exception("Error en la API")

content = data["output"][0]["content"][0]["text"]
print(content)

# Motor de decisión
if "No se encontraron problemas relevantes" in content:
    with open("review_comment.txt", "w") as f:
        f.write("🤖 **AI Review**\n\n✅ No se encontraron problemas relevantes.")
else:
    with open("review_comment.txt", "w") as f:
        f.write("🤖 **AI Review (Java / WebFlux)**\n\n" + content)
