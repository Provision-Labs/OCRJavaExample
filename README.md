# OCRJavaExample

## System overview

OCRJavaExample is a small Java/Spring Boot web client for the self-hosted Provision OCR service. The application provides a browser UI for uploading an image or PDF, forwards the file to the Provision OCR REST API, and renders the recognized document structure returned by the OCR server.

Provision OCR recognizes text in images and PDF documents, supports CPU and GPU deployments, and exposes HTTP endpoints such as `/processing/{template}` for document recognition. The default client configuration expects the OCR server at `http://localhost:8098/processing/{template}`.

## Installation

### 1. Install and start the Provision OCR server

Install the OCR backend first. See the official Provision OCR documentation for the full and current instructions: <https://provisionlabs.ru/docs/ocr>.

#### Docker installation (recommended for Linux)

```bash
# Pull the trial OCR image
docker pull registry.provlabs.tech/hub/trial/provision_ocr:latest

# Start with NVIDIA GPU support
docker run -d \
  --name provision_ocr \
  --gpus all \
  -p 8098:8098 \
  --restart always \
  registry.provlabs.tech/hub/trial/provision_ocr:latest
```

For CPU-only or macOS runs, omit the `--gpus all` option. After startup, verify that the OCR service is healthy:

```bash
curl -X POST http://localhost:8098/health
```

#### Windows installation

1. Download and run the Windows installer from the Provision OCR documentation page.
2. Select the language and installation path.
3. Start `ProvisionOCR.exe` from the `ProvisionScan/` directory.
4. Wait until the console shows `Provision Scan: READY`.
5. Use the URL shown in the `Listening on` field as the OCR API base URL.

For CUDA acceleration on Windows, install NVIDIA CUDA Toolkit 11.8 or newer and an NVIDIA driver version 520 or newer.

### 2. Build and run this Java client

Requirements:

- JDK 17
- Access to a running Provision OCR server

Build the application:

```bash
./gradlew clean build
```

Run it:

```bash
./gradlew bootRun
```

Open the web UI in a browser:

```text
http://localhost:8080/ocr
```

If your Provision OCR server is not available at the default URL, override the client endpoint with the `OCR_PROCESS_URL_TEMPLATE` environment variable:

```bash
OCR_PROCESS_URL_TEMPLATE=http://localhost:8098/processing/{template} ./gradlew bootRun
```

## Technologies and requirements

### Java client

- Java: 17
- Spring Boot: 3.5.15
- Spring Framework dependency set: 6.2.19
- Thymeleaf: 3.1.5.RELEASE
- Gradle wrapper project with Spring Boot Web and Thymeleaf starters

### Provision OCR backend hardware and OS requirements

Provision OCR can run on CPU or GPU. CPU-only mode is supported but is significantly slower; for production workloads, the vendor recommends a GPU with at least 8 GB of VRAM.

#### GPU compatibility

| Vendor / device | Minimum VRAM | Backend | Status |
| --- | ---: | --- | --- |
| NVIDIA RTX 3060 / 4060 / 4070 | 8 GB | CUDA 11.8+ | Recommended |
| NVIDIA RTX 3080 / 4080 / 4090 | 12 GB | CUDA 12.x | Recommended |
| NVIDIA A4000 / A10 / L4 | 16 GB | CUDA 12.x | Tested |
| NVIDIA GTX 10xx / 16xx | 8 GB | CUDA 11.x | Partial support |
| Apple M1 / M2 / M3 / M4 | 16 GB unified memory | Metal / MLX | Tested |
| CPU only | x86_64 / ARM64 | N/A | Slower |

#### Operating systems

| OS | Architecture | Minimum RAM | Status |
| --- | --- | ---: | --- |
| Ubuntu 20.04 / 22.04 / 24.04 | x86_64, ARM64 | 8 GB | Recommended |
| Debian 11 / 12 | x86_64 | 8 GB | Tested |
| Windows 10 / 11 | x86_64 | 8 GB | Tested |
| macOS 13+ (Ventura) | ARM64 (Apple Silicon) | 16 GB | Tested |
| Docker Linux image | x86_64, ARM64 | 8 GB | Recommended |
