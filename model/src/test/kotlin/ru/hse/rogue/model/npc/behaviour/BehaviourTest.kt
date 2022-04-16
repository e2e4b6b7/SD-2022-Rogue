package ru.hse.rogue.model.npc.behaviour

internal interface BehaviourTest {
    fun `nobody around`()
    fun `player far`()
    fun `player near not in tight`()
    fun `player near in tight`()
}
