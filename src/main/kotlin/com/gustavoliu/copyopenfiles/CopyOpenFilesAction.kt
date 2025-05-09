package com.gustavoliu.copyopenfiles

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.ide.CopyPasteManager
import java.awt.datatransfer.StringSelection

class CopyOpenFilesAction : AnAction("Copiar Arquivos Abertos") {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val editorManager = FileEditorManager.getInstance(project)
        val docs = FileDocumentManager.getInstance()
        val sb = StringBuilder()

        for (vf in editorManager.openFiles) {
            docs.getDocument(vf)?.let { doc ->
                sb.append(vf.path)
                    .append(":\n`")
                    .append(doc.text.replace("`", "\\`"))
                    .append("`\n\n")
            }
        }

        CopyPasteManager.getInstance().setContents(StringSelection(sb.toString()))
        Notifications.Bus.notify(
            Notification(
                "copyOpenFiles",
                "Copy Open Files",
                "${editorManager.openFiles.size} arquivo(s) copiado(s).",
                NotificationType.INFORMATION
            ),
            project
        )
    }
}
