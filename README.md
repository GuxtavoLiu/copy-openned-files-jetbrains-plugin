# Copy Open Files for JetBrains IDEs

> **Quickly gather your open files and paste them into AI assistants for code help**  
> This plugin lets you copy the absolute path and full contents of all open editor tabs in one click—perfect for sharing
> entire files with AI tools.

---

## Prerequisites

- JDK 21+ installed and `JAVA_HOME` set
- Gradle Wrapper (included)
- A JetBrains IDE (IntelliJ IDEA, PyCharm, WebStorm, etc.)

---

## 1. Clone the repository

```bash
git clone https://github.com/GuxtavoLiu/copy-openned-files-jetbrains-plugin.git CopyOpenFiles
cd CopyOpenFiles
````

---

## 2. Configure in your IDE

1. Open the project in your JetBrains IDE.
2. Go to **Project Structure** (Ctrl + Alt + Shift + S) and select Java 21 as the SDK.
3. Wait for Gradle to sync.

---

## 3. Run in development mode

From the project root:

```bash
./gradlew runIde
```

This launches a fresh IDE instance with your plugin loaded.
Open any project, open a few files, then go to **View ▶ Tool Windows ▶ CopyOpenFiles** to test.

---

## 4. Build and install locally

1. Package the plugin:

   ```bash
   ./gradlew clean buildPlugin
   ```

   The ZIP will be in `build/distributions/`.

2. In your primary IDE:

    * **Settings ▶ Plugins ▶ ⚙ ▶ Install Plugin from Disk…**
    * Select the generated `.zip`
    * Restart if prompted

---

## Usage

1. Open multiple files in the editor.
2. Open the **CopyOpenFiles** tool window (sidebar).
3. Click **Copy Open Files**.
4. Everything will be copied in this format:

   ```
   /absolute/path/to/file.txt:
   `full file contents…`
   ```

---

## Project structure

```
.
├── src/
│   ├── main/kotlin/…       – plugin source code
│   └── resources/…         – plugin.xml, messages, icons
├── build.gradle.kts        – Gradle build script
├── gradlew(.bat)           – Gradle Wrapper scripts
└── settings.gradle.kts     – multi‐project settings
```

---

## Contributing

1. Open an issue to discuss your idea.
2. Fork the repo, implement your change, and send a Pull Request.

---
<!-- Plugin description -->
**Copy Open Files** is a lightweight JetBrains IDE plugin that lets you instantly collect the absolute paths and full
contents of all open editor tabs and copy them to your clipboard in one click. Whether you’re sharing code snippets with
colleagues, pasting into documentation or feeding entire files to AI assistants, this tool streamlines the process and
saves valuable time.

Key features:

- 📋 Copy every open file’s absolute path and content in Markdown-friendly format
- ⚡ One-click operation from a dedicated sidebar tool window
- 🌐 Full localization support (English, Portuguese, Spanish)
- 🔧 Easy to install and run in any IntelliJ-based IDE (IntelliJ IDEA, PyCharm, WebStorm, etc.)

---

[gh:template]: https://docs.github.com/en/repositories/creating-and-managing-repositories/creating-a-repository-from-a-template
<!-- Plugin description end -->

## License

This project is licensed under the MIT License – see the [LICENSE](LICENSE) file for details.

© 2025 Gustavo Liu
