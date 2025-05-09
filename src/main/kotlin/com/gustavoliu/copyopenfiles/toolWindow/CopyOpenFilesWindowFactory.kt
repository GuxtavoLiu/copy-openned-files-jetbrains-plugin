package com.gustavoliu.copyopenfiles.toolWindow

import com.gustavoliu.copyopenfiles.CopyOpenFilesBundle
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBPanel
import com.intellij.ui.content.ContentFactory
import java.awt.FlowLayout
import java.awt.datatransfer.StringSelection
import javax.swing.JButton

class CopyOpenFilesWindowFactory : ToolWindowFactory {

    init {
        thisLogger().warn(
            "Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`."
        )
    }

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        // Criamos um painel com FlowLayout para alinhar horizontalmente
        val panel = JBPanel<JBPanel<*>>().apply {
            layout = FlowLayout(FlowLayout.LEFT, 8, 8)

            // Botão "Copiar Arquivos Abertos"
            val btnCopy = JButton(CopyOpenFilesBundle.message("copyActionLabel")).apply {
                addActionListener {
                    val fem = FileEditorManager.getInstance(project)
                    val docs = FileDocumentManager.getInstance()
                    val sb = StringBuilder()

                    fem.openFiles.forEach { vf ->
                        docs.getDocument(vf)?.let { d ->
                            sb.append(vf.path)
                                .append(":\n`")
                                .append(d.text.replace("`", "\\`"))
                                .append("`\n\n")
                        }
                    }

                    // copia para a área de transferência
                    CopyPasteManager.getInstance()
                        .setContents(StringSelection(sb.toString()))

                    // notifica o usuário
                    val count = fem.openFiles.size
                    Notifications.Bus.notify(
                        Notification(
                            "copy-open-files",
                            "Copy Open Files",
                            CopyOpenFilesBundle.message("copyNotification", count),
                            NotificationType.INFORMATION
                        ),
                        project
                    )
                }
            }

            add(btnCopy)
        }

        // adiciona o painel ao Tool Window
        val content = ContentFactory.getInstance()
            .createContent(panel, /* display name */ "", false)
        toolWindow.contentManager.addContent(content)
    }

    override fun shouldBeAvailable(project: Project) = true
}
