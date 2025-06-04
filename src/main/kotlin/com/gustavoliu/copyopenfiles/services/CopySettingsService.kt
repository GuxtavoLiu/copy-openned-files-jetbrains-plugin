package com.gustavoliu.copyopenfiles.services

import com.intellij.openapi.components.*

@State(
    name = "CopyOpenFilesSettings",
    storages = [Storage("CopyOpenFilesSettings.xml")]
)
@Service(Service.Level.PROJECT)
class CopySettingsService : PersistentStateComponent<CopySettingsService.State> {

    data class State(var prefixText: String = "")

    private var state = State()

    override fun getState(): State = state

    override fun loadState(state: State) {
        this.state = state
    }

    var prefixText: String
        get() = state.prefixText
        set(value) {
            state.prefixText = value
        }
}
