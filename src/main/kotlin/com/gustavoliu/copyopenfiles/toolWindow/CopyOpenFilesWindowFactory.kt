package com.gustavoliu.copyopenfiles.toolWindow

import com.gustavoliu.copyopenfiles.CopyOpenFilesBundle
import com.gustavoliu.copyopenfiles.services.CopySettingsService
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTextArea
import com.intellij.ui.content.ContentFactory
import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.datatransfer.StringSelection
import javax.swing.JButton
import javax.swing.JPanel

class CopyOpenFilesWindowFactory : ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val settings = project.getService(CopySettingsService::class.java)

        val mainPanel = JBPanel<JBPanel<*>>().apply {
            layout = BorderLayout(8, 8)
        }

        // Área de texto (multilinha)
        val prefixArea = JBTextArea(5, 50).apply {
            lineWrap = true
            wrapStyleWord = true
            text = settings.prefixText
        }

        // Scroll para o TextArea
        val scrollPane = JBScrollPane(prefixArea)

        // Botões no topo
        val buttonsPanel = JPanel(FlowLayout(FlowLayout.LEFT, 8, 8))

        // Botão "Copiar com prefixo"
        val btnCopyWithPrefix = JButton(CopyOpenFilesBundle.message("copyActionLabel")).apply {
            addActionListener {
                settings.prefixText = prefixArea.text
                val content = buildContent(project, includePrefix = true, prefixArea.text)
                copyToClipboard(project, content)
            }
        }

        // Botão "Copiar somente código"
        val btnCopyOnlyCode = JButton("Copy Only Code").apply {
            addActionListener {
                settings.prefixText = prefixArea.text
                val content = buildContent(project, includePrefix = false, "")
                copyToClipboard(project, content)
            }
        }

        buttonsPanel.add(btnCopyWithPrefix)
        buttonsPanel.add(btnCopyOnlyCode)

        mainPanel.add(buttonsPanel, BorderLayout.NORTH)
        mainPanel.add(scrollPane, BorderLayout.CENTER)

        val content = ContentFactory.getInstance()
            .createContent(mainPanel, "", false)
        toolWindow.contentManager.addContent(content)
    }

    private fun buildContent(project: Project, includePrefix: Boolean, prefix: String): String {
        val fem = FileEditorManager.getInstance(project)
        val docs = FileDocumentManager.getInstance()
        val sb = StringBuilder()

        if (includePrefix) {
            sb.append(prefix).append("\n\n")
        }

        fem.openFiles.forEach { vf ->
            docs.getDocument(vf)?.let { d ->
                sb.append(vf.path)
                    .append(":\n`")
                    .append(d.text.replace("`", "\\`"))
                    .append("`\n\n")
            }
        }

        return sb.toString()
    }

    private fun copyToClipboard(project: Project, content: String) {
        CopyPasteManager.getInstance().setContents(StringSelection(content))
        val count = FileEditorManager.getInstance(project).openFiles.size
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

    override fun shouldBeAvailable(project: Project) = true
}
